package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private static Properties properties;

	static {
		try {
			properties = new Properties();
			FileInputStream input = new FileInputStream("src\\main\\java\\util\\config.properties");
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getProperty(String key) {

		return properties.getProperty(key);
	}

	public static int getIntProperty(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}

}
