import {FETCH_LESSONS} from "../actions";

const initialState = {
    lessons: [],
    loading: true,
    error: null
};


export default function(state = initialState, action){
    switch(action.type){
        case FETCH_LESSONS:
            return { ...state, lessons: action.payload, loading: false, error: null };
        default:
            return state;
    }
}