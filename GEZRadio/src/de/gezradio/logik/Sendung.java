package de.gezradio.logik;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Sendung {
	private String titel;
	private String beschreibung;
	private Image bild;
	private Sender sender;

	private TreeSet<Folge> folgen = new TreeSet<>();

	public Sendung(String titel, String beschreibung, Image bild, Sender sender) {
		this.titel = titel;
		this.beschreibung = beschreibung;
		this.bild = bild;
		this.sender = sender;
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
}
