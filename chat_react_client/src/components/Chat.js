import React from "react";
import "../styles.css";
// import ChatFeed from "react-chat-ui";/
import { ChatFeed, ChatBubble, BubbleGroup, Message } from "react-chat-ui";

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


class Chat extends React.Component {
    constructor() {
        super();

        this.state = {
            messages: [
                new Message({ id: "Mark", message: "Hey guys!", senderName: "Mark" })
            ]
        }
        
    }

    render() {
        return (
            <div className="container">
                <div className="chatfeed-wrapper">
                    <ChatFeed
                        messages={this.state.messages} // Boolean: list of message objects
                        showSenderName
                    />

                    <form onSubmit={e => { e.preventDefault(); this.props.addMessage(this.props.thisUser, this.props.user, e.target.value)}}>
                        <input
                            placeholder="Type a message..."
                            className="message-input"
                        />
                    </form>
                </div>
            </div>
        );
    }
}

export default Chat;
