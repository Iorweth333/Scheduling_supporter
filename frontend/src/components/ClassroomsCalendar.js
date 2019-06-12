import React, { Component } from "react";
import moment from "moment";
import 'react-calendar-timeline/lib/Timeline.css'
import Timeline from "react-calendar-timeline";
import _ from "lodash";
import {fetchClassrooms, fetchLessons, sendLessons} from "../actions";
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

    parseClassrooms(classrooms){
        return _.uniqBy(classrooms, 'id').map( classroom =>{
            return {id: classroom.id,
                title: `${classroom.number}`,
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
                group: row.classroom.id,             // <- group to nie grupa tylko id prowadzacego, wymagane przez timeline pole musi sie tak nazywac :/
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
            this.props.fetchClassrooms();
        }
    }

    checkConflicts(){
        const {lessons} = this.state;
        let newConflictList = [];
        if(lessons) {


            lessons.forEach((lesson1, index) => {
                lessons.slice(index+1).forEach(lesson2 => {
                    if (lesson1.id !== lesson2.id) {

                        const lesson1Start = this.dateAndTimeToValue(lesson1.date, lesson1.startsAt);
                        const lesson1End =  this.dateAndTimeToValue(lesson1.date, lesson1.endsAt);
                        const lesson2Start = this.dateAndTimeToValue(lesson2.date, lesson2.startsAt);
                        const lesson2End =  this.dateAndTimeToValue(lesson2.date, lesson2.endsAt);



                        if (lesson1Start <= lesson2End && lesson1End >= lesson2Start) {
                            if (lesson1.lecturer.id === lesson2.lecturer.id) {
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
        console.log(nextProps)

        let groups = this.parseClassrooms(nextProps.classrooms);
        groups.push({ id: 0, title: 'CS', root: true });

        this.setState({
            lessons: nextProps.lessons,
            groups: groups,
            currentlesson: nextProps.lessons[0],
        });
        this.checkConflicts();
    }

    constructor(props) {
        super(props);

        document.title = "Scheduling Supporter";

        this.state = {
            groups: null,
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
        this.handleOverride = this.handleOverride.bind(this);
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
        const { groups, lessons } = this.state;

        const group = groups[newGroupOrder];

        let classroom = lessons.find(lesson => lesson.classroom.id == group.id);

        if(classroom){
            classroom = classroom.classroom;

            const newDate = moment(dragTime);
            const lesson = lessons.find(lesson => lesson.id == itemId);


            const duration = moment.duration(moment(`Jun 11, 2019, ${lesson.endsAt}`)
                .diff(moment(`Jun 11, 2019, ${lesson.startsAt}`))).valueOf();


            const newLesson = { ...lesson, date: newDate.format("lll"),
                startsAt: newDate.format("HH:mm:ss"),
                endsAt: newDate.add(duration).format("HH:mm:ss"),
                classroom: classroom};

            this.setState({
                lessons: lessons.map(lesson => lesson.id === itemId
                    ? Object.assign({}, lesson, newLesson)
                    : lesson)
            });
            this.checkConflicts();
        }
    };

    handleItemResize = (itemId, time, edge) => {

        const { lessons } = this.state;

        const lesson = lessons.find(lesson => lesson.id == itemId);

        const newLesson = { ...lesson,
            startsAt: edge === "left" ? moment(time).format("HH:MM") : lesson.startsAt,
            endsAt: edge === "left" ? lesson.endsAt : moment(time).format("HH:MM")};

        console.log(newLesson)
        this.setState({
            lessons: lessons.map(lesson => lesson.id === itemId
                ? Object.assign({}, lesson, newLesson)
                : lesson)
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

    handleOverride(){
        console.log("override db");
        this.props.sendLessons(this.state.lessons);
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
                <div className="header">
                    <div style={{padding: "50px"}}>
                        <div className="row"><div className="col-xs-12">
                            <div className="buttons_line">
                                <div className="divider">
                                    <button className="btn btn-success" onClick={() => this.handleButton(-1*DAY)}>previous day</button>
                                </div>
                                <div className="divider">
                                    <button className="btn btn-success" onClick={() => this.handleButton(DAY)}>next day</button>
                                </div>
                                <div className="divider">
                                    <button className="btn btn-success" onClick={() => this.handleButton(-1*MONTH)}>previous month</button>
                                </div>
                                <div className="divider">
                                    <button className="btn btn-success" onClick={() => this.handleButton(MONTH)}>next month</button>
                                </div>
                                <div className="divider">
                                    <button className="btn btn-success" onClick={() => this.handleButton(-1*YEAR)}>previous year</button>
                                </div>
                                <div className="divider">
                                    <button className="btn btn-success" onClick={() => this.handleButton(YEAR)}>next year</button>
                                </div>
                            </div></div></div>
                    </div>
                </div>
                <Timeline
                    groups={this.newGroups(groups, openGroups)}
                    items={this.parseLessons(this.state.lessons)}
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
                {this.state.lessonsConflicts.length ? <Conflicts lessonsConflicts={this.state.lessonsConflicts}/>
                    : <div>
                        <button className="btn btn-success" onClick={this.handleOverride}>Override!</button>
                    </div>
                }

            </div>
        );
    }

    dateAndTimeToValue(date, time){
        return moment(moment(date).format("ll") + " " + moment(time, 'hh:mm a').format('HH:mm:ss')).valueOf();
    }
}


function mapStateToProps(state){
    return{
        loading: state.lessons.loading,
        error: state.lessons.error,
        lessons: state.lessons.lessons,
        classrooms: state.lessons.classrooms,
    };
}

export default connect(mapStateToProps, {fetchLessons, fetchClassrooms, sendLessons})(SchedulerCalendar);
