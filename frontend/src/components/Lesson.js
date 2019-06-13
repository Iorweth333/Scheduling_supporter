import React, {Component} from 'react';
import {Modal, Button} from 'react-bootstrap';

export default class Lesson extends Component {

    constructor(props) {
        super(props);

        this.state = {
            group: this.props.currentlesson.studentsGroup,
            classroom: this.props.currentlesson.classroom,
            startsAt: this.props.currentlesson.startsAt,
            endsAt: this.props.currentlesson.endsAt,
            date: this.props.currentlesson.date,
        };

        this.handleGroupChange = this.handleGroupChange.bind(this);
        this.handleClassroomChange = this.handleClassroomChange.bind(this);
        this.handleTimeOfDayStartChange = this.handleTimeOfDayStartChange.bind(this);
        this.handleHourStartChange = this.handleHourStartChange.bind(this);
        this.handleMinuteStartChange = this.handleMinuteStartChange.bind(this);
        this.handleTimeOfDayEndChange = this.handleTimeOfDayEndChange.bind(this);
        this.handleHourEndChange = this.handleHourEndChange.bind(this);
        this.handleMinuteEndChange = this.handleMinuteEndChange.bind(this);
        this.handleMonthChange = this.handleMonthChange.bind(this);
        this.handleDayChange = this.handleDayChange.bind(this);
        this.handleYearChange = this.handleYearChange.bind(this);
        this.updateLesson = this.updateLesson.bind(this);
    }

    renderGroups(allGroups) {
        return allGroups.map( (group, index) => {
            return (
                <option value={group.name} key={index}>{group.name}</option>
            )
        } );
    }

    renderClassrooms(allClassrooms) {
        return allClassrooms.map( (classroom, index) => {
            return (
                <option value={classroom.number} key={index}>{classroom.number}</option>
            )
        } );
    }

    renderHoursStart() {
        let hours = [];

        for (let i = 1; i < 13; i++) {
            let minute = i.toString().padStart(2, '0');
            hours.push(<option value={minute} key={minute}>{minute}</option>);
        }
        let hour = this.props.currentlesson.startsAt.substring(0,2);
        return (
            <select value={hour} onChange={this.handleHourStartChange}>
                {hours}
            </select>

        )
    }

    renderMinutesStart() {
        let minutes = [];

        for (let i = 0; i < 60; i++) {
            let minute = i.toString().padStart(2, '0');
            minutes.push(<option value={minute} key={minute}>{minute}</option>);
        }
        let minute = this.props.currentlesson.startsAt.substring(3,5);
        return (
        <select value={minute} onChange={this.handleMinuteStartChange}>
            {minutes}
        </select>
        )
    }

    renderTimeOfDayStart() {
        let timeOfDay = this.props.currentlesson.startsAt.substring(9,11);
        return (
            <select value={timeOfDay} onChange={this.handleTimeOfDayStartChange}>
                <option value="AM">AM</option>
                <option value="PM">PM</option>
            </select>
        )
    }

    renderHoursEnd() {
        let hours = [];

        for (let i = 1; i < 13; i++) {
            let minute = i.toString().padStart(2, '0');
            hours.push(<option value={minute} key={minute}>{minute}</option>);
        }
        let hour = this.props.currentlesson.endsAt.substring(0,2);
        return (
            <select value={hour} onChange={this.handleHourEndChange}>
                {hours}
            </select>

        )
    }

    renderMinutesEnd() {
        let minutes = [];

        for (let i = 0; i < 60; i++) {
            let minute = i.toString().padStart(2, '0');
            minutes.push(<option value={minute} key={minute}>{minute}</option>);
        }
        let minute = this.props.currentlesson.endsAt.substring(3,5);
        return (
            <select value={minute} onChange={this.handleMinuteEndChange}>
                {minutes}
            </select>
        )
    }

    renderTimeOfDayEnd() {
        let timeOfDay = this.props.currentlesson.endsAt.substring(9,11);
        return (
            <select value={timeOfDay} onChange={this.handleTimeOfDayEndChange}>
                <option value="AM">AM</option>
                <option value="PM">PM</option>
            </select>
        )
    }

    renderMonth() {
        let month = this.props.currentlesson.date.substring(0,3);
        return (
            <select value={month} onChange={this.handleMonthChange}>
                <option value="Jan">Jan</option>
                <option value="Feb">Feb</option>
                <option value="Mar">Mar</option>
                <option value="Apr">Apr</option>
                <option value="May">May</option>
                <option value="Jun">Jun</option>
                <option value="Jul">Jul</option>
                <option value="Aug">Aug</option>
                <option value="Sep">Sep</option>
                <option value="Oct">Oct</option>
                <option value="Nov">Nov</option>
                <option value="Dec">Dec</option>
            </select>
        )
    }

    renderDay() {
        let month = this.props.currentlesson.date.substring(0,3);
        let pattern = /[0-9]{1,2},/g;
        let day = this.props.currentlesson.date.match(pattern)[0];
        day = day.substring(0, day.length-1);
        let days = [];
        if (month === "Feb") {
            for (let i = 1; i <= 28; i++) {
                let d = i.toString();
                days.push(<option value={d} key={d}>{d}</option>);
            }
        } else if (month === "Jan" || month === "Mar" || month === "May" || month === "Jul" || month === "Aug" || month === "Oct" || month === "Dec") {
            for (let i = 1; i <= 31; i++) {
                let d = i.toString();
                days.push(<option value={d} key={d}>{d}</option>);
            }
        } else {
            for (let i = 1; i <= 30; i++) {
                let d = i.toString();
                days.push(<option value={d} key={d}>{d}</option>);
            }
        }
        return (
            <select value={day} onChange={this.handleDayChange}>
                {days}
            </select>
        )
    }

