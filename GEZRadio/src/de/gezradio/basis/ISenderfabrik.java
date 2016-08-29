package de.gezradio.basis;

import java.io.IOException;
import java.util.List;

import de.gezradio.exceptions.PluginBrokenException;
import de.gezradio.logik.Folge;
import de.gezradio.logik.Sender;
import de.gezradio.logik.Sendung;

public interface ISenderfabrik {
	public Sender getSender() throws IOException, PluginBrokenException;
	public List<Sendung> getSendungen(Sender sender) 
			throws IOException, PluginBrokenException;
	public List<Folge> getFolgen(Sendung sendung) 
			throws IOException, PluginBrokenException;
}
