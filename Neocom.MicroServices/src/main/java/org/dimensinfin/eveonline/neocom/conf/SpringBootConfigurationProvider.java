//  PROJECT:     Neocom.Microservices (NEOC-MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.stream.Stream;

import org.dimensinfin.eveonline.neocom.interfaces.IConfigurationProvider;

public class SpringBootConfigurationProvider implements IConfigurationProvider {
	// - S T A T I C - S E C T I O N ..........................................................................
//	private static Logger logger = LoggerFactory.getLogger(NeoComSBDBHelper.class);
//	private static final String BUNDLE_NAME = "org.dimensinfin.eveonline.neocom.constant.R";  //$NON-NLS-1$
//	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private static final String DEFAULT_PROPERTIES_FOLDER = "src/main/resources/properties";
//	static{
//		initialize();
//	}

// --- S T A T I C   P R I V A T E   M E T H O D S

	// - F I E L D - S E C T I O N ............................................................................
	private Properties globalConfigurationProperties = new Properties();

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public SpringBootConfigurationProvider () {
		super();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public String getResourceString (final String key) {
		try {
			return globalConfigurationProperties.getProperty(key);
		} catch (MissingResourceException mre) {
			return '!' + key + '!';
		}
	}

	public String getResourceString (final String key, final String defaultValue) {
		try {
			return globalConfigurationProperties.getProperty(key, defaultValue);
		} catch (MissingResourceException mre) {
			return '!' + key + '!';
		}
	}

	/**
	 * Ths initialization method reads all the files located on a predefined folder under the src/main/resources path.
	 * All the files are expected to be Properties files and are read in alphabetical order and their contents added
	 * to the list of application properties. Read order will replace same ids with new data so the developer
	 * can use a naming convention to replace older values with new values without editing the older files.
	 */
	public void initialize () {
		try {
			readAllProperties();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void readAllProperties () throws IOException {
		// Read all .properties files under the predefined path on the /resources folder.
		Path propertiesPath = FileSystems.getDefault().getPath(DEFAULT_PROPERTIES_FOLDER);
		try (Stream<Path> paths = Files.walk(propertiesPath)) {
			paths
					.filter(Files::isRegularFile)
					.filter(Files::isReadable)
					.filter(path -> path.endsWith(".properties"))
					.forEach((fileName) -> {
						try {
							Properties properties = new Properties();
							properties.load(new FileInputStream(fileName.toString()));
							// Copy poperties to globals.
							globalConfigurationProperties.putAll(properties);
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					});
		}
	}
}
// - UNUSED CODE ............................................................................................
//[01]
