export default function (state = null, action) {
    switch (action.type) {
        case 'USER_LOGIN_SUCCESSFUL':
            console.log("reached reducer");
            return action.user;
        case 'USER_LOGIN_FAIL':
            return '';
        case 'USER_LOGOUT_SUCCESSFUL':
            return '';
        case 'USER_LOGOUT_FAIL':
            return action.user;
        default:
    }

    return state;
}