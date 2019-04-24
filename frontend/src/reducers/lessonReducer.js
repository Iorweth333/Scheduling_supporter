import {FETCH_LESSONS, UPLOAD_FILE} from "../actions";

export default function(state = null, action){
    switch(action.type){
        case FETCH_LESSONS:
            console.log(action);
            return action.payload;
        case UPLOAD_FILE:
            console.log(action);
    }
    return state;
}