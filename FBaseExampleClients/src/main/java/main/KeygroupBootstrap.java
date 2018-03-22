package main;

import client.KeygroupRequest;
import client.NodeRequest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.JSONable;
import model.config.KeygroupConfig;
import model.config.NodeConfig;
import model.config.ReplicaNodeConfig;
import model.config.TriggerNodeConfig;
import model.data.NodeID;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class KeygroupBootstrap {

	private static Logger logger = Logger.getLogger(KeygroupBootstrap.class.getName());

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		String address = args[0];
		int port = Integer.parseInt(args[1]);

		KeygroupBootstrap r = new KeygroupBootstrap(address, port);

		for (int i = 2; i < args.length; i++) {
			String pathToKeygroupConfig = args[i];
			r.bootstrapKeygroup(pathToKeygroupConfig);
		}

	}

	String address;
	int port;

	public KeygroupBootstrap(String address, int port) {
		this.address = address;
		this.port = port;
	}


	public void bootstrapKeygroup(String pathToKeygroupConfig) throws FileNotFoundException, InterruptedException {
		KeygroupRequest keygroupRequest = new KeygroupRequest(address, port);
		FileInputStream fileIS = new FileInputStream(pathToKeygroupConfig);
		KeygroupConfig config = JSONable.fromJSON(fileIS, KeygroupConfig.class);

		try {
			keygroupRequest.createKeygroup(config);
			logger.info("Created keygroup config " + config.getKeygroupID());
		} catch (UnirestException e) {
			logger.error("Could not create keygroup config " + config.getKeygroupID(), e);
			return;
		}

		Thread.sleep(1000); // let's wait a second

		// now add the first (other) replica node -> first replica node is the raid because it forwards create request
		NodeID replicaNodeId = new NodeID(config.getKeygroupID().getGroup()); // strong assumption, but true for example
		ReplicaNodeConfig replicaNodeConfig = new ReplicaNodeConfig(replicaNodeId);

		try {
			keygroupRequest.addReplicaNode(config.getKeygroupID(), replicaNodeConfig);
			logger.info("Added replica node config for node " + replicaNodeId);
		} catch (UnirestException e) {
			logger.error("Could not add replica node config for node " + replicaNodeId);
		}

		Thread.sleep(1000);

		// now add the other trigger nodes
		List<TriggerNodeConfig> tnCs = getTriggerNodeConfigs(replicaNodeId);

		for (int i = 0; i < tnCs.size(); i++) {
			TriggerNodeConfig c = tnCs.get(i);
			try {
				keygroupRequest.addTriggerNode(config.getKeygroupID(), c);
				logger.info("Added trigger node config for node " + c.getNodeID());
				Thread.sleep(1000);

			} catch (UnirestException e) {
				logger.error("Could not add trigger node config for node " + c.getNodeID());
			}
		};

	}


	private List<TriggerNodeConfig> getTriggerNodeConfigs(NodeID replicaNodeId) {
		NodeID floor0 = new NodeID("floor0");
		NodeID floor1 = new NodeID("floor1");
		NodeID floor2 = new NodeID("floor2");
		List<TriggerNodeConfig> configs = new ArrayList<>();

		if (!floor0.equals(replicaNodeId)) {
			configs.add(new TriggerNodeConfig(floor0));
		}

		if (!floor1.equals(replicaNodeId)) {
			configs.add(new TriggerNodeConfig(floor1));
		}

		if (!floor2.equals(replicaNodeId)) {
			configs.add(new TriggerNodeConfig(floor2));
		}

		return configs;
	}

}
