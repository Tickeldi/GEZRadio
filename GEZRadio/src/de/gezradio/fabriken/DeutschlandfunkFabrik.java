package de.gezradio.fabriken;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import de.gezradio.exceptions.PluginBrokenException;
import de.gezradio.logik.Folge;
import de.gezradio.logik.Sender;
import de.gezradio.logik.Sendung;
import de.gezradio.logik.XMLStreamReaderWrapper;

public class DeutschlandfunkFabrik extends Fabrikvorlage {

	private static final String NAME = "Deutschlandfunk";
	private static final String BILDURL = "http://static.radio.de"
			+ "/images/broadcasts/25/3f/1521/c175.png";
	private static final String BESCHREIBUNG = "Deutschlandfunk (DLF) ist neben "
			+ "Deutschlandradio Kultur und DRadio Wissen eines der "
			+ "nationalen Hörfunkprogramme des Deutschlandradios.";
	private final URL PODCASTURL;
	private static final String SENDUNGSPATTERN = "(?s)img src=\"([^\"]+)\".+?alt=\"Podcast ([^"
			+ "\"]+)\".+?href=\"([^\"]+)\"";
	private Sender sender;
	
	public DeutschlandfunkFabrik() {
			PODCASTURL = createURLFromString("http://www.deutschlandfunk.de/"
					+ "podcast.346.de.html");
		
	}
	
	@Override
	public Sender getSender() throws IOException {
		if(sender == null){
			sender = new Sender(
				NAME,
				BESCHREIBUNG, 
				ImageIO.read(new URL(BILDURL)), 
				this
				);
		}
		return sender;
	}

	@Override
	public List<Sendung> getSendungen(Sender sender) throws IOException, PluginBrokenException {
		List<Sendung> sendungsliste = new ArrayList<>();
		Matcher matcher = getMatcherFuerSendungen(
				PODCASTURL, 
				Pattern.compile(SENDUNGSPATTERN)
				);

		while(matcher.find()) {
			sendungsliste.add(
				new Sendung(
					matcher.group(2), 
					"", 
					ImageIO.read(new URL(matcher.group(1))), 
					sender, 
					new URL(matcher.group(3)),
					this
				)
			);
		}
		return sendungsliste;
	}

	@Override
	public List<Folge> getFolgen(Sendung sendung) 
			throws IOException, PluginBrokenException {
		List<Folge> folgen = new ArrayList<>();
		
		try {
			XMLStreamReaderWrapper wrapper = 
					new XMLStreamReaderWrapper(sendung.getURL());
			for(XMLStreamReaderWrapper item:wrapper) {
				URL url = createURLFromString(
						item.getAttribut("enclosure", "url"));
				
				Folge.Builder builder = new Folge.Builder(sendung, url);
				
				builder
					.titel(item.getText("title"))
					.bild(sendung.getBild())
					.beschreibung(item.getText("description"))
					.laenge(Integer.parseInt(
							item.getAttribut("enclosure", "length")))
					.gesendet(new SimpleDateFormat(
							"EEE, d MMM yyyy HH:mm:ss ZZZZZ", Locale.ENGLISH)
							.parse(item.getText("pubDate")));
				folgen.add(builder.build());
			}
		} catch (Exception e) {
			throw new PluginBrokenException(this, e);
		}
		return folgen;
	}
	
}