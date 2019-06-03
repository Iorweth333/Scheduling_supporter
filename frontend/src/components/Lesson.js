import React, {Component} from 'react';
import {Modal, Button} from 'react-bootstrap';
import { Field, reduxForm } from 'redux-form';

export default class Lesson extends Component {
    render() {
        const { handleSubmit, pristine, reset, submitting } = this.props;
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
                    <h4>{this.props.currentlesson.lecturer.name} {this.props.currentlesson.lecturer.surname}</h4>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onHide}>Close</Button>
                </Modal.Footer>
            </Modal>
        );
    }
}
