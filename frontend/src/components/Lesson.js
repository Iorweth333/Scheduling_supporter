import React, {Component} from 'react';
import {Modal, Button} from 'react-bootstrap';

export default class Lesson extends Component {

    constructor(props) {
        super(props);

        this.state = {
            group: this.props.currentlesson.studentsGroup.name,
            classroom: this.props.currentlesson.classroom.number,
            startsAt: this.props.currentlesson.startsAt,
            endsAt: this.props.currentlesson.endsAt,
        };

        this.handleGroupChange = this.handleGroupChange.bind(this);
        this.handleClassroomChange = this.handleClassroomChange.bind(this);
        this.handleTimeOfDayStartChange = this.handleTimeOfDayStartChange.bind(this);
        this.handleHourStartChange = this.handleHourStartChange.bind(this);
        this.handleMinuteStartChange = this.handleMinuteStartChange.bind(this);
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
        console.log(minute);
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
        this.props.currentlesson.studentsGroup.name = event.target.value;
        this.setState({
            group: event.target.value,
        })
    }

    handleClassroomChange(event) {
        this.props.currentlesson.classroom.number = event.target.value;
        this.setState({
            classroom: event.target.value,
        })
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

    componentDidMount() {
        this.setState({
            group: this.props.currentlesson.studentsGroup.name,
        })
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
                        <label>{'\n'}Date{' '}</label>
                        <br/>
                        <label>Starts at{' '}{this.props.currentlesson.startsAt}
                            {this.renderHoursStart()}
                            {' '}
                            {this.renderMinutesStart()}
                            {' '}
                            {this.renderTimeOfDayStart()}
                        </label>
                        <br/>
                        <label>Ends at{' '}</label>
                        <br/>
                        <label>Classroom{' '}
                            <select value={this.props.currentlesson.classroom.number} onChange={this.handleClassroomChange}>
                                {this.renderClassrooms(this.props.allclassrooms)}
                            </select>
                        </label>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onHide}>Close</Button>
                </Modal.Footer>
            </Modal>
        );
    }
}
