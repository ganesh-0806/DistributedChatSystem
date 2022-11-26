import React, { Component } from 'react';
import Dialog from '@mui/material/Dialog';
import Login from './Login';
import MessageType from '../utils/MessageType';
import Singleton from '../utils/Socket'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as actions from '../actions/index';
import ChatNavbar from './ChatNavbar';
import Chat from "./Chat";
import Users from "./Users"


class App extends Component {

    constructor() {
        super();

        this.registerSocket();

        this.state = {
            modalOpen: true,
            userName: '',
            userPassword: '',
        }
    }

    updateUserName = (value) => {
        console.log(value);
        this.setState({ userName: value });
    };

    updatePassword = (value) => {
        console.log(value);
        this.setState({ userPassword: value });
    };

    handleAuth(type) {

        console.log(this.props.thisUser);
        const socket = Singleton.getInstance();
        let messageDto = JSON.stringify({ fromUser: this.state.userName, password: this.state.userPassword, desc: "", type: MessageType.ADD_USER });

        socket.send(messageDto);
    }

    handleSend = (message) => {
        this.props.addMessage(this.props.thisUser, this.props.user, message);
    }

    componentDidMount() {
    }

    render() {
        const chatNav = this.state.modalOpen ? '' : <ChatNavbar />
        const users = this.state.modalOpen ? '' : <Users />
        const chat = this.state.modalOpen ? '' : <Chat handleSend={(message) => this.handleSend(message)} />
        return (
            <div className="App">
                {chatNav}
                <div style={{ float: "left" , paddingRight:"200px" }}>
                    {users}
                </div>
                <div style={{ float: "right" , paddingRight:"200px" }}>
                    {chat}
                </div>
                <Dialog
                    modal={true}
                    open={this.state.modalOpen}>
                    <Login
                        handleAuth={(type) => this.handleAuth(type)}
                        handleUserChange={(event) => this.updateUserName(event.target.value)}
                        handlePassChange={(event) => this.updatePassword(event.target.value)}
                    />
                </Dialog>
            </div>
        )
    }

    registerSocket() {

        console.log("registering socket");
        let self = this;
        console.log(this.props);
        this.socket = Singleton.getInstance();

        this.socket.onmessage = (response) => {
            let message = JSON.parse(response.data);
            let users;

            switch (message.type) {
                case MessageType.USER_LOGIN_FAIL:
                    self.props.loginFailAck();
                    break;
                case MessageType.USER_LOGIN_SUCCESSFUL:
                    console.log(self.props);
                    self.props.loginSuccessAck(message.fromUser.userName);
                    self.setState({ modalOpen: false });
                    break;
                case MessageType.USER_LOGOUT_FAIL:
                    self.props.logoutFailAck(message.fromUser.userName);
                    break;
                case MessageType.USER_LOGOUT_SUCCESSFUL:
                    self.props.logoutSuccessAck();
                    self.setState({ modalOpen: true });
                    break;
                case MessageType.TEXT_MESSAGE:
                    self.props.messageReceived(message.fromUser.userName, message.toUser.userName, message.message);
                    break;
                case MessageType.GET_FRIENDS_SUCCESSFUL:
                    self.props.getFriendsSuccessAck(message.friends);
                    break;
                case MessageType.GET_FRIENDS_FAIL:
                    self.props.getFriendsFailAck();
                    break;
                case MessageType.ADD_FRIEND_SUCCESSFUL:
                    self.props.addFriendSuccessAck(message.friends[0].userName);
                    break;
                case MessageType.ADD_FRIEND_FAIL:
                    self.props.addFriendFailAck();
                    break;
                default:
            }
        }

        this.socket.onopen = () => {
            console.log('Connected socket');
        }

        window.onbeforeunload = () => {
            let messageDto = JSON.stringify({ fromUser: this.state.userName, type: MessageType.USER_LOGOUT });
            this.socket.send(messageDto);
        }
    }

}

function mapStateToProps(state) {
    return {
        messages: state.message,
        users: state.users,
        thisUser: state.thisUser,
        user: state.user
    }
}

function mapDispatchToProps(dispatch, props) {
    return bindActionCreators({
        loginSuccessAck: actions.loginSuccessAck,
        loginFailAck: actions.loginFailAck,
        logoutSuccessAck: actions.logoutSuccessAck,
        logoutFailAck: actions.logoutFailAck,
        addFriendSuccessAck: actions.addFriendSuccessAck,
        addFriendFailAck: actions.addFriendFailAck,
        getFriendsSuccessAck: actions.getFriendsSuccessAck,
        getFriendsFailAck: actions.getFriendsFailAck,
        messageReceived: actions.messageReceived,
        addMessage: actions.addMessage,
        selectUser: actions.selectUser
    }, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(App); //props, actions(dispatch)