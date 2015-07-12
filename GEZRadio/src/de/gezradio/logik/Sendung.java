package de.gezradio.logik;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Sendung {
	String titel;
	String beschreibung;
	Image bild;
	
	List<Folge> folgen = new ArrayList<>();

	
	public Sendung(String titel, String beschreibung, Image bild) {
		super();
		this.titel = titel;
		this.beschreibung = beschreibung;
		this.bild = bild;
	}
	
	public void addFolge(Folge folge) {
		folgen.add(folge);
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
}
