//  PROJECT:     NeoCom.Microservices (NEOC.MS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.8 / SpringBoot-1.3.5 / Angular 5.0
//  DESCRIPTION: This is the SpringBoot MicroServices module to run the backend services to complete the web
//               application based on Angular+SB. This is the web version for the NeoCom Android native
//               application. Most of the source code is common to both platforms and this module includes
//               the source for the specific functionality for the backend services.
package org.dimensinfin.eveonline.neocom.datamngmt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.eveonline.neocom.interfaces.IFileSystem;

/**
 * @author Adam Antinoo
 */
// - CLASS IMPLEMENTATION ...................................................................................
public class FileSystemSBImplementation implements IFileSystem {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = LoggerFactory.getLogger("FileSystemSBImplementation");

	// - F I E L D - S E C T I O N ............................................................................
	private String applicationFolder = ".";

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public FileSystemSBImplementation() {
	}

	public FileSystemSBImplementation( final String applicationStoreDirectory ) {
		this.applicationFolder = applicationStoreDirectory;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public InputStream openResource4Input( final String filePath ) throws IOException {
		return new FileInputStream(new File(applicationFolder + "/" + filePath));
	}
	@Override
	public OutputStream openResource4Output( final String filePath ) throws IOException {
		return new FileOutputStream(new File(applicationFolder + "/" + filePath));
	}

	@Override
	public InputStream openAsset4Input( final String filePath ) throws IOException {
		return new FileInputStream(new File(accessAssetPath() + filePath));
	}

	@Override
	public File accessAppStorageFile( final String filePath ) {
		return new File(applicationFolder + "/" + filePath);
	}

	@Override
	public String accessAssetPath() {
		return "./src/main/";
	}

	// --- D E L E G A T E D   M E T H O D S
	@Override
	public String toString() {
		return new StringBuffer("FileSystemSBImplementation [ ")
				.append("applicationFolder:").append(applicationFolder).append(" ")
				.append("assetsFolder:").append(accessAssetPath()).append(" ")
				.append("]")
				.append("->").append(super.toString())
				.toString();
	}
}

// - UNUSED CODE ............................................................................................
//[01]
