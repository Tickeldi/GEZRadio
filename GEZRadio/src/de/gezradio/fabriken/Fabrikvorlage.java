package de.gezradio.fabriken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.gezradio.basis.ISenderfabrik;

public abstract class Fabrikvorlage implements ISenderfabrik {
	
	Matcher getMatcherFuerSendungen(URL podcastsurl, Pattern pattern) 
			throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(podcastsurl.openStream())
				);
		StringBuilder builder = new StringBuilder();
		String line = reader.readLine();
		
		while(line != null) {
			builder.append(line);
			line = reader.readLine();
		}
		
		return pattern.matcher(builder);
	}

	public static URL createURLFromString(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
