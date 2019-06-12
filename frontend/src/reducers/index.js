import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';
import calendarReducer from './calendarReducer';
import lessonReducer from "./lessonReducer";
import conflictsReducer from "./conflictsReducer";
import classroomReducer from "./classroomReducer";

const rootReducer = combineReducers({
    form: formReducer,
    calendar: calendarReducer,
    lessons: lessonReducer,
    classrooms: classroomReducer,
    conflicts: conflictsReducer,
});

export default rootReducer;