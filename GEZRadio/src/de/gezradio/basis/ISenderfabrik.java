package de.gezradio.basis;

import java.io.IOException;

import de.gezradio.exceptions.PluginBrokenException;
import de.gezradio.logik.Sender;
import de.gezradio.logik.Sendung;

public interface ISenderfabrik {
	public Sender getSender() throws IOException, PluginBrokenException;
	public void fillSender() throws IOException, PluginBrokenException;
	public boolean updateSendung(Sendung sendung) throws IOException, PluginBrokenException;
}
