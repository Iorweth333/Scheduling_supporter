import React, { Component } from "react";
import moment from "moment";
import 'react-calendar-timeline/lib/Timeline.css'
import Timeline from "react-calendar-timeline";
import _ from "lodash";
import {fetchLessons} from "../actions";
import {connect} from "react-redux";
import {Spinner} from "react-bootstrap";
import { MdModeEdit } from "react-icons/md";
import Lesson from "./Lesson";
import Conflicts from "./Conflicts";
import containerResizeDetector from 'react-calendar-timeline/lib/resize-detector/container'

var keys = {
    groupIdKey: "id",
    groupTitleKey: "title",
    groupRightTitleKey: "rightTitle",
    itemIdKey: "id",
    itemTitleKey: "title",
    itemDivTitleKey: "title",
    itemGroupKey: "group",
    itemTimeStartKey: "start",
    itemTimeEndKey: "end",
    groupLabelKey: "title"
};

const DAY = 86400000;
const MONTH = 2678400000;
const YEAR = 31536000000;

const conflictStyle={
    backgroundColor: "red",
    position:"relative",
    display: "inline-block"
};
const normalStyle={
    position:"relative", display: "inline-block"
};

class SchedulerCalendar extends Component {

    parseLecturers(lecturers){
        return _.uniqBy(lecturers, 'id').map( lecturer =>{
            return {id: lecturer.id,
                    title: lecturer.name + ' ' +lecturer.surname,
                    root: false,
                    parent: 0
            }
        });
    }

    newGroups(groups, openGroups){
        return groups.filter(g => g.root || openGroups[g.parent])
                .map(group => {
                    return Object.assign({}, group, {
                        title: group.root ? (
                            <div
                                onClick={() => this.toggleGroup(parseInt(group.id))}
                                style={{cursor: "pointer"}}
                            >
                                {openGroups[parseInt(group.id)] ? "[-]" : "[+]"} {group.title}
                            </div>
                        ) : (
                            <div style={{paddingLeft: 20}}>{group.title}</div>
                        )
                    });
                });
    }

    parseLessons(data){
        return data.map( row =>{
            let date = moment(row.date).format("YYYY-MM-DD");
            return {
                id: row.id,
                group: row.lecturer.id,             // <- group to nie grupa tylko id prowadzacego, wymagane przez timeline pole musi sie tak nazywac :/
                title: row.subject.name,
                start: moment(date + "T" + moment(row.startsAt, 'hh:mm a').format('HH:mm:ss')).valueOf(),
                end: moment(date + "T" + moment(row.endsAt, 'hh:mm a').format('HH:mm:ss')).valueOf(),
                lecturer: row.lecturer,
                classroom: row.classroom,
                studentsGroup: row.studentsGroup
            }
        })
    }

    componentDidMount() {
        if (this.props.loading) {
            this.props.fetchLessons();
        }
    }

