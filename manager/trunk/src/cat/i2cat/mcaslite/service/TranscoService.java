package cat.i2cat.mcaslite.service;

import java.io.File;

import cat.i2cat.mcaslite.cloud.CloudManager;
import cat.i2cat.mcaslite.exceptions.MCASException;
import cat.i2cat.mcaslite.management.TranscoHandler;
import cat.i2cat.mcaslite.utils.DefaultsLoader;

public class TranscoService {
	
	private static TranscoHandler transcoH;
	private static CloudManager client;
	private static Thread managerTh;
	private static Thread clientTh;
	private static String path = "config" + File.separator;
	
	public static void main(String[] args){
		
		DefaultsLoader loader = new DefaultsLoader(path);
		try {
			loader.tConfigFeedDefaults();
		} catch (MCASException e) {
			e.printStackTrace();
			return;
		}
	
		transcoH = TranscoHandler.getInstance();
		managerTh = new Thread(transcoH);
		managerTh.setName("MainManager");
		managerTh.setDaemon(true);
		managerTh.start();
		
		client = CloudManager.getInstance();
		clientTh = new Thread(client);
		clientTh.setName("MainManager");
		clientTh.setDaemon(true);
		clientTh.start();
		
		try {
			clientTh.join();
			managerTh.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}		
}