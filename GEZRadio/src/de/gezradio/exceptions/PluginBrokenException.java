package de.gezradio.exceptions;

import de.gezradio.basis.ISenderfabrik;

public class PluginBrokenException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9205849542529949037L;

	private ISenderfabrik brokenplugin;
	private Exception originalException;
	
	public PluginBrokenException(ISenderfabrik brokenplugin,
			Exception originalException) {
		this.brokenplugin = brokenplugin;
		this.originalException = originalException;
	}

	public ISenderfabrik getBrokenplugin() {
		return brokenplugin;
	}

	public Exception getOriginalException() {
		return originalException;
	}

}
