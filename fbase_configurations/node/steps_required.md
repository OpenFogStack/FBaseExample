# Info

The nodes will update their configs at the naming service after they started.

.json files contain the node configs (if needed, e.g. for initial node for NamingService)
.properties files contain the properties required by node on startup

# Todo for all nodes

1. Register node via the raid node (raid node is already registered)
1. Start node with path to its properties file (NODE MUST BE ADDED TO NamingService ALREADY, OTHERWISE IT CANNOT ANNOUNCE IT MACHINES)
 * Raid: `java -jar FBase/target/fbase-0.0.1-SNAPSHOT-jar-with-dependencies.jar /vagrant/fbase_configurations/node/raid.properties`
 * Floor0: `java -jar FBase/target/fbase-0.0.1-SNAPSHOT-jar-with-dependencies.jar /vagrant/fbase_configurations/node/floor0.properties`
 * Floor1: `java -jar FBase/target/fbase-0.0.1-SNAPSHOT-jar-with-dependencies.jar /vagrant/fbase_configurations/node/floor1.properties`
 * Floor2: `java -jar FBase/target/fbase-0.0.1-SNAPSHOT-jar-with-dependencies.jar /vagrant/fbase_configurations/node/floor2.properties`
