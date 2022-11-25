export default function (state = [], action) {
    switch (action.type) {
        case 'TEXT_MESSAGE':
        return state.concat([
            {
                fromUser: action.fromUser,
                toUser: action.toUser,
                message: action.message,
            }
            ])
        default:
    }
    return state;
}