package de.gezradio.logik;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import de.gezradio.exceptions.UnsupportedFileTypeException;

public class Folge implements Comparable<Folge>{
	
	private final Sendung sendung;
	private MP3UrlFile audioDatei;
	private String titel;
	private URL fileurl;
	private URL artikelurl;
	private File file;
	private Image bild;
	private String beschreibung;
	private int laenge;
	private Date gesendet;
	

	public Folge(URL url, Sendung sendung) throws IOException, UnsupportedFileTypeException {
		audioDatei = new MP3UrlFile(url);
		titel = url.getPath().replaceAll(".+/|\\..*", "");
		this.sendung = sendung;
	}
	
	public Folge(String titel,
			Image bild, 
			URL url, 
			String beschreibung, 
			int laenge, 
			Sendung sendung,
			Date gesendet) {
		this.titel = titel;
		this.bild = bild;
		this.fileurl = url;
		this.beschreibung = beschreibung;
		this.laenge = laenge;
		this.sendung = sendung;
	}
	
	private MP3UrlFile getAudioDatei() 
			throws IOException, UnsupportedFileTypeException {
		if(audioDatei == null)
			audioDatei = new MP3UrlFile(fileurl);
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
		if(file == null || !file.exists()) {
			FileOutputStream out = new FileOutputStream(target);
			InputStream in = fileurl.openStream();
			
			new DirectedStream(in, out);

			file = target;
			
		}
		return file;
	}
	
	public File getFile() {
		return file;
	}
	
	public void deleteFile() {
		file.delete();
	}
	
	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public URL getUrl() {
		return fileurl;
	}

	public void setUrl(URL url) {
		this.fileurl = url;
	}
	
	public boolean urlFileExists() throws IOException {
		HttpURLConnection huc =  (HttpURLConnection)  fileurl.openConnection();
		huc.setRequestMethod("HEAD");
		return (huc.getResponseCode() == HttpURLConnection.HTTP_OK);
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
	
	public Sendung getSendung() {
		return sendung;
	}
	
	public Date getGesendet() {
		return gesendet;
	}

	public void setGesendet(Date gesendet) {
		this.gesendet = gesendet;
	}
	
	public URL getArtikelURL() {
		return artikelurl;
	}
	
	public void setArtikelURL(URL artikelurl) {
		this.artikelurl = artikelurl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileurl == null) ? 0 : fileurl.hashCode());
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
		Folge other = (Folge) obj;
		if (fileurl == null) {
			if (other.fileurl != null)
				return false;
		} else if (!fileurl.equals(other.fileurl))
			return false;
		return true;
	}

	@Override
	public int compareTo(Folge o) {
		return gesendet.compareTo(o.gesendet);
	}
}
