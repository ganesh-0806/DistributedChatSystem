## Server

This component interacts with Database, BrokerHandler and BalancerHandler. Messages from clients are received and categorized according to type in BalancerHandler. 
They are then sent to BrokerHandler which talks with the database in order to store this information. Server futher implements ping-ack protocol for fault detection and to get notified about the joined server nodes.

# Requirements:
1. JVM
2. JRE
3. Maven 

# Execution steps:
1. Clone the GitHub project and navigate to the Server folder where pom.xml for the Server component is available.
2. Install the maven dependencies and create jar executable file for the project using: <br>
    > maven clean package command
3. Navigate to the target folder and execute the jar file using the command <br>
    > java -jar \<name-of-jar-file\>.jar
