import React, {Component} from 'react';

class Conflicts extends Component {
    constructor(props) {
        super(props);

        document.title = "Scheduling Supporter";

        this.state = {
            conflicts: []
        };
    }

    parseConflicts(conflicts) {
        return conflicts.map( conflict => {
            return (
                <div className="conflict">
                    <p>{ conflict.typeOfConflict }</p>
                    <p>{ conflict.lessonId1 }</p>
                    <p>{ conflict.lessonId2 }</p>
                </div>
        )
        });
    }

    componentDidMount() {
        fetch("http://localhost:8080/conflicts")
            .then(response => response.json())
            .then(conflicts => {
                this.setState({conflicts: this.parseConflicts(conflicts)});
            });
    }

    render() {
        return (
            <div className="Conflicts">
                {this.state.conflicts}
            </div>
        )
    }
}

export default Conflicts;