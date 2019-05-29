import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';
import calendarReducer from './calendarReducer';
import lessonReducer from "./lessonReducer";
import conflictsReducer from "./conflictsReducer";

const rootReducer = combineReducers({
    form: formReducer,
    calendar: calendarReducer,
    lessons: lessonReducer,
    conflicts: conflictsReducer,
    lesson: lessonReducer,
});

export default rootReducer;