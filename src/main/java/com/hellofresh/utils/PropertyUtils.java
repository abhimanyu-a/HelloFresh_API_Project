package com.hellofresh.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
	
	private final String FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\ConfigFile\\Config.properties";

	public String getProperty(String key) throws IOException {

		return loadProperty(key);

	}

	private String loadProperty(String key) throws IOException {
		File file = new File(FILE_PATH);
		FileInputStream inStream = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(inStream);
		inStream.close();

		return properties.getProperty(key);

	}

}
