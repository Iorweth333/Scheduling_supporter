import {UPLOAD_FILE} from "../actions";

export default function(state = null, action){
    switch(action.type){
        case UPLOAD_FILE:
            return action.payload;
    }
    return state;
}