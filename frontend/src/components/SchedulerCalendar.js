import React, { Component } from "react";
import moment from "moment";
import 'react-calendar-timeline/lib/Timeline.css'
import Timeline from "react-calendar-timeline";
import _ from "lodash";
import {fetchLessons} from "../actions";
import {connect} from "react-redux";
import {Button, Spinner} from "react-bootstrap";
import { MdModeEdit } from "react-icons/md";
import Lesson from "./Lesson";

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
                group: row.lecturer.id,
                title: row.subject.name,
                start: moment(date + "T" + moment(row.startsAt, 'hh:mm a').format('HH:mm:ss')),
                end: moment(date + "T" + moment(row.endsAt, 'hh:mm a').format('HH:mm:ss')),
            }
        })
    }



    componentDidMount() {
        if(this.props.loading){
            this.props.fetchLessons();
        }
    }

    componentWillReceiveProps(nextProps, nextContext) {
        let groups = this.parseLecturers(nextProps.lessons.map(({ lecturer }) => lecturer))
            groups.push({ id: 0, title: 'CS', root: true });

        this.setState({
            lessons: nextProps.lessons,
            groups: groups,
            items: this.parseLessons(nextProps.lessons),
            currentlesson: nextProps.lessons[0]
        })
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
            currentlesson: null
        };
        this.handleTimeChange = this.handleTimeChange.bind(this);
        this.handlePrevMonth = this.handlePrevMonth.bind(this);
        this.handleNextMonth = this.handleNextMonth.bind(this);
        this.handlePrevDay = this.handlePrevDay.bind(this);
        this.handleNextDay = this.handleNextDay.bind(this);
        this.handlePrevYear = this.handlePrevYear.bind(this);
        this.handleNextYear = this.handleNextYear.bind(this);
        this.itemRenderer = this.itemRenderer.bind(this);
        this.handleLessonClick = this.handleLessonClick.bind(this);
    }

    handleTimeChange(visibleTimeStart, visibleTimeEnd){
        this.setState({
            visibleTimeStart: visibleTimeStart,
            visibleTimeEnd: visibleTimeEnd
        });
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
    };

    handlePrevDay(){
        if(this.state.visibleTimeStart && this.state.visibleTimeEnd) {
            this.handleTimeChange(this.state.visibleTimeStart - DAY, this.state.visibleTimeEnd - DAY);
        }
    }
    handleNextDay(){
        if(this.state.visibleTimeStart && this.state.visibleTimeEnd) {
            this.handleTimeChange(this.state.visibleTimeStart + DAY, this.state.visibleTimeEnd + DAY);
        }
    }

    handlePrevMonth(){
        if(this.state.visibleTimeStart && this.state.visibleTimeEnd) {
            this.handleTimeChange(this.state.visibleTimeStart - MONTH, this.state.visibleTimeEnd - MONTH);
        }
    }
    handleNextMonth(){
        if(this.state.visibleTimeStart && this.state.visibleTimeEnd) {
            this.handleTimeChange(this.state.visibleTimeStart + MONTH, this.state.visibleTimeEnd + MONTH);
        }
    }
    handlePrevYear(){
        if(this.state.visibleTimeStart && this.state.visibleTimeEnd) {
            this.handleTimeChange(this.state.visibleTimeStart - YEAR, this.state.visibleTimeEnd - YEAR);
        }
    }
    handleNextYear(){
        if(this.state.visibleTimeStart && this.state.visibleTimeEnd) {
            this.handleTimeChange(this.state.visibleTimeStart + YEAR, this.state.visibleTimeEnd + YEAR);
        }
    }

    handleLessonClick(itemId, e, time){
        const lesson = this.state.lessons.find(lesson => lesson.id === itemId);
        this.setState({modalShow: true, currentlesson: lesson});
    }



    itemRenderer({item,itemContext,getItemProps,getResizeProps}){

        const { left: leftResizeProps, right: rightResizeProps } = getResizeProps();

        return (
            <div {...getItemProps(item.itemProps)}>
                <div style={{position:"relative", display: "inline-block"}}>
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

        const { error, loading, lessons } = this.props;

        if (error) {
            return <div>Error! {error.message}</div>;
        }

        if (loading) {
            return <div><Spinner animation="grow" /></div>;
        }

        return (
            <div>
                <button onClick={this.handlePrevDay}>prevDay</button>
                <button onClick={this.handleNextDay}>nextDay</button>
                <button onClick={this.handlePrevMonth}>prevMonth</button>
                <button onClick={this.handleNextMonth}>nextMonth</button>
                <button onClick={this.handlePrevYear}>prevYear</button>
                <button onClick={this.handleNextYear}>nextYear</button>
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
                />

                <Lesson
                    show={this.state.modalShow}
                    onHide={modalClose}
                    currentlesson={this.state.currentlesson}
                />

            </div>
        );
    }
}


function mapStateToProps(state){
    return{
        loading: state.lessons.loading,
        error: state.lessons.error,
        lessons: state.lessons.lessons
    };
}

export default connect(mapStateToProps, {fetchLessons})(SchedulerCalendar);
