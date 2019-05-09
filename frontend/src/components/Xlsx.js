import React, {Component} from 'react';
import XLSX from 'xlsx';
import 'bootstrap/dist/css/bootstrap.css';
import {connect} from 'react-redux';
import {uploadFile2} from "../actions";
import SchedulerCalendar from "./Upload";
import {Field} from "redux-form";
import Products from "./Products"


class SheetJSApp extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            cols: []
        };
        this.handleFile = this.handleFile.bind(this);
        this.exportFile = this.exportFile.bind(this);
    };
    handleFile(file) {
        const reader = new FileReader();
        const rABS = !!reader.readAsBinaryString;
        reader.onload = (e) => {
            const bstr = e.target.result;
            const wb = XLSX.read(bstr, {type:rABS ? 'binary' : 'array'});
            const wsname = wb.SheetNames[0];
            const ws = wb.Sheets[wsname];
            const data = XLSX.utils.sheet_to_json(ws, {header:1});
            this.setState({ data: data, cols: make_cols(ws['!ref']) });
        };
        if(rABS) reader.readAsBinaryString(file); else reader.readAsArrayBuffer(file);
    };
    exportFile() {
        const ws = XLSX.utils.aoa_to_sheet(this.state.data);
        const wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, ws, "lessons");
        var data = XLSX.write(wb, {bookType: 'xlsx', type: 'array'});
        this.props.uploadFile2(data);
        // XLSX.writeFile(wb, "sheetjs.xlsx")
    };
    render() {

        const { handleSubmit } = this.props;


        return (
            <div>
                <header className="headerMain">
                    <h2 className="logo">Scheduling Supporter</h2>
                </header>
                <div style={{padding: "50px"}}>
                    <h2 className="mainText">Wprowadzenie planu zajęć</h2>
                    <DragDropFile handleFile={this.handleFile}>
                        <div className="row"><div className="col-xs-12">
                            <DataInput handleFile={this.handleFile} />
                        </div></div>
                        <div className="row"><div className="col-xs-12">
                            <button disabled={!this.state.data.length} className="btn btn-success" onClick={this.exportFile}>Export</button>
                        </div></div>
                        { (this.state.data.length) ? (
                            <h2 className="mainText" style={{paddingTop: "3rem"}}>Plan zajęć: </h2>
                        ) : (
                            <div></div>
                        )
                        }
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
                            <div></div>
                        )}
                        { (this.state.data.length) ? (
                            <div className="row"><div className="col-xs-12">
                            <Products data={this.state.data} />
                            </div></div>
                        ) : (
                            <div></div>
                        )
                        }
                        {console.log(this.state.data)}
                        
                    </DragDropFile>
                </div>
            </div>
        ); };
};


class DragDropFile extends React.Component {
    constructor(props) {
        super(props);
        this.onDrop = this.onDrop.bind(this);
    };
    suppress(evt) { evt.stopPropagation(); evt.preventDefault(); };
    onDrop(evt) { evt.stopPropagation(); evt.preventDefault();
        const files = evt.dataTransfer.files;
        if(files && files[0]) this.props.handleFile(files[0]);
    };
    render() { return (
        <div onDrop={this.onDrop} onDragEnter={this.suppress} onDragOver={this.suppress}>
            {this.props.children}
        </div>
    ); };
};

class DataInput extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    };
    handleChange(e) {
        const files = e.target.files;
        if(files && files[0]) this.props.handleFile(files[0]);
    };
    render() { return (
        <form className="form-inline">
            <div className="form-group">
                <label htmlFor="file">Spreadsheet</label>
                <input type="file" className="form-control" id="file" accept={SheetJSFT} onChange={this.handleChange} />
            </div>
        </form>
    ); };
}

// class OutTable extends React.Component {
//     constructor(props) { super(props); };
//     render() { return (
//         <div className="table-responsive">
//             <table className="table table-striped">
//                 <thead>
//                 <tr>{this.props.cols.map((c) => <th key={c.key}>{c.name}</th>)}</tr>
//                 </thead>
//                 <tbody>
//                 {this.props.data.map((r,i) => <tr key={i}>
//                     {this.props.cols.map(c => <td key={c.key}>{ r[c.key] }</td>)}
//                 </tr>)}
//                 </tbody>
//             </table>
//         </div>
//     ); };
// };

const SheetJSFT = [
    "xlsx", "xlsb", "xlsm", "xls", "xml", "csv", "txt", "ods", "fods", "uos", "sylk", "dif", "dbf", "prn", "qpw", "123", "wb*", "wq*", "html", "htm"
].map(function(x) { return "." + x; }).join(",");

const make_cols = refstr => {
    let o = [], C = XLSX.utils.decode_range(refstr).e.c + 1;
    for(var i = 0; i < C; ++i) o[i] = {name:XLSX.utils.encode_col(i), key:i}
    return o;
};


function mapStateToProps(state){
    return{
        calendar: state.calendar
    };
}


export default connect(mapStateToProps, {uploadFile2})(SheetJSApp);
