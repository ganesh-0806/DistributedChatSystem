## Load Balancer

This component is designed to interact with Message Broker and Server components. 
The Load Balancer receives messages from Message Broker upon user submitting his/her messages. Load Balancer then, chooses which server the message should be directed to. We used a hashing protocol to determine load distribution among servers. The client user name is hashed and used to determine which server node the message will be forwarded to :

# Requirements:
1. JVM
2. JRE
3. Maven 

# Execution steps:
1. Clone the GitHub project and navigate to the loadBalancer folder where pom.xml for the Load Balancer component is available.
2. Install the maven dependencies and create jar executable file for the project using:   >maven clean package command
3. Navigate to the target folder and execute the jar file using the command > java -jar <name-of-jar-file>.jar
