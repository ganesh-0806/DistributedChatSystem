export function messageReceived(from, to, msg) {
    return {
        type: 'TEXT_MESSAGE',
        fromUser: from,
        toUser: to,
        message: msg
    }
}

export function addMessage(from, to, msg) {
    return {
        type: 'ADD_MESSAGE',
        fromUser: from,
        toUser: to,
        message: msg
    }
}


export function selectUser(user) {
    return {
        type: 'SELECT_USER',
        user: user
    }
}


export function loginSuccessAck(user) {
    console.log('reached action');
    return {
        type: 'USER_LOGIN_SUCCESSFUL',
        user: user
    }
}

export function loginFailAck() {
    return {
        type: 'USER_LOGIN_FAIL',
        user: ''
    }
}

export function logoutSuccessAck() {
    return {
        type: 'USER_LOGOUT_SUCCESSFUL',
        user: ''
    }
}

export function logoutFailAck(user) {
    return {
        type: 'USER_LOGOUT_FAIL',
        user: user
    }
}

export function addFriendSuccessAck(user) {
    return {
        type: 'ADD_FRIEND_SUCCESSFUL',
        users: user
    }
}

export function addFriendFailAck() {
    return {
        type: 'ADD_FRIEND_FAIL',
        users: []
    }
}

export function getFriendsSuccessAck(users) {
    return {
        type: 'GET_FRIENDS_SUCCESSFUL',
        users: users
    }
}

export function getFriendsFailAck() {
    return {
        type: 'GET_FRIENDS_FAIL',
        users: []
    }
}