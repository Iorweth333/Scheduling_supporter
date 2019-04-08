import React, {Component} from 'react';
import { Field, reduxForm } from 'redux-form';
import {connect} from 'react-redux';
import {uploadFile} from "../actions";
import SchedulerCalendar from "./SchedulerCalendar";


const UploadFile = ({ input: {value: omitValue, ...inputProps }, meta: omitMeta, ...props }) => (
    <input type='file' {...inputProps} {...props} />
);


class Upload extends Component{

    constructor (props) {
        super(props);
        this.state = {
            events:[
                {
                    title: 'All Day Event',
                    start: '2019-04-08'
                },
                {
                    id: 997,
                    title: 'Jjsajajj',
                    start: '2019-04-01T16:00:00'
                },
                {
                    id: 999,
                    title: 'Ababba',
                    start: '2019-04-01T16:00:00'
                },
                {
                    title: 'Conference',
                    start: '2017-05-11',
                    end: '2017-05-13'
                },
                {
                    title: 'Meeting',
                    start: '2019-04-12T10:30:00',
                    end: '2019-04-12T12:30:00'
                },
                {
                    title: 'Click for Google',
                    url: 'http://google.com/',
                    start: '2019-04-14'
                }
            ],
        };
    }

    render(){
        const { handleSubmit } = this.props;
        return(
            <div>
                {this.props.calendar ? (
                    <div>
                        NAME: {this.props.calendar.fileName}
                        <br/>
                        URI: {this.props.calendar.fileDownloadUri}
                        <br/>
                        SIZE: {this.props.calendar.size}

                        <SchedulerCalendar events={this.state.events}/>
                    </div>
                ) : (
                    <form onSubmit={handleSubmit(this.props.uploadFile)}>

                        <Field component={UploadFile} name='scheduleUpload' accept=".png,.csv,.xls,.xlsx" />

                        <button type="submit">Submit</button>
                    </form>
                )}
            </div>
        );
    }
}

function mapStateToProps(state){
    return{
        calendar: state.calendar
    };
}

export default connect(mapStateToProps, {uploadFile} )(
    reduxForm({form: "UploadFileForm"})(Upload));


