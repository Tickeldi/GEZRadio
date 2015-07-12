package de.gezradio.logik;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
/**
 * A Thread extension dedicated to redirecting InputStreams
 * to given OutputStreams. Feeds into the given OutputStream
 * during the lifetime of this Thread.
 *
 * @author Isaac Whitfield
 * @version 10/05/2014
 */
public class DirectedStream extends Thread {
 
	public DirectedStream(final InputStream in, final OutputStream out){
		super(handler(in, out));
		start();
	}
 
	public DirectedStream(final InputStream in, final OutputStream out, final boolean daemon){
		super(handler(in, out));
		setDaemon(daemon);
		start();
	}
 
	private static Runnable handler(final InputStream in, final OutputStream out){
		return new Runnable() {
			@Override
			public void run() {
				try {
					int d;
					while ((d = in.read()) != -1) {
						out.write(d);
					}
				} catch (IOException ex) {
					// Stream failure
				}
			}
		};
	}
}

