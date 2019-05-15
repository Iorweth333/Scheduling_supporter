import React from 'react';

export default class Subjects extends React.Component {

    constructor(props) {
      super(props);
      
      //  this.state.subjects = [];
      this.state = {};
      this.state.filterText = "";
      var data = this.convert(this.props.data)
      this.state.subjects = data;
    }
    handleUserInput(filterText) {
      this.setState({filterText: filterText});
    };
    handleRowDel(subject) {
      var index = this.state.subjects.indexOf(subject);
      this.state.subjects.splice(index, 1);
      this.setState(this.state.subjects);
      this.props.changeParentData(this.state.subjects);
    };
    convert(data) {
      var subjects = []
      for(var subject in data){
        var obj = {}
        obj.id = subject
        obj.Zjazd_nr = data[subject][0]
        obj.Data = data[subject][1]
        obj.Godziny = data[subject][2]
        obj.G1_przedmiot = data[subject][3]
        obj.G1_sala = data[subject][4]
        obj.G1_prowadzacy = data[subject][5]
        obj.G2_przedmiot = data[subject][6]
        obj.G2_sala = data[subject][7]
        obj.G2_prowadzacy = data[subject][8]
        subjects.push(obj)
      }
      return subjects
    }
  
    handleAddEvent(evt) {
      var id = (+ new Date() + Math.floor(Math.random() * 999999)).toString(36);
      var subject = {
        id: id,
        Zjazd_nr: "",
        Data: "",
        Godziny: "",
        G1_prowadzacy: "",
        G1_sala: "",
        G1_przedmiot: "",
        G2_prowadzacy: "",
        G2_sala: "",
        G2_przedmiot: "",
      }
      this.state.subjects.push(subject);
      this.setState(this.state.subjects);
      this.props.changeParentData(this.state.subjects);
  
    }
  
    handleSubjectTable(evt) {
      var item = {
        id: evt.target.id,
        name: evt.target.name,
        value: evt.target.value
      };
  var subjects = this.state.subjects.slice();
    var newSubjects = subjects.map(function(subject) {
  
      for (var key in subject) {
        if (key == item.name && subject.id == item.id) {
          subject[key] = item.value;
  
        }
      }
      return subject;
    });
      this.setState({subjects:newSubjects});
      this.props.changeParentData(this.state.subjects);
    };
    render() {
  
      return (
        <div>
          <SearchBar filterText={this.state.filterText} onUserInput={this.handleUserInput.bind(this)}/>
          <SubjectTable onSubjectTableUpdate={this.handleSubjectTable.bind(this)} onRowAdd={this.handleAddEvent.bind(this)} onRowDel={this.handleRowDel.bind(this)} subjects={this.state.subjects} filterText={this.state.filterText}/>
        </div>
      );
  
    }
  
  }
  class SearchBar extends React.Component {
    handleChange() {
      this.props.onUserInput(this.refs.filterTextInput.value);
    }
    render() {
      return (
        <div>
  
        <input type="text" placeholder="Search..." value={this.props.filterText} ref="filterTextInput" onChange={this.handleChange.bind(this)}/>
  
        </div>
  
      );
    }
  
  }
  
  class SubjectTable extends React.Component {
  
    render() {
      var onSubjectTableUpdate = this.props.onSubjectTableUpdate;
      var rowDel = this.props.onRowDel;
      var filterText = this.props.filterText;
      var id = 0;
      var subject = this.props.subjects.map(function(subject) {
         if (subject.G1_prowadzacy.toLowerCase().indexOf(filterText.toLowerCase()) === -1 && subject.G2_prowadzacy.toLowerCase().indexOf(filterText.toLowerCase()) && subject.G1_przedmiot.toLowerCase().indexOf(filterText.toLowerCase()) && subject.G2_przedmiot.toLowerCase().indexOf(filterText.toLowerCase())) {
           return;
         }
      //   if (subject[3] === filterText) {
      //     return;
      //  }
        subject.id = id;
        id=id+1
        return (<SubjectRow onSubjectTableUpdate={onSubjectTableUpdate} subject={subject} onDelEvent={rowDel.bind(this)} key={subject.id}/>)
      });
      return (
        <div>
  
  
        <button type="button" onClick={this.props.onRowAdd} className="btn btn-success pull-right">Add</button>
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>Zjazd nr</th>
                <th>Data</th>
                <th>Godziny</th>
                <th>Grupa 1 - Przedmiot</th>
                <th>Sala</th>
                <th>Grupa 1 - Prowadzacy</th>
                <th>Grupa 2 - Przedmiot</th>
                <th>Sala</th>
                <th>Grupa 2 - Prowadzacy</th>
              </tr>
            </thead>
  
            <tbody>
              {subject}
  
            </tbody>
  
          </table>
        </div>
      );
  
    }
  
  }
  
  class SubjectRow extends React.Component {
    onDelEvent() {
      this.props.onDelEvent(this.props.subject);
  
    }
    render() {
  
      return (
        <tr className="eachRow">
          <EditableCell onSubjectTableUpdate={this.props.onSubjectTableUpdate} cellData={{
            type: "Zjazd_nr",
            value: this.props.subject.Zjazd_nr,
            id: this.props.subject.id
          }}/>
          <EditableCell onSubjectTableUpdate={this.props.onSubjectTableUpdate} cellData={{
            type: "Data",
            value: this.props.subject.Data,
            id: this.props.subject.id
          }}/>
          <EditableCell onSubjectTableUpdate={this.props.onSubjectTableUpdate} cellData={{
            type: "Godziny",
            value: this.props.subject.Godziny,
            id: this.props.subject.id
          }}/>
          <EditableCell onSubjectTableUpdate={this.props.onSubjectTableUpdate} cellData={{
            type: "G1_przedmiot",
            value: this.props.subject.G1_przedmiot,
            id: this.props.subject.id
          }}/>
          <EditableCell onSubjectTableUpdate={this.props.onSubjectTableUpdate} cellData={{
            type: "G1_sala",
            value: this.props.subject.G1_sala,
            id: this.props.subject.id
          }}/>
          <EditableCell onSubjectTableUpdate={this.props.onSubjectTableUpdate} cellData={{
            type: "G1_prowadzacy",
            value: this.props.subject.G1_prowadzacy,
            id: this.props.subject.id
          }}/>
          <EditableCell onSubjectTableUpdate={this.props.onSubjectTableUpdate} cellData={{
            type: "G2_przedmiot",
            value: this.props.subject.G2_przedmiot,
            id: this.props.subject.id
          }}/>
          <EditableCell onSubjectTableUpdate={this.props.onSubjectTableUpdate} cellData={{
            type: "G2_sala",
            value: this.props.subject.G2_sala,
            id: this.props.subject.id
          }}/>
          <EditableCell onSubjectTableUpdate={this.props.onSubjectTableUpdate} cellData={{
            type: "G2_prowadzacy",
            value: this.props.subject.G2_prowadzacy,
            id: this.props.subject.id
          }}/>
          <td className="del-cell">
            <input type="button" onClick={this.onDelEvent.bind(this)} value="X" className="del-btn"/>
          </td>
        </tr>
      );
  
    }
  
  }
  class EditableCell extends React.Component {
  
    render() {
      return (
        <td>
          <input type='text' name={this.props.cellData.type} id={this.props.cellData.id} value={this.props.cellData.value} onChange={this.props.onSubjectTableUpdate}/>
        </td>
      );
  
    }
  
  }
  //ReactDOM.render( < subjects / > , document.getElementById('container'));
  