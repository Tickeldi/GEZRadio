package de.gezradio.exceptions;

import java.net.URI;

public class UnsupportedFileTypeException extends Exception {
	URI file;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnsupportedFileTypeException() {}
	public UnsupportedFileTypeException(URI file) {
		this.file = file;
	}
	
	public URI getURI() {
		return file;
	}

}
