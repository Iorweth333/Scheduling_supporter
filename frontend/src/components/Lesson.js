import React, {Component} from 'react';
import {Modal, Button} from 'react-bootstrap';

export default class Lesson extends Component {

    /*constructor(props) {
        super(props);

        this.state = {
            groups: []
        };
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({
            groups: this.renderGroups(this.props.allGroups)
        });
    }*/

    renderGroups(allGroups) {
        return allGroups.map( (group, index) => {
            return (
                <option value={group.name}>{group.name}</option>
            )
        } );
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
                        {this.props.currentlesson.studentsGroup.name}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form>
                        <h4>{this.props.currentlesson.lecturer.name} {this.props.currentlesson.lecturer.surname}</h4>
                        <label>Students Group{' '}
                            <select value={this.props.currentlesson.studentsGroup.name}>
                                {this.renderGroups(this.props.allGroups)}
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
                            <input type="text" value={this.props.currentlesson.classroom.number}/>
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
