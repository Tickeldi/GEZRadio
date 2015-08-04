package de.gezradio.basis;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import de.gezradio.logik.Sender;
import de.gezradio.logik.Sendung;

public interface ISenderfabrik {
	public Sender getSender() throws IOException, XMLStreamException;
	public boolean updateSendung(Sendung sendung) throws IOException, XMLStreamException;
}
