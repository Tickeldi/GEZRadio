package de.gezradio.logik;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.gezradio.exceptions.PluginBrokenException;

public class Sendung implements Comparable<Sendung>{
	private String titel;
	private String beschreibung;
	private Image bild;
	private Sender sender;
	private URL podcasturl;

	private Set<Folge> folgen = new HashSet<>();

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
	
	public boolean update() throws IOException, PluginBrokenException {
		return sender.getFabrik().updateSendung(this);
	}
	
	public Set<Folge> getFolgen() {
		
		return Collections.unmodifiableSet(folgen);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((titel == null) ? 0 : titel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sendung other = (Sendung) obj;
		if (titel == null) {
			if (other.titel != null)
				return false;
		} else if (!titel.equals(other.titel))
			return false;
		return true;
	}

	@Override
	public int compareTo(Sendung o) {
		return titel.compareTo(o.titel);
	}
}
