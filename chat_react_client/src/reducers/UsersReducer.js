export default function (state = [], action) {
    switch (action.type) {
        case 'SELECT_USER':
            return action.user;
        case 'ADD_FRIEND_SUCCESSFUL':
        case 'ADD_FRIEND_FAIL':
        case 'GET_FRIENDS_SUCCESSFUL':
        case 'GET_FRIENDS_FAIL':
            const us = action.users && action.users.length > 0 ? action.users : [];
            return us;
        default:
    }

    return state;
}