package org.dimensinfin.eveonline.neocom.infinity.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.adapter.implementers.SBFileSystemAdapter;

@Component
public class FileSystemWrapper extends SBFileSystemAdapter {
	//
	private static String DEFAULT_APPLICATION_DIRECTORY = "./NeoCom.Infinity";

	public FileSystemWrapper( @Value("${P.runtime.filesystem.application.directory}") final String defaultApplicationDirectory ) {
		if (null != defaultApplicationDirectory) this.applicationFolder = defaultApplicationDirectory;
	}
}