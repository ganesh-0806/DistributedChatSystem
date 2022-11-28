
export default function (state = [{"userName": "abc"}, {"userName":"def"}], action) {
    switch (action.type) {
        case 'ADD_FRIEND_SUCCESSFUL':
            return [state.users, action.user];
        case 'ADD_FRIEND_FAIL':
            return state.users;
        case 'GET_FRIENDS_SUCCESSFUL':
            return action.users;
        case 'GET_FRIENDS_FAIL':
            return state.users
        default:
    }

    return state;
}