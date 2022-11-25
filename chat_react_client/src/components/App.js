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


class App extends Component {

    constructor() {
        super();

        this.registerSocket();

        this.state = {
            modalOpen: false,
            userName: 'gregoti',
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


        const socket = Singleton.getInstance();
        let messageDto = JSON.stringify({ fromUser: this.state.userName, password: this.state.userPassword, desc: "", type: MessageType.ADD_USER });

        socket.send(messageDto);


        //this.setState({modalOpen: false});
    }

    componentDidMount() {
        //Fetch session
    }

    render() {
        const chat = this.state.modalOpen ? '' : <Chat />
        return (
            <div className="App">
                <ChatNavbar />
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
        this.socket = Singleton.getInstance();

        //this.socket.open();

        this.socket.onmessage = (response) => {
            let message = JSON.parse(response.data);
            let users;

            switch (message.type) {
                case MessageType.USER_LOGIN_FAIL:
                    self.props.loginFailAck();
                    break;
                case MessageType.USER_LOGIN_SUCCESSFUL:
                    console.log("In switch response successful");
                    self.props.loginSuccessAck(message.fromUser);
                    self.setState({ modalOpen: false });
                    break;
                case MessageType.USER_LOGOUT_FAIL:
                    self.props.logoutFailAck(message.fromUser);
                    break;
                case MessageType.USER_LOGOUT_SUCCESSFUL:
                    self.props.logoutSuccessAck();
                    self.setState({ modalOpen: true });
                    break;
                case MessageType.TEXT_MESSAGE:
                    self.props.messageReceived(message.fromUser,message.toUser, message.message);
                    break;
                case MessageType.GET_FRIENDS_SUCCESSFUL:
                    self.props.getFriendsSuccessAck(message.friends);
                    break;
                case MessageType.GET_FRIENDS_FAIL:
                    self.props.getFriendsFailAck();
                    break;
                case MessageType.ADD_FRIEND_SUCCESSFUL:
                    self.props.addFriendSuccessAck(message.friends[0]);
                    break;
                case MessageType.ADD_FRIEND_FAIL:
                    self.props.addFriendFailAck();
                    break;

                /* case MessageType.TEXT_MESSAGE:
                    self.props.messageReceived(message);
                    break;
                case MessageType.USER_JOINED:
                    users = JSON.parse(message.data);
                    self.props.userJoined(users);
                    break;
                case MessageType.USER_LEFT:
                    users = JSON.parse(message.data);
                    self.props.userLeft(users);
                    break;
                case MessageType.USER_JOINED_ACK:
                    let thisUser = message.user;
                    self.props.userJoinedAck(thisUser);
                    break;*/
                default:
            }
        }

        this.socket.onopen = () => {
            //TODO: 
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