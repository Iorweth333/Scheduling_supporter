import React, { Component } from "react";
import moment from "moment";
import 'react-calendar-timeline/lib/Timeline.css'
import Timeline from "react-calendar-timeline";
import _ from "lodash";


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

export default class SchedulerCalendar_legacy extends Component {

    parseLecturers(lecturers){
        return _.uniqBy(lecturers, 'id').map( lecturer =>{
            return {id: lecturer.id,
                    title: lecturer.name + ' ' +lecturer.surname,
                    root: false,
                    parent: 0
            }
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
    constructor(props) {
        super(props);
        let groups = this.parseLecturers(this.props.items.map(({ lecturer }) => lecturer));
        groups.push({ id: 0, title: 'CS', root: true });
        console.log(groups);
        let lessons = this.parseLessons(this.props.items);


        this.state = {
            groups: groups,
            items: lessons,
            defaultTimeStart: moment().startOf("day").toDate(),
            defaultTimeEnd: moment().startOf("day").add(1, "day").toDate(),
            openGroups: {},
            visibleTimeStart: null,
            visibleTimeEnd: null
        };
        this.handleTimeChange = this.handleTimeChange.bind(this);
        this.handlePrevMonth = this.handlePrevMonth.bind(this);
        this.handleNextMonth = this.handleNextMonth.bind(this);
        this.handlePrevDay = this.handlePrevDay.bind(this);
        this.handleNextDay = this.handleNextDay.bind(this);
        this.handlePrevYear = this.handlePrevYear.bind(this);
        this.handleNextYear = this.handleNextYear.bind(this);
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

        console.log("Moved", itemId, dragTime, newGroupOrder);
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

        console.log("Resized", itemId, time, edge);
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

    render() {
        const {
            groups,
            items,
            defaultTimeStart,
            defaultTimeEnd,
            openGroups,
            visibleTimeStart,
            visibleTimeEnd
        } = this.state;

       const newGroups = groups
            .filter(g => g.root || openGroups[g.parent])
            .map(group => {
                return Object.assign({}, group, {
                    title: group.root ? (
                        <div
                            onClick={() => this.toggleGroup(parseInt(group.id))}
                            style={{ cursor: "pointer" }}
                        >
                            {openGroups[parseInt(group.id)] ? "[-]" : "[+]"} {group.title}
                        </div>
                    ) : (
                        <div style={{ paddingLeft: 20 }}>{group.title}</div>
                    )
                });
            });

        return (
            <div>
                <button onClick={this.handlePrevDay}>prevDay</button>
                <button onClick={this.handleNextDay}>nextDay</button>
                <button onClick={this.handlePrevMonth}>prevMonth</button>
                <button onClick={this.handleNextMonth}>nextMonth</button>
                <button onClick={this.handlePrevYear}>prevYear</button>
                <button onClick={this.handleNextYear}>nextYear</button>
                <Timeline
                    groups={newGroups}
                    items={items}
                    keys={keys}
                    fixedHeader="fixed"
                    sidebarWidth={250}
                    sidebarContent={<div></div>}
                    canMove
                    canResize="right"
                    canSelect
                    itemsSorted
                    itemTouchSendsClick={false}
                    stackItems
                    itemHeightRatio={0.75}
                    showCursorLine
                    defaultTimeStart={defaultTimeStart}
                    defaultTimeEnd={defaultTimeEnd}
                    visibleTimeStart={visibleTimeStart}
                    visibleTimeEnd={visibleTimeEnd}
                    onItemMove={this.handleItemMove}
                    onItemResize={this.handleItemResize}
                    onTimeChange={this.handleTimeChange}
                />
            </div>
        );
    }
}
