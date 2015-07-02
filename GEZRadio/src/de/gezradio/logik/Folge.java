package de.gezradio.logik;

import java.io.File;
import java.net.URL;

import org.jaudiotagger.audio.AudioFile;

public class Folge {
	AudioFile audioDatei;

	public Folge(URL url) {
		new File(url.openStream())
	}
	
public int getLaenge() {
	return audioDatei.getAudioHeader().getTrackLength();
}

public int getSamplingRate() {
	return audioDatei.getAudioHeader().getSampleRateAsNumber();
}



}
