package de.gezradio.logik;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.stream.XMLStreamException;

public class Sendung {
	private String titel;
	private String beschreibung;
	private Image bild;
	private Sender sender;
	private URL podcasturl;

	private TreeSet<Folge> folgen = new TreeSet<>();

	public Sendung(String titel, 
			String beschreibung, 
			Image bild, 
			Sender sender,
			URL podcasturl) {
		this.titel = titel;
		this.beschreibung = beschreibung;
		this.bild = bild;
		this.sender = sender;
		this.podcasturl = podcasturl;
	}
	
	public void addFolge(Folge folge) {
		folgen.add(folge);
	}
	
	public void removeFolge(Folge folge) {
		folgen.remove(folge);
	}
	
	public boolean update(List<Folge> neueFolgen) throws IOException {
		boolean changed = false;
		changed = folgen.addAll(neueFolgen);
		LinkedList<Folge> loeschen = new LinkedList<>();
		for(Folge folge:folgen) {
			if(!neueFolgen.contains(folge)
					&& (folge.getFile() == null || !folge.getFile().exists()) 
					&& !folge.urlFileExists()) {
				loeschen.add(folge);
				changed = true;
			}
		}
		folgen.removeAll(loeschen);
		return changed;
	}
	
	public boolean update() throws IOException, XMLStreamException {
		return sender.getFabrik().updateSendung(this);
	}
	
	public SortedSet<Folge> getFolgen() {
		
		return Collections.unmodifiableSortedSet(folgen);
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
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
	
	public Sender getSender() {
		return sender;
	}
	
	public URL getURL() {
		return podcasturl;
	}
}