    checkConflicts(){
        const {items} = this.state;
        let newConflictList = [];
        if(items) {

            items.forEach(lesson1 => {
                items.forEach(lesson2 => {
                    if (lesson1.id !== lesson2.id) {
                        if (lesson1.start <= lesson2.end && lesson1.end >= lesson2.start) {
                            if (lesson1.group === lesson2.group) {
                                newConflictList.push({
                                    typeOfConflict: "LECTURER",
                                    lessonId1: lesson1.id,
                                    lessonId2: lesson2.id
                                });
                            }
                            if (lesson1.classroom.id === lesson2.classroom.id) {
                                newConflictList.push({
                                    typeOfConflict: "CLASSROOM",
                                    lessonId1: lesson1.id,
                                    lessonId2: lesson2.id
                                });
                            }
                            if (lesson1.studentsGroup.id === lesson2.studentsGroup.id) {
                                newConflictList.push({
                                    typeOfConflict: "GROUP",
                                    lessonId1: lesson1.id,
                                    lessonId2: lesson2.id
                                });
                            }
                        }
                    }
                });
            });
            this.setState({
                lessonsConflicts: newConflictList,
            });
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {
        let groups = this.parseLecturers(nextProps.lessons.map(({ lecturer }) => lecturer));
            groups.push({ id: 0, title: 'CS', root: true });

        this.setState({
            lessons: nextProps.lessons,
            groups: groups,
            items: this.parseLessons(nextProps.lessons),
            currentlesson: nextProps.lessons[0],
        });
        this.checkConflicts();
    }

    constructor(props) {
        super(props);

        document.title = "Scheduling Supporter";

        this.state = {
            groups: null,
            items: null,
            lessons: null,
            defaultTimeStart: moment('2019-03-03').startOf("day").toDate(),
            defaultTimeEnd: moment('2019-03-03').startOf("day").add(1, "day").toDate(),
            openGroups: {},
            visibleTimeStart: null,
            visibleTimeEnd: null,
            modalShow: false,
            currentlesson: null,
            lessonsConflicts: []
        };
        this.handleTimeChange = this.handleTimeChange.bind(this);
        this.itemRenderer = this.itemRenderer.bind(this);
        this.checkConflicts = this.checkConflicts.bind(this);
        this.handleLessonClick = this.handleLessonClick.bind(this);
        this.handleButton = this.handleButton.bind(this);
    }

    handleTimeChange(visibleTimeStart, visibleTimeEnd){
        this.setState({
            visibleTimeStart: visibleTimeStart,
            visibleTimeEnd: visibleTimeEnd
        });
        this.checkConflicts();
    }
    toggleGroup = id => {
        const { openGroups } = this.state;
        this.setState({
            openGroups: {
                ...openGroups,
                [id]: !openGroups[id]
            }
        });
    };


    handleItemMove = (itemId, dragTime, newGroupOrder) => {
        const { items, groups } = this.state;

        const group = groups[newGroupOrder];

        this.setState({
            items: items.map(item =>
                item.id === itemId
                    ? Object.assign({}, item, {
                        start: dragTime,
                        end: dragTime + (item.end - item.start),
                        group: group.id
                    })
                    : item
            )
        });
        this.checkConflicts();
    };

    handleItemResize = (itemId, time, edge) => {
        const { items } = this.state;

        this.setState({
            items: items.map(item =>
                item.id === itemId
                    ? Object.assign({}, item, {
                        start: edge === "left" ? time : item.start,
                        end: edge === "left" ? item.end : time
                    })
                    : item
            )
        });
        this.checkConflicts();
    };

    handleLessonClick(itemId, e, time){
        const lesson = this.state.lessons.find(lesson => lesson.id === itemId);
        this.setState({modalShow: true, currentlesson: lesson});
    }

    handleButton(value){
        if(this.state.visibleTimeStart && this.state.visibleTimeEnd) {
            this.handleTimeChange(this.state.visibleTimeStart + value, this.state.visibleTimeEnd + value);
        }
    }

    itemRenderer({item,itemContext,getItemProps,getResizeProps}){

        const { left: leftResizeProps, right: rightResizeProps } = getResizeProps();

        const conflict = this.state.lessonsConflicts.find(conflict => conflict.lessonId1 === item.id || conflict.lessonId2 === item.id);

        return (
            <div {...getItemProps(item.itemProps)}  >
                <div style={conflict ? conflictStyle: normalStyle}>
                    <div className="rct-item-content" style={{ maxHeight: `${itemContext.dimensions.height}`, lineHeight: "15px", fontSize: "10px" }}>
                        {itemContext.title}
                        <MdModeEdit style={{position: "aboslute", bottom: "0px", right: "0px", width: "15px", cursor: "pointer"}}
                                    onClick={() => this.setState({ modalShow: true })}/>
                    </div>
                </div>
            </div>
        )}


    render() {
        let modalClose = () => this.setState({ modalShow: false });

        const {
            defaultTimeStart,
            defaultTimeEnd,
            openGroups,
            groups,
            items,
            visibleTimeStart,
            visibleTimeEnd
        } = this.state;

        const { error, loading } = this.props;

        if (error) {
            return <div>Error! {error.message}</div>;
        }

        if (loading) {
            return <div><Spinner animation="grow" /></div>;
        }

        return (
            <div>
                <header className="headerMain">
                    <h2 className="logo">Scheduling Supporter</h2>
                </header>
                <div className="header">
                    <div style={{padding: "50px"}}>
                        <div className="row"><div className="col-xs-12">
                            <div className="buttons_line">
                                <div className="divider">
                                    <button className="btn btn-success" onClick={this.handlePrevDay}>previous day</button>
                                </div>
                                <div className="divider">
                                    <button className="btn btn-success" onClick={this.handleNextDay}>next day</button>
                                </div>
                                <div className="divider">
                                    <button className="btn btn-success" onClick={this.handlePrevMonth}>previous month</button>
                                </div>
                                <div className="divider">
                                    <button className="btn btn-success" onClick={this.handleNextMonth}>next month</button>
                                </div>
                                <div className="divider">
                                    <button className="btn btn-success" onClick={this.handlePrevYear}>previous year</button>
                                </div>
                                <div className="divider">
                                    <button className="btn btn-success" onClick={this.handleNextYear}>next year</button>
                                </div>
                            </div></div></div>
                    </div>
                </div>
                    <Timeline
                        groups={this.newGroups(groups, openGroups)}
                        items={items}
                        keys={keys}
                        fixedHeader="fixed"
                        sidebarWidth={250}
                        lineHeight={60}
                        sidebarContent={<div></div>}
                        canMove
                        canResize="right"
                        canSelect
                        itemsSorted
                        itemRenderer={this.itemRenderer}
                        itemTouchSendsClick={false}
                        stackItems
                        itemHeightRatio={0.85}
                        showCursorLine
                        defaultTimeStart={defaultTimeStart}
                        defaultTimeEnd={defaultTimeEnd}
                        visibleTimeStart={visibleTimeStart}
                        visibleTimeEnd={visibleTimeEnd}
                        onItemDoubleClick={this.handleLessonClick}
                        onItemMove={this.handleItemMove}
                        onItemResize={this.handleItemResize}
                        onTimeChange={this.handleTimeChange}
                        resizeDetector={containerResizeDetector}
                    />

                    <Lesson
                        show={this.state.modalShow}
                        onHide={modalClose}
                        currentlesson={this.state.currentlesson}
                    />
                <p/>
                <Conflicts/>
            </div>
        );
    }
}


function mapStateToProps(state){
    return{
        loading: state.lessons.loading,
        error: state.lessons.error,
        lessons: state.lessons.lessons,
    };
}

export default connect(mapStateToProps, {fetchLessons})(SchedulerCalendar);
