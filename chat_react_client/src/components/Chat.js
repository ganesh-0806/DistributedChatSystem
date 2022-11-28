import React from "react";
import "../styles.css";
// import ChatFeed from "react-chat-ui";/
import { ChatFeed, ChatBubble, BubbleGroup, Message } from "react-chat-ui";
import { connect } from 'react-redux';

const styles = {

    button: {
        backgroundColor: "#fff",
        borderColor: "#1D2129",
        borderStyle: "solid",
        borderRadius: 20,
        borderWidth: 2,
        color: "#1D2129",
        fontSize: 18,
        fontWeight: "300",
        paddingTop: 8,
        paddingBottom: 8,
        paddingLeft: 16,
        paddingRight: 16,
        outline: "none"
    },
    selected: {
        color: "#fff",
        backgroundColor: "#0084FF",
        borderColor: "#0084FF"
    }
};

const customBubble = props => (
    <div>
        <p>{`${props.message.senderName} ${props.message.id ? "says" : "said"}: ${props.message.message}`}</p>
    </div>
);


class Chat extends React.Component {
    constructor() {
        super();

        /*this.state = {
            messages: [
                new Message({ id: "Mark", message: "Hey guys!", senderName: "Mark" })
            ]
        }*/

    }

    handleSubmit = (event) => {
        event.preventDefault();
        this.props.handleMessage(this.message.value);
        this.message.value = '';
    }

    render() {

        if(this.props.modalOpen || this.props.thisUser == '' )
            return null;

        const msgs = this.props.messages.map((message, i) => {
            if(message.toUser == this.props.thisUser) {
                return new Message({id: this.props.thisUser, message: message.message, senderName: message.fromUser});
            }
            else if(message.fromUser == this.props.thisUser) {
                return new Message({id: 0, message: message.message, senderName: message.fromUser});
            }
        });

        
        return (
            <div className="container"> 
                <label> Friend: {this.props.selectUser}</label>
                <div className="chatfeed-wrapper">
                    <ChatFeed
                        chatBubble={customBubble}
                        messages={msgs} // Boolean: list of message objects
                        showSenderName
                        bubblesCentered={false}
                    />

                    <form onSubmit={e => { console.log(this.message); this.handleSubmit(e) }} >
                        <input
                            ref={m => { this.message = m; }}
                            placeholder="Type a message..."
                            className="message-input"
                        />
                    </form>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        messages: state.messages,
        thisUser: state.thisUser
    }
}

export default connect(mapStateToProps)(Chat);
