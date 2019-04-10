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
                        <SchedulerCalendar/>
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


