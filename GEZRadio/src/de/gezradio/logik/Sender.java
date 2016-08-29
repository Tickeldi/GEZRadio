package de.gezradio.logik;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.gezradio.basis.ISenderfabrik;
import de.gezradio.exceptions.PluginBrokenException;

public class Sender {
	private final String name;
	private final ISenderfabrik fabrik;
	
	public ISenderfabrik getFabrik() {
		return fabrik;
	}

	private final String beschreibung;
	private final Image bild;
	
	private List<Sendung> sendungen = new ArrayList<>();

	public Sender(String name, 
			String beschreibung, 
			Image bild, 
			ISenderfabrik fabrik) {
		this.name = name;
		this.beschreibung = beschreibung;
		this.bild = bild;
		this.fabrik = fabrik;
	}
	
	public void updateSendungsliste(Set<Sendung> sendungen) {
		this.sendungen.clear();
		this.sendungen.addAll(sendungen);
	}

	public String getName() {
		return name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public Image getBild() {
		return bild;
	}
	
	public List<Sendung> getSendungen() 
			throws IOException, PluginBrokenException {
		if(sendungen == null || sendungen.isEmpty()) {
			sendungen = fabrik.getSendungen(this);
		}
		return sendungen;
	}
}