    renderYear() {
        let pattern = /[0-9]{4}/g;
        let year = this.props.currentlesson.date.match(pattern)[0];
        let years = [];
        for (let i = 2019; i <= 2030; i++) {
            let y = i.toString();
            years.push(<option value={y} key={y}>{y}</option>);
        }
        return (
            <select value={year} onChange={this.handleYearChange}>
                {years}
            </select>
        )
    }

    findGroup(groupName) {
        this.props.allGroups.forEach(g => {
            if (g.name === groupName) {
                return g;
            }
        }
        );
        return null;
    }

    handleGroupChange(event) {
        const {allgroups} =  this.props;
        const group = allgroups.find(group => group.name == event.target.value);
        this.setState({
            group: group,
        });
        this.props.currentlesson.studentsGroup = group;
    }

    handleClassroomChange(event) {
        const {allclassrooms} =  this.props;
        console.log(allclassrooms);

        const classroom = allclassrooms.find(classroom => classroom.number == event.target.value);

        console.log(classroom);

        this.setState({
            classroom: classroom
        });

        this.props.currentlesson.classroom = classroom;
    }

    handleHourStartChange(event) {
        this.props.currentlesson.startsAt = event.target.value + this.props.currentlesson.startsAt.slice(2,11);
        this.setState({
            startsAt: this.props.currentlesson.startsAt,
        })

    }

    handleMinuteStartChange(event) {
        let tmp = this.props.currentlesson.startsAt;
        this.props.currentlesson.startsAt = tmp.slice(0,3) + event.target.value + this.props.currentlesson.startsAt.slice(5,11);
        this.setState({
            startsAt: this.props.currentlesson.startsAt,
        })
    }

    handleTimeOfDayStartChange(event) {
        this.props.currentlesson.startsAt = this.props.currentlesson.startsAt.slice(0,9) + event.target.value;
        this.setState({
            startsAt: this.props.currentlesson.startsAt,
        })
    }

    handleHourEndChange(event) {
        this.props.currentlesson.endsAt = event.target.value + this.props.currentlesson.endsAt.slice(2,11);
        this.setState({
            endsAt: this.props.currentlesson.endsAt,
        })

    }

    handleMinuteEndChange(event) {
        let tmp = this.props.currentlesson.endsAt;
        this.props.currentlesson.endsAt = tmp.slice(0,3) + event.target.value + this.props.currentlesson.endsAt.slice(5,11);
        this.setState({
            endsAt: this.props.currentlesson.endsAt,
        })
    }

    handleTimeOfDayEndChange(event) {
        this.props.currentlesson.endsAt = this.props.currentlesson.endsAt.slice(0,9) + event.target.value;
        this.setState({
            endsAt: this.props.currentlesson.endsAt,
        })
    }

    handleMonthChange(event) {
        this.props.currentlesson.date = event.target.value + this.props.currentlesson.date.slice(3);
        this.setState({
            date: this.props.currentlesson.date,
        })
    }

    handleDayChange(event) {
        let pattern = /[0-9]{1,2},/g;
        let day = this.props.currentlesson.date.match(pattern)[0];
        day = day.substring(0, day.length-1);
        this.props.currentlesson.date = this.props.currentlesson.date.replace(day, event.target.value);
        this.setState({
            date: this.props.currentlesson.date,
        })
    }

    handleYearChange(event) {
        let pattern = /[0-9]{4}/g;
        let year = this.props.currentlesson.date.match(pattern)[0];
        this.props.currentlesson.date = this.props.currentlesson.date.replace(year, event.target.value);
        this.setState({
            date: this.props.currentlesson.date,
        })
    }

    componentDidMount() {
        this.setState({
            group: this.props.currentlesson.studentsGroup.name,
        })
    }

    updateLesson(){
        const {currentlesson} = this.props;
        this.props.onHide();
        this.props.save(currentlesson);
    }
    render() {
        return (
            <Modal
                {...this.props}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        {this.props.currentlesson.subject.name}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form>
                        <h4>{this.props.currentlesson.lecturer.name} {this.props.currentlesson.lecturer.surname}</h4>
                        <label>Students Group{' '}
                            <select value={this.props.currentlesson.studentsGroup.name} onChange={this.handleGroupChange}>
                                {this.renderGroups(this.props.allgroups)}
                            </select>
                        </label>
                        <br/>
                        <label>{'\n'}Date{' '}
                            {this.renderMonth()}
                            {' '}
                            {this.renderDay()}
                            {' '}
                            {this.renderYear()}
                        </label>
                        <br/>
                        <label>Starts at{' '}
                            {this.renderHoursStart()}
                            {' '}
                            {this.renderMinutesStart()}
                            {' '}
                            {this.renderTimeOfDayStart()}
                        </label>
                        <br/>
                        <label>Ends at{' '}
                            {this.renderHoursEnd()}
                            {' '}
                            {this.renderMinutesEnd()}
                            {' '}
                            {this.renderTimeOfDayEnd()}
                        </label>
                        <br/>
                        <label>Classroom{' '}
                            <select value={this.props.currentlesson.classroom.number} onChange={this.handleClassroomChange}>
                                {this.renderClassrooms(this.props.allclassrooms)}
                            </select>
                        </label>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    {/*<Button onClick={this.props.onHide}>Close</Button>*/}
                    <Button onClick={this.updateLesson}>Save</Button>
                </Modal.Footer>
            </Modal>
        );
    }
}
