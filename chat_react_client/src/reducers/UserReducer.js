export default function (state = null, action) {
    switch (action.type) {
        case 'SELECT_USER':
            return action.user;
        default:
    }
    return null;
}