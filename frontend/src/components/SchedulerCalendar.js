import React from 'react'

import FullCalendar from 'fullcalendar-reactwrapper';
import "fullcalendar-reactwrapper/dist/css/fullcalendar.min.css";
import dayGridPlugin from 'fullcalendar-reactwrapper';
import timeGridPlugin from 'fullcalendar-reactwrapper';
import interactionPlugin from 'fullcalendar-reactwrapper';

export default class SchedulerCalendar extends React.Component {

    constructor(props){
        super(props);
    }

    handleDateClick(arg){
        this.setState({  // add new event data
            calendarEvents: this.state.calendarEvents.concat({ // creates a new array
                title: 'New Event',
                start: arg.date,
                allDay: arg.allDay
            })
        })
    }

    render(){
        return(
            <FullCalendar
                id = "your-custom-ID"
                header = {{
                    left: 'prev,next today myCustomButton',
                    center: 'title',
                    right: 'month,basicWeek,basicDay'
                }}
                {...this.props}
                defaultDate={'2019-04-08'}
                plugins={[ dayGridPlugin, timeGridPlugin, interactionPlugin ]}
                navLinks= {true} // can click day/week names to navigate views
                editable= {true}
                eventLimit= {true} // allow "more" link when too many events
                dateClick={ this.handleDateClick }
            />
        );
    }
}