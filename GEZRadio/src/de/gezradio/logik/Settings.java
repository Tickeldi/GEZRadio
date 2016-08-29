package de.gezradio.logik;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import de.gezradio.exceptions.PluginBrokenException;

public class Settings implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -960957197828756605L;
	private Set<Sendung> gewaehlteSendungen = new HashSet<>();
	private int listenDauer = 0;
	private boolean sortiereAltZuNeu = true;
	
	
	/**
	 * @return the gewaehlteSendungen
	 */
	public Set<Sendung> getGewaehlteSendungen() {
		return gewaehlteSendungen;
	}
	
	public void addSendung(Sendung sendung) {
		gewaehlteSendungen.add(sendung);
	}
	
	public boolean removeSendung(Sendung sendung) {
		return gewaehlteSendungen.remove(sendung);
	}
	
	public SortedSet<Folge> getFolgen() 
			throws IOException, PluginBrokenException {
		
		SortedSet<Folge> folgen;
		if(sortiereAltZuNeu) {
			folgen = new TreeSet<Folge>() ;
		}
		else {
			folgen = new TreeSet<Folge>( Collections.reverseOrder() );
		}
		
		for(Sendung sendung:gewaehlteSendungen) {
			folgen.addAll(sendung.getFolgen());
		}
		
		
		return folgen;
	}
	
	/**
	 * @return the listenLaenge
	 */
	public int getListenDauer() {
		return listenDauer;
	}
	/**
	 * @param listenLaenge the listenLaenge to set
	 */
	public void setListenLaenge(int listenLaenge) {
		this.listenDauer = listenLaenge;
	}
	/**
	 * @return the sortiereAltZuNeu
	 */
	public boolean isSortiereAltZuNeu() {
		return sortiereAltZuNeu;
	}
	/**
	 * @param sortiereAltZuNeu the sortiereAltZuNeu to set
	 */
	public void setSortiereAltZuNeu(boolean sortiereAltZuNeu) {
		this.sortiereAltZuNeu = sortiereAltZuNeu;
	}
	
	
}
