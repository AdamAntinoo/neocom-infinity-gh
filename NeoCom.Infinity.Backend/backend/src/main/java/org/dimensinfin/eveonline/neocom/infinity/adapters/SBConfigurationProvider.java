package org.dimensinfin.eveonline.neocom.infinity.adapters;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.annimon.stream.Stream;

import org.dimensinfin.eveonline.neocom.conf.GlobalConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */
public class SBConfigurationProvider extends GlobalConfigurationProvider {
	public SBConfigurationProvider(final String propertiesFolder ) {
		super(propertiesFolder);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	protected void readAllProperties() throws IOException {
		logger.info(">> [GlobalConfigurationProvider.readAllProperties]");
		// Read all .properties files under the predefined path on the /resources folder.
		final List<String> propertyFiles = getResourceFiles(getResourceLocation());
		final ClassLoader classLoader = getClass().getClassLoader();
		Stream.of(propertyFiles)
				.sorted()
				.forEach(( fileName ) -> {
					logger.info("-- [GlobalConfigurationProvider.readAllProperties]> Processing file: {}", fileName);
					try {
						Properties properties = new Properties();
						// Generate the proper URI to ge tot the resource file.
						final String propertyFileName = getResourceLocation() + "/" + fileName;
						final URI propertyURI = new URI(classLoader.getResource(propertyFileName).toString());
						properties.load(new FileInputStream(propertyURI.getPath()));
						// Copy properties to globals.
						configurationProperties.putAll(properties);
					} catch (IOException ioe) {
						logger.error("E [GlobalConfigurationProvider.readAllProperties]> Exception reading properties file {}. {}",
								fileName, ioe.getMessage());
						ioe.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				});
		logger.info("<< [GlobalConfigurationProvider.readAllProperties]> Total properties number: {}", contentCount());
	}

	protected List<String> getResourceFiles( String path ) throws IOException {
		List<String> filenames = new ArrayList<>();

		try (
				InputStream in = getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;

			while ((resource = br.readLine()) != null) {
				filenames.add(resource);
			}
		}

		return filenames;
	}

	private InputStream getResourceAsStream( String resource ) {
		final InputStream in
				= getContextClassLoader().getResourceAsStream(resource);

		return in == null ? getClass().getResourceAsStream(resource) : in;
	}

	private ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}