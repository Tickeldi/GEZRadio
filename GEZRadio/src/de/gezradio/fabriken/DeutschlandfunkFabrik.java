package de.gezradio.fabriken;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.gezradio.basis.ISenderfabrik;
import de.gezradio.logik.Folge;
import de.gezradio.logik.Sender;
import de.gezradio.logik.Sendung;

public class DeutschlandfunkFabrik extends Fabrikvorlage implements ISenderfabrik {

	private final String NAME = "Deutschlandfunk";
	private final String BILDURL = "http://static.radio.de"
			+ "/images/broadcasts/25/3f/1521/c175.png";
	private final String BESCHREIBUNG = "Deutschlandfunk (DLF) ist neben "
			+ "Deutschlandradio Kultur und DRadio Wissen eines der "
			+ "nationalen Hörfunkprogramme des Deutschlandradios.";
	private final String PODCASTURL = "http://www.deutschlandfunk.de/"
			+ "podcast.346.de.html";
	private final String SENDUNGSPATTERN = "(?s)img src=\"([^\"]+)\".+?alt=\"Podcast ([^"
			+ "\"]+)\".+?href=\"([^\"]+)\"";
	
	@Override
	public Sender getSender() throws IOException, XMLStreamException {
		Sender deutschlandfunk = new Sender(
				NAME,
				BESCHREIBUNG, 
				ImageIO.read(new URL(BILDURL)), 
				this);

		Matcher matcher = getMatcherFuerSendungen(
				new URL(PODCASTURL), 
				Pattern.compile(SENDUNGSPATTERN)
				);

		while(matcher.find()) {
			deutschlandfunk.addSendung(
					getSendung(
							matcher.group(2),
							"",
							ImageIO.read(new URL(matcher.group(1))),
							deutschlandfunk,
							new URL(matcher.group(3))
							)
					);
		}
		return deutschlandfunk;
	}

	public List<Folge> getFolgen(Sendung sendung) throws IOException, XMLStreamException {
		LinkedList<Folge> folgen = new LinkedList<>();

		InputStream in = sendung.getURL().openStream();

		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser  = factory.createXMLStreamReader( in );

		Folge aktuelleFolge = null;
		String aktuellesElement = "";

		while(parser.hasNext()) {

			switch ( parser.getEventType() )
			{
			/*
			case XMLStreamConstants.START_DOCUMENT:
				break;

			case XMLStreamConstants.END_DOCUMENT:
				break;

			case XMLStreamConstants.NAMESPACE:
				break;
				
			*/
			case XMLStreamConstants.START_ELEMENT:
				aktuellesElement = parser.getLocalName();
				if(aktuellesElement.equals("item")) {
					aktuelleFolge = new Folge("", sendung.getBild(), null, "", 0, sendung, null);
				}
				else if(aktuellesElement.equals("enclosure")) {
					aktuelleFolge.setUrl(new URL(parser.getAttributeValue(0)));
					aktuelleFolge.setLaenge(Integer.parseInt(parser.getAttributeValue(1)));
				}

				break;

			case XMLStreamConstants.CHARACTERS:
				if ( ! parser.isWhiteSpace() ) {
					if (aktuelleFolge == null) {
						if(
								aktuellesElement.equals("description")
								&& (sendung.getBeschreibung() == null 
								|| sendung.getBeschreibung().isEmpty())
								)
							sendung.setBeschreibung(parser.getText());

						else if (aktuellesElement.equals("url")
								&& sendung.getBild() == null)
							ImageIO.read(new URL(parser.getText()));
					}
					
					else {
						if(aktuellesElement.equals("title")) {
							aktuelleFolge.setTitel(parser.getText());
						}
						else if (aktuellesElement.equals("link")) {
							aktuelleFolge.setArtikelURL(new URL(parser.getText()));
						}
						else if (aktuellesElement.equals("description")) {
							aktuelleFolge.setBeschreibung(parser.getText());
						}
						else if (aktuellesElement.equals("pubDate")) {
							DateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ZZZZZ", Locale.ENGLISH);
							try {
								Date date = format.parse(parser.getText());
								aktuelleFolge.setGesendet(date);
							} catch (ParseException e) {
								//TODO Implementiere "plugin kaputt" exception
							}
						}
					}

				}

				break;

			case XMLStreamConstants.END_ELEMENT:
				if(parser.getLocalName().equals("item")) {
					folgen.add(aktuelleFolge);
					aktuelleFolge = null;
				}
				break;

			default:
				break;
			}
			parser.next();

		}
		return folgen;
	}
}
