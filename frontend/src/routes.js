import React from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import App from "./components/App";
import Upload from "./components/Upload";
import SchedulerCalendar from "./components/SchedulerCalendar";
import Conflicts from "./components/Conflicts";
import ClassroomsCalendar from "./components/ClassroomsCalendar";

export default () => (
    <BrowserRouter>
        <Switch>
            <Route exact path="/" component={App}/>
            <Route exact path="/upload" component={Upload}/>
            <Route exact path="/lessons" component={SchedulerCalendar}/>
            <Route exact path="/classrooms" component={ClassroomsCalendar}/>
        </Switch>
    </BrowserRouter>
);