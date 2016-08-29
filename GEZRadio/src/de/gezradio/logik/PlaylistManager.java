package de.gezradio.logik;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import de.gezradio.basis.IOutput;
import de.gezradio.basis.ISenderfabrik;
import de.gezradio.exceptions.PluginBrokenException;

public class PlaylistManager {
	private Set<Sender> sender = new HashSet<>();
	
	public Set<Sender> getSender() 
			throws IOException, PluginBrokenException {
		if(sender.isEmpty()) {
			updateSender();
		}
		return sender;
	}
	
	public void updateSender() throws IOException, PluginBrokenException {
		ServiceLoader<ISenderfabrik> fabrikLoader = 
				ServiceLoader.load(ISenderfabrik.class);
		
		for(ISenderfabrik fabrik:fabrikLoader) {
			sender.add(fabrik.getSender());
		}
	}
	
	public File createPlayList(File file, Settings settings, IOutput output) {
		return output.createPlayList(file, settings);
	}
}
