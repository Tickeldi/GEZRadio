package de.gezradio.logik;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import de.gezradio.exceptions.UnsupportedFileTypeException;

public class MP3UrlFile {
	
	private final static int[] VERSION_1_BITRATES = 
		{0, 32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 0};
	
	private int bytesRead;
	public int getVersionID() {
		return versionID;
	}

	public int getBitrate() {
		return bitrate;
	}

	public int getSamplingRate() {
		return samplingRate;
	}

	public int getFileSize() {
		return fileSize;
	}

	public int getDuration() {
		return duration;
	}

	public URL getUrl() {
		return url;
	}
	
	public int getLayer() {
		return layer;
	}


	private int versionID;
	private int layer;
	private int bitrate;
	private int samplingRate;
	private int fileSize;
	private int duration;
	private URL url;
	
	public MP3UrlFile(URL url) throws IOException, UnsupportedFileTypeException {
		this.url = url;
		
		URLConnection connection = url.openConnection();
		
		fileSize = connection.getContentLength();
		
		InputStream in = connection.getInputStream();
		
		int[] headerArray = new int[4];
		
		while(!isHeader(headerArray) && headerArray[3] != -1) {
			headerArray[0] = headerArray[1];
			headerArray[1] = headerArray[2];
			headerArray[2] = headerArray[3];
			headerArray[3] = in.read();
			bytesRead++;
		}
		
		in.close();
		
		if(isHeader(headerArray)) {
			int b = (headerArray[1] >> 3) & 3;
			versionID = (b - 4) * -1;
			
			int c = (headerArray[1] >> 1) & 3;
			layer = (c - 4) * -1;
			
			int e = (headerArray[2] >> 4) & 15;
			if(versionID == 1)
				bitrate = VERSION_1_BITRATES[e];
			
			int f = (headerArray[2] >> 2) & 3;
			
			if(versionID == 1) {
				if(f == 0)
					samplingRate = 44100;
				if(f == 1)
					samplingRate = 48000;
				if(f == 2)
					samplingRate = 32000;
			}
			
//			int padding = (headerArray[2] >> 1) & 1;
			
			duration = (fileSize - bytesRead) / (bitrate * 125); 
			
//			System.out.println("File Size:          " + fileSize);
//			System.out.println("Länge:              " + duration);
//			System.out.println("Bytes read:         " + bytesRead);
//			System.out.println("Bitrate:            " + bitrate);
//			System.out.println("Version:            " + versionID);
//			System.out.println("Layer:              " + layer);
//			System.out.println("Sampling Rate:      " + samplingRate);
			
			
			
		}
		else {
			try {
				throw new UnsupportedFileTypeException(url.toURI());
			} catch (URISyntaxException e) {
				throw new UnsupportedFileTypeException();
			}
		}
		
	}
	
	private boolean isHeader(int[] check) {
		if(check[0] != 255) return false;

		int b = (check[1] >> 3) & 3;
		if(b == 0 || b == 1) return false;

		int c = (check[1] >> 1) & 3;
		if(c != 1) return false;

		int e = (check[2] >> 4) & 15;
		if(e == 255 || e == 0) return false;

		int f = (check[2] >> 2) & 3;
		if(f > 2) return false;

		return true;
	}

}
