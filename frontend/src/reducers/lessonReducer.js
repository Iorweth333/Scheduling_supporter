import {FETCH_LESSONS} from "../actions";
import {FETCH_LESSON} from "../actions";

const initialState = {
    lessons: [],
    loading: true,
    error: null
};


export default function(state = initialState, action){
    switch(action.type){
        case FETCH_LESSONS:
            return { ...state, lessons: action.payload, loading: false, error: null };
        case FETCH_LESSON:
            return {lesson: action.payload};
        default:
            return { ...state, lessons: action.payload, loading: true, error: null };
    }
}