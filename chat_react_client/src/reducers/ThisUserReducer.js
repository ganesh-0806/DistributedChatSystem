export default function (state = null, action) {
    switch (action.type) {
        case 'USER_LOGIN_SUCCESSFUL':
            return action.thisUser;
        case 'USER_LOGIN_FAIL':
            return action.thisUser;
        case 'USER_LOGOUT_SUCCESSFUL':
            return action.thisUser;
        case 'USER_LOGOUT_FAIL':
            return action.thisUser;
        default:
    }

    return state;
}