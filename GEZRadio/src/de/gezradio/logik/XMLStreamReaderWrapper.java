package de.gezradio.logik;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XMLStreamReaderWrapper  implements Iterable<XMLStreamReaderWrapper>{
	private XMLStreamReader parser;
	private Map<String, String> itemStore;
	private Map<String, Map<String, String>> attributeStore;
	private String itemTag;

	public XMLStreamReaderWrapper(String url) 
			throws MalformedURLException, XMLStreamException, IOException {
		this(url, "item");
	}
	
	public XMLStreamReaderWrapper(URL url) 
			throws MalformedURLException, XMLStreamException, IOException {
		this(url, "item");
	}

	public XMLStreamReaderWrapper(String url, String itemTag) 
			throws MalformedURLException, XMLStreamException, IOException {
		this(new URL(url), itemTag);
	}
	
	public XMLStreamReaderWrapper(URL url, String itemTag) 
			throws XMLStreamException, IOException {
		this(XMLInputFactory.newInstance()
				.createXMLStreamReader(url.openStream()), itemTag);
	}

	public XMLStreamReaderWrapper(XMLStreamReader parser) 
			throws XMLStreamException {
		this(parser, "item");
	}

	public XMLStreamReaderWrapper(XMLStreamReader parser, String itemTag) 
			throws XMLStreamException {
		this.parser = parser;
		this.itemTag = itemTag;
		readNextItem();
	}

	public void readNextItem() throws XMLStreamException {
		itemStore = new HashMap<>();
		attributeStore = new HashMap<>();

		String aktuellesElement = null;

		while((!parser.isEndElement() 
				|| !parser.getLocalName().equals(itemTag))
					&& parser.hasNext()) {

			if(!parser.isWhiteSpace()) {
				if(parser.isStartElement()) {
					aktuellesElement = parser.getLocalName();
					if(parser.getAttributeCount() > 0) {
						Map<String, String> attributes = new HashMap<>();
						attributeStore.put(aktuellesElement, attributes);
						for(int i = 0; i < parser.getAttributeCount(); i++) {
							attributes.put(
								parser.getAttributeLocalName(i), 
								parser.getAttributeValue(i)
							);
						}
					}
				}
				else if(parser.isCharacters()) {
					itemStore.put(aktuellesElement, parser.getText());
				}
			}
			if(parser.hasNext()) {
				parser.next();
			}
		}
		if(parser.hasNext()) {
			parser.nextTag();
			System.out.println(parser.getLocalName());
		}
	}

	public String getText(String localName) {
		return itemStore.get(localName);
	}

	public String getAttribut(String localName, String attributeName) {
		if(attributeStore.get(localName) == null) {
			return null;
		}
		return attributeStore.get(localName).get(attributeName);
	}

	public boolean hasNext() {
		if(parser.isEndElement()
				&& !parser.getLocalName().equals(itemTag)) {
			return false;
		}
		try {
			return parser.hasNext();
		} catch (XMLStreamException e) {
			return false;
		}
	}

	@Override
	public Iterator<XMLStreamReaderWrapper> iterator() {
		return new Iterator<XMLStreamReaderWrapper>() {

			@Override
			public boolean hasNext() {
				return XMLStreamReaderWrapper.this.hasNext();
			}

			@Override
			public XMLStreamReaderWrapper next() {
				try {
					readNextItem();
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
				return XMLStreamReaderWrapper.this;
			}
		};
	}
}