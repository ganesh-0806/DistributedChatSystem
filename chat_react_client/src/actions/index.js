export function messageReceived(from, msg) {
    return {
        type: 'TEXT_MESSAGE',
        fromUser: from,
        message: msg
    }
}

export function loginSuccessAck(user) {
    return {
        type: 'USER_LOGIN_SUCCESSFUL',
        thisUser: user
    }
}

export function loginFailAck() {
    return {
        type: 'USER_LOGIN_FAIL',
        thisUser: ''
    }
}

export function logoutSuccessAck() {
    return {
        type: 'USER_LOGOUT_SUCCESSFUL',
        thisUser: ''
    }
}

export function logoutFailAck(user) {
    return {
        type: 'USER_LOGOUT_FAIL',
        thisUser: user
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