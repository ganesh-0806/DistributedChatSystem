## DistributedChatSystem

This project has been developed as a part of CS249 SJSU course work. The project enables users to chat across their social network developed by reactJS frontend and java backend. Web Socket Server (WSS) protocol is used for interaction between the UI and the backend. The backend is further distributed into multiple components distributed across different instances. <br>

The server components is implemented to support load balancing, availability and scalability.

# Components
The components are as follows: <br>
1. Message Broker
2. Load Balancer
3. Server
4. Mongo DB

You can find individual components working in respective folders of the repo.

# Further Enhancements
Few future enhancements are: <br>
1. Distribute Message Broker and Load Balancer components
2. Add TLS security layer
3. Caching the messages at client end
4. Further support different types of messages (video, audio files)
5. Support audio and video conferencing
6. Utilization of timestamps for synchronization of messages

