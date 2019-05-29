import {FETCH_CONFLICTS} from "../actions";

const initialState = {
    conflicts: [],
    loading: true,
    error: null
};

export default function(state = initialState, action){
    switch(action.type){
        case FETCH_CONFLICTS:
            return { ...state, conflicts: action.payload, loading: false, error: null };
        default:
            return state;
    }
}