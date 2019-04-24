import React from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import App from "./components/App";
import Upload from "./components/Upload";
import Xlsx from "./components/Xlsx";
import Xlsx2 from "./components/Xlsx2";

export default () => (
    <BrowserRouter>
        <Switch>
            <Route exact path="/" component={App}/>
            <Route exact path="/upload" component={Upload}/>
            <Route exact path="/lessons" component={Xlsx}/>
            <Route exact path="/lessons2" component={Xlsx2}/>
        </Switch>
    </BrowserRouter>
);