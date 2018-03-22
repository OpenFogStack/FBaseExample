package main;

import client.NodeRequest;
import client.RecordRequest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.JSONable;
import model.config.NodeConfig;
import model.data.DataIdentifier;
import model.data.DataRecord;
import model.data.KeygroupID;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddRecordsClient {

	private static Logger logger = Logger.getLogger(AddRecordsClient.class.getName());

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		String address = args[0];
		int port = Integer.parseInt(args[1]);
		KeygroupID keygroupID = KeygroupID.createFromString(args[2]);

		AddRecordsClient r = new AddRecordsClient(address, port);

		r.addRecords(keygroupID);

	}

	String address;
	int port;

	public AddRecordsClient(String address, int port) {
		this.address = address;
		this.port = port;
	}


	public void addRecords(KeygroupID keygroupID) {
		String key = "Number of parked cars";
		RecordRequest request = new RecordRequest(address, port);
		Random rand = new Random();
		int i = 0;

		while (true) {
			DataIdentifier lastIdentifier = new DataIdentifier(keygroupID, Integer.toString(i-1));
			Map<String, String> value = new HashMap<>();
			value.put(key, "1000");
			try {
				DataRecord record = request.getDataRecord(lastIdentifier);
				if (record != null) {
					value = record.getValue();
					if (rand.nextBoolean()) {
						value.put(key, Integer.toString(Integer.parseInt(value.get(key)) + 1));
					} else {
						value.put(key, Integer.toString(Integer.parseInt(value.get(key)) - 1));
					}
				}
			} catch (UnirestException e) {
				e.printStackTrace();
			}

			DataIdentifier newIdentifier = new DataIdentifier(keygroupID, Integer.toString(i));
			DataRecord record = new DataRecord(newIdentifier, value);
			try {
				request.putDataRecord(record);
				logger.info("Put data record into keygroup " + keygroupID);
			} catch (UnirestException e) {
				logger.error("Could not put data record into keygroup " + keygroupID);
				return;
			}
			try {
				Thread.sleep(rand.nextInt(20000));
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			i++;
		}
	}

}
