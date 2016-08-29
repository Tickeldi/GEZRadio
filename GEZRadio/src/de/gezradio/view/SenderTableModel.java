package de.gezradio.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.sun.prism.Image;

import de.gezradio.basis.ISenderfabrik;
import de.gezradio.exceptions.PluginBrokenException;
import de.gezradio.logik.Sender;

public class SenderTableModel implements TableModel{
	private List<Sender> sender = new ArrayList<>();
	private Map<Sender, Boolean> checked = new HashMap<>();
	
	public SenderTableModel() throws IOException, PluginBrokenException {
		ServiceLoader<ISenderfabrik> fabrikLoader = 
				ServiceLoader.load(ISenderfabrik.class);
		
		for(ISenderfabrik fabrik:fabrikLoader) {
			sender.add(fabrik.getSender());
		}
	}

	@Override
	public int getRowCount() {
		return sender.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex) {
			case 0:
				return "Bild";
			case 1:
				return "Name";
			case 2:
				return "Beschreibung";
		}
		return "";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 0) {
			return Image.class;
		}
		if(columnIndex == 3) {
			return Boolean.class;
		}
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 3) {
			return true;
		}
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Sender sender = this.sender.get(rowIndex);
		switch(columnIndex) {
			case 0:
				return sender.getBild();
			case 1:
				return sender.getName();
			case 2:
				return sender.getBeschreibung();
			case 3:
				return checked.get(sender);
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(columnIndex == 3) {
			Sender sender = this.sender.get(rowIndex);
			checked.put(sender, (Boolean) aValue);
		}
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
	}

}
