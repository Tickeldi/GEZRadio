package de.gezradio.fabriken;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.gezradio.basis.ISenderfabrik;
import de.gezradio.exceptions.PluginBrokenException;
import de.gezradio.logik.Folge;
import de.gezradio.logik.Sender;
import de.gezradio.logik.Sendung;

public abstract class Fabrikvorlage implements ISenderfabrik {
	
	private Sendung sendung;
	
	abstract List<Folge> getFolgen(Sendung sendung) throws PluginBrokenException, IOException;
	
	Sendung getSendung(
			String titel, 
			String beschreibung, 
			Image bild, 
			Sender sender,
			URL podcasturl
			) throws PluginBrokenException, IOException {
		if(sendung == null) {
			sendung = new Sendung(
					titel, 
					beschreibung, 
					bild, 
					sender, 
					podcasturl
					);
			//sendung.update(getFolgen(sendung));
		}

		return sendung;
	}
	
	Matcher getMatcherFuerSendungen(URL podcastsurl, Pattern pattern) throws IOException {
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

	@Override
	public abstract Sender getSender() throws IOException;

	@Override
	public boolean updateSendung(Sendung sendung) throws IOException,
	PluginBrokenException {
		return sendung.update(getFolgen(sendung));
	}

}
