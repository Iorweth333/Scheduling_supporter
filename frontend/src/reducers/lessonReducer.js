import {FETCH_CONFLICTS, FETCH_LESSONS} from "../actions";

const initialState = {
    lessons: [],
    loading: true,
    error: null
};


export default function(state = initialState, action){
    switch(action.type){
        case FETCH_LESSONS:
            return { ...state, lessons: action.payload, loading: false, error: null };
        case FETCH_CONFLICTS:
            return { ...state, conflicts: action.payload, loading: false, error: null };
        default:
            return { ...state, lessons: action.payload, loading: true, error: null };
    }
}