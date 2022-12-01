import { combineReducers } from 'redux';
import UsersReducer from './UsersReducer';
import UserReducer from './UserReducer';
import MessagesReducer from './MessagesReducer';
import ThisUserReducer from './ThisUserReducer';

const rootReducer = combineReducers({
    users: UsersReducer,
    user: UserReducer,
    messages: MessagesReducer,
    thisUser: ThisUserReducer
});

export default rootReducer;