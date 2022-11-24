import React, { Component } from 'react';
import Dialog from '@mui/material/Dialog';
import Login from './Login';
import MessageType from '../utils/MessageType';
import Singleton from '../utils/Socket'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as actions from '../actions/index';


class App extends Component {

    constructor() {
        super();

        this.registerSocket();

        this.state = {
            modalOpen: true,
            userName: '',
            userPassword: ''
        }
    }

    updateUserName = (value) => {
        console.log(value);
        this.setState({userName: value});
    };

    updatePassword = (value) => {
        console.log(value);
        this.setState({userPassword: value});
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
        //const chat = this.state.modalOpen ? '' : <Chat />
        return (
            <div className="App">
                <Dialog
                    modal={true}
                    open={this.state.modalOpen}>
                    <Login
                    handleAuth={(type) => this.handleAuth(type)}
                    handleUserChange = {(event) => this.updateUserName(event.target.value)}
                    handlePassChange = {(event) => this.updatePassword(event.target.value)}
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
                    break;
                case MessageType.USER_LOGIN_SUCCESSFUL:
                    console.log("In switch response successful");
                    self.setState({modalOpen: false});
                    break;
                case MessageType.USER_LOGOUT_FAIL:
                case MessageType.USER_LOGOUT_SUCCESSFUL:
                case MessageType.TEXT_MESSAGE:
                    break;
                case MessageType.GET_FRIENDS_SUCCESSFUL:
                case MessageType.GET_FRIENDS_FAIL:
                case MessageType.ADD_FRIEND_SUCCESSFUL:
                case MessageType.ADD_FRIEND_FAIL:

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
        thisUser: state.thisUser
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
        messageReceived: actions.messageReceived
    }, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(App); //props, actions(dispatch)