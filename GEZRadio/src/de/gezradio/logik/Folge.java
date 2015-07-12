package de.gezradio.logik;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.gezradio.exceptions.UnsupportedFileTypeException;

public class Folge {

	private MP3UrlFile audioDatei;
	private String titel;
	private URL url;
	private File file;
	private Image bild;
	private String beschreibung;
	private int laenge;
	

	public Folge(URL url) throws IOException, UnsupportedFileTypeException {
		audioDatei = new MP3UrlFile(url);
		titel = url.getPath().replaceAll(".+/|\\..*", "");
	}
	
	public Folge(String titel, Image bild, URL url, String beschreibung, int laenge) {
		this.titel = titel;
		this.bild = bild;
		this.url = url;
		this.beschreibung = beschreibung;
		this.laenge = laenge;
	}
	
	private MP3UrlFile getAudioDatei() 
			throws IOException, UnsupportedFileTypeException {
		if(audioDatei == null)
			audioDatei = new MP3UrlFile(url);
		return audioDatei;
	}

	public int getLaenge() throws IOException, UnsupportedFileTypeException {
		if(laenge == 0) {
			laenge = getAudioDatei().getDuration();
		}
		return laenge;
	}
	
	public int getSamplingRate() throws IOException, UnsupportedFileTypeException {
		return getAudioDatei().getSamplingRate();
	}
	
	public int getBitRate() throws IOException, UnsupportedFileTypeException {
		return getAudioDatei().getBitrate();
	}
	
	public File downloadFile(File target) throws IOException {
		if(!file.exists()) {
			FileOutputStream out = new FileOutputStream(target);
			InputStream in = url.openStream();
			
			new DirectedStream(in, out);

			file = target;
			
		}
		return file;
	}
	
	public File getFile() {
		return file;
	}
	
	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public Image getBild() {
		return bild;
	}

	public void setBild(Image bild) {
		this.bild = bild;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setLaenge(int laenge) {
		this.laenge = laenge;
	}
}
