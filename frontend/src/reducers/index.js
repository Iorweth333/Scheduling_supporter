import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';
import calendarReducer from './calendarReducer';
import lessonReducer from "./lessonReducer";

const rootReducer = combineReducers({
    form: formReducer,
    calendar: calendarReducer,
    lessons: lessonReducer
});

export default rootReducer;