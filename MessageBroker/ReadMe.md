## Message Broker

This component is to directly interact with reactJS UI. Each client is seperately handler and all the handler work concurrently. The message delivery uses the concept of Pub-Sub (push-pull) which delivers the messages if the client is active or else store it in memory to deliver them at later point of time.

# Prerquisites
JVM
JDK
maven

# How to run
1. Open the maven project in intelliJ or Eclipse
2. Install maven dependencies for the project
3. Open terminal and go to root folder
4. Create the application jar file
  mvn clean package
5. Run the application
  java -jar <generated jar file in target folder>
  
Note: If you need to run on local host, update the ip addresses accordingly
