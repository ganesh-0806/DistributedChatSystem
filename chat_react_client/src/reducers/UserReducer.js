export default function (state = null, action) {

    console.log("In user action", action);
    switch (action.type) {
        case 'SELECT_USER':
            if(action.user == null) 
                return state;
            return action.user;
        default:
    }
    return state;
}