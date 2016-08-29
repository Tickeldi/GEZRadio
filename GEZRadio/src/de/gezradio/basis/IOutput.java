package de.gezradio.basis;

import java.io.File;

import de.gezradio.logik.Settings;

public interface IOutput {
	public File createPlayList(File file, Settings settings);
}
