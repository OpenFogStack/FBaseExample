package main;

import client.NodeRequest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.JSONable;
import model.config.NodeConfig;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Registration {

	private static Logger logger = Logger.getLogger(Registration.class.getName());

	public static void main(String[] args) throws FileNotFoundException {
		String address = args[0];
		int port = Integer.parseInt(args[1]);

		Registration r = new Registration(address, port);

		for (int i = 2; i < args.length; i++) {
			String pathToNodeConfig = args[i];
			r.registerNodeConfig(pathToNodeConfig);
		}

	}

	String address;
	int port;

	public Registration(String address, int port) {
		this.address = address;
		this.port = port;
	}


	public void registerNodeConfig(String pathToNodeConfig) throws FileNotFoundException {
		NodeRequest nodeRequest = new NodeRequest(address, port);
		FileInputStream fileIS = new FileInputStream(pathToNodeConfig);
		NodeConfig config = JSONable.fromJSON(fileIS, NodeConfig.class);

		try {
			nodeRequest.createNodeConfig(config);
			logger.info("Created node config for node " + config.getNodeID());
		} catch (UnirestException e) {
			logger.error("Could not create node config for node " + config.getNodeID(), e);
		}
	}

}
