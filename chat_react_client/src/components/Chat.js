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

const customBubble = props => (
    <div>
        <p>{`${props.message.senderName} ${props.message.id ? "says" : "said"}: ${props.message.message}`}</p>
    </div>
);


class Chat extends React.Component {
    constructor() {
        super();

        this.state = {
            messages: [
                new Message({ id: "Mark", message: "Hey guys!", senderName: "Mark" })
            ]
        }

    }

    handleSubmit = (event) => {
        event.preventDefault();
        console.log(this.message.value, this.props.thisUser);
        this.setState({
            messages: [...this.state.messages, new Message({ id: 0, message: this.message.value, senderName: "You" })]
        });
        this.props.handleSend(this.message.value);
        this.message.value = '';
        console.log(this.props);
    }

    render() {
        return (
            <div className="container">
                <div className="chatfeed-wrapper">
                    <ChatFeed
                        chatBubble={customBubble}
                        messages={this.state.messages} // Boolean: list of message objects
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

export default Chat;
