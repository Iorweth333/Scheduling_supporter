import React, { Component } from "react";
import moment from "moment";
import 'react-calendar-timeline/lib/Timeline.css'
import Timeline from "react-calendar-timeline";


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

export default class SchedulerCalendar extends Component {
    constructor(props) {
        super(props);

        this.state = {
            groups: [{ id: 1, title: 'CS', root: true },
                { id: 4, title: 'Jan Nowak', root: false, parent: 1},
                { id: 5, title: 'Pawel Kowalski', root: false, parent: 1},
                { id: 6, title: 'Zbigniew Bak', root: false, parent: 1},
                { id: 2, title: 'E&T', root: true },
                { id: 7, title: 'Marek Garek', root: false, parent: 2},
                { id: 8, title: 'Pawel Kowalski', root: false, parent: 2}],
            items:
                [
                {
                    id: 1,
                    group: 4,
                    title: "WDI",
                    start: moment().add(-8, 'hour'),
                    end: moment().add(-6, 'hour'),
                    className: (moment(moment()).day() === 6 || moment(moment()).day() === 0) ? 'item-weekend' : '',
                    itemProps: {
                        'data-tip': "WDI"
                    }
                },
                {
                    id: 2,
                    group: 5,
                    title: "ASD",
                    start: moment().add(-5, 'hour'),
                    end: moment().add(-3, 'hour'),
                    className: (moment(moment()).day() === 6 || moment(moment()).day() === 0) ? 'item-weekend' : '',
                    itemProps: {
                        'data-tip': "ASD"
                    }
                },
                {
                    id: 3,
                    group: 6,
                    title: "Funkcyjne",
                    start: moment().add(1, 'hour'),
                    end: moment().add(3, 'hour'),
                    className: (moment(moment()).day() === 6 || moment(moment()).day() === 0) ? 'item-weekend' : '',
                    itemProps: {
                        'data-tip': "Funkcyjne"
                    }
                },
                {
                    id: 4,
                    group: 7,
                    title: "JKA",
                    start: moment().add(-8, 'hour'),
                    end: moment().add(-6, 'hour'),
                    className: (moment(moment()).day() === 6 || moment(moment()).day() === 0) ? 'item-weekend' : '',
                    itemProps: {
                        'data-tip': "JKA"
                    }
                }
            ],
            defaultTimeStart: moment().startOf("day").toDate(),
            defaultTimeEnd: moment().startOf("day").add(1, "day").toDate(),
            openGroups: {}
        };
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

    render() {
        const {
            groups,
            items,
            defaultTimeStart,
            defaultTimeEnd,
            openGroups
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
            <Timeline
                groups={newGroups}
                items={items}
                keys={keys}
                fixedHeader="fixed"
                sidebarWidth={150}
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
                onItemMove={this.handleItemMove}
                onItemResize={this.handleItemResize}
            />
        );
    }
}
