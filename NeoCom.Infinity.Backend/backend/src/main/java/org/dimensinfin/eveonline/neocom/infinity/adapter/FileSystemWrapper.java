package org.dimensinfin.eveonline.neocom.infinity.adapter;

import org.springframework.stereotype.Component;

@Component
public class FileSystemWrapper extends FileSystemSBAdapter {
//	@Value("${P.runtime.filesystem.application.directory}")
	private static String DEFAULT_APPLICATION_DIRECTORY = "./NeoCom.Infinity";

	public FileSystemWrapper() {
		super(DEFAULT_APPLICATION_DIRECTORY);
	}
}