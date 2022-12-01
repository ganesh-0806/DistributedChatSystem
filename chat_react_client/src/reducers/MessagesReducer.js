import { act } from "react-dom/test-utils";


export default function (state = null, action) {
    console.log("In messages action", action);
    switch (action.type) {
        case 'TEXT_MESSAGE':
        case 'ADD_MESSAGE':
            if(state != null) {
                return [...state, {fromUser: action.fromUser, toUser: action.toUser, message: action.message}]
            }
            else {
                return [{fromUser: action.fromUser, toUser: action.toUser, message: action.message}]
            }
            break;
        default:
    }
    return state;
}