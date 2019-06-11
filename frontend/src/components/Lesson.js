import React, {Component} from 'react';
import {Modal, Button} from 'react-bootstrap';
import {forEach} from "react-bootstrap/es/utils/ElementChildren";

export default class Lesson extends Component {

    constructor(props) {
        super(props);

        this.state = {
            group: this.props.currentlesson.studentsGroup.name,
            classroom: this.props.currentlesson.classroom.number,
        };

        this.handleGroupChange = this.handleGroupChange.bind(this);
        this.handleClassroomChange = this.handleClassroomChange.bind(this);
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
                        <label>Starts at{' '}</label>
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
