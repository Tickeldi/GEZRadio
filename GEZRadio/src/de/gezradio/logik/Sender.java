package de.gezradio.logik;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import de.gezradio.basis.ISenderfabrik;

public class Sender {
	private String name;
	private ISenderfabrik fabrik;
	
	public ISenderfabrik getFabrik() {
		return fabrik;
	}

	public Sender(String name, 
			String beschreibung, 
			Image bild, 
			ISenderfabrik fabrik) {
		this.name = name;
		this.beschreibung = beschreibung;
		this.bild = bild;
		this.fabrik = fabrik;
	}

	private String beschreibung;
	private Image bild;
	
	private List<Sendung> sendungen = new ArrayList<>();
	
	public void addSendung(Sendung sendung) {
		sendungen.add(sendung);
	}
	
	public void removeSendung(Sendung sendung) {
		sendungen.remove(sendung);
	}
	
	public void update() throws IOException, XMLStreamException {
		for(Sendung sendung:sendungen) {
			sendung.update();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Image getBild() {
		return bild;
	}

	public void setBild(Image bild) {
		this.bild = bild;
	}
	
	

}
