package de.gezradio.basis;

import de.gezradio.logik.Sender;
import de.gezradio.logik.Sendung;

public interface Senderfabrik {
	public Sender getSender();
	public boolean updateSendung(Sendung sendung);
}
