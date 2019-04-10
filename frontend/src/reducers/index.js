import { combineReducers } from 'redux';
import { reducer as formReducer } from 'redux-form';
import calendarReducer from './calendarReducer';

const rootReducer = combineReducers({
    form: formReducer,
    calendar: calendarReducer
});

export default rootReducer;