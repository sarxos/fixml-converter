package com.sarxos.fixml.spec.ml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * FIXML schema entity.
 * 
 * @author Bartosz Firyn (SarXos)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "fix")
public class FIXMLSchema {

	@XmlAttribute
	private int minor;

	@XmlAttribute
	private int major;

	@XmlAttribute(name = "servicepack")
	private int servicePack;

	@XmlElementRef
	@XmlElementWrapper(name = "header")
	private List<FIXMLElement> headerElements;

	@XmlElement(name = "message")
	@XmlElementWrapper(name = "messages")
	private List<FIXMLMessage> messages;

	@XmlElement(name = "component")
	@XmlElementWrapper(name = "components")
	private List<FIXMLComponent> components;

	private transient Map<String, FIXMLMessage> messageMapping = new HashMap<>();
	private transient Map<String, FIXMLComponent> componentMapping = new HashMap<>();

	public List<FIXMLElement> getHeaderElements() {
		return headerElements;
	}

	public List<FIXMLMessage> getMessages() {
		if (messages == null) {
			messages = new ArrayList<>();
		}
		return messages;
	}

	public FIXMLMessage getMessageByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		return messageMapping.get(name);
	}

	public List<FIXMLComponent> getComponents() {
		if (components == null) {
			components = new ArrayList<>();
		}
		return components;
	}

	public FIXMLComponent getComponentByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		return componentMapping.get(name);
	}

	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		for (FIXMLMessage m : getMessages()) {
			messageMapping.put(m.getName(), m);
		}
		for (FIXMLComponent c : getComponents()) {
			componentMapping.put(c.getName(), c);
		}
	}

	public int getMinor() {
		return minor;
	}

	public int getMajor() {
		return major;
	}

	public int getServicePack() {
		return servicePack;
	}
}
