package main;

import client.KeygroupRequest;
import client.NodeRequest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.JSONable;
import model.config.KeygroupConfig;
import model.config.ReplicaNodeConfig;
import model.config.TriggerNodeConfig;
import model.data.KeygroupID;
import model.data.NodeID;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class KeygroupUpdateSubscriptions {

	private static Logger logger = Logger.getLogger(KeygroupUpdateSubscriptions.class.getName());

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		String address = args[0];
		int port = Integer.parseInt(args[1]);

		KeygroupUpdateSubscriptions kus = new KeygroupUpdateSubscriptions(address, port);

		for (int i = 2; i < args.length; i++) {
			KeygroupID keygroupId = KeygroupID.createFromString(args[i]);
			kus.updateKeygroupSubscriptions(keygroupId);
		}

	}


	public KeygroupUpdateSubscriptions(String address, int port) {
		this.address = address;
		this.port = port;
	}

	String address;
	int port;


	public void updateKeygroupSubscriptions(KeygroupID keygroupId) throws FileNotFoundException, InterruptedException {

		KeygroupRequest keygroupRequest = new KeygroupRequest(address, port);

		try {
			keygroupRequest.updateLocalKeygroupConfig(keygroupId);
			logger.info("Node updated local keygroup config " + keygroupId);
			Thread.sleep(1000); // let's wait a second
		} catch (UnirestException e) {
			logger.error("Node could not update local keygroup config " + keygroupId, e);
		}

	}

}
