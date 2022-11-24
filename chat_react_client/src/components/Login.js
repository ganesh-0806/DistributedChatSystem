import React, { Component } from 'react';
import MessageType from '../utils/MessageType';

class Login extends Component {

    handleSubmit = (event) => {

        event.preventDefault();
        this.props.handleAuth(MessageType.ADD_USER);
        /*// Prevent page reload
        event.preventDefault();

        var { uname, pass } = document.forms[0];

        const socket = Singleton.getInstance();
        let messageDto = JSON.stringify({ user: this.props.thisUser, data: this.state.inputValue, type: MessageType.ADD_USER });
        socket.send(messageDto);
        this.setState({ inputValue: '' })*/

    };

    render() {
        return (
            <div className="login" id="login">
                <h2>Login</h2>
                <div className="form">
                    <form onSubmit={(event) => this.handleSubmit(event)}>
                    <div className="input-container">
                        <label>Username </label>
                        <input type="text" name="uname" onChange={(event) => {this.props.handleUserChange(event)}} required />
                    </div>
                    <div className="input-container">
                        <label>Password </label>
                        <input type="password" name="pass" onChange={(event) => {this.props.handlePassChange(event)}} required />
                    </div>
                    <div className="button-container">
                        <input type="submit" value="Login"/>
                    </div>
                    </form>
                </div>
            </div>
        )
    }

}


export default Login;