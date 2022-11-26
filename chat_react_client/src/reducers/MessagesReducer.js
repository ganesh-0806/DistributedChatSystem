import { act } from "react-dom/test-utils";

const initState = {
    messages: [{
        fromUser: '',
        toUser: '',
        message: ''
    }
    ]
}

export default function (state = initState, action) {
    switch (action.type) {
        case 'TEXT_MESSAGE':
        return [state.messages, {fromUser: action.fromUser, toUser: action.toUser, message: action.message}]
        case 'ADD_MESSAGE':
            return [state.messages, {fromUser: action.fromUser, toUser: action.toUser, message: action.message}]
        default:
    }
    return state;
}