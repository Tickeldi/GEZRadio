package de.gezradio.basis;

import de.gezradio.logik.Sender;
import de.gezradio.logik.Sendung;

public interface Senderfabrik {
	public Sender getSender();
	public void updateSendung(Sendung sendung);
}
