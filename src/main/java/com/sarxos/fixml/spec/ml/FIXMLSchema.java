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

	@XmlElement(name = "field")
	@XmlElementWrapper(name = "fields")
	private List<FIXMLField> fields;

	private transient Map<String, FIXMLField> fieldsMapping = new HashMap<>();
	private transient Map<String, FIXMLMessage> messageMapping = new HashMap<>();
	private transient Map<String, FIXMLComponent> componentMapping = new HashMap<>();

	public List<FIXMLElement> getHeaderElements() {
		return headerElements;
	}

	/**
	 * @return All FIXML messages defined by schema
	 */
	public List<FIXMLMessage> getMessages() {
		if (messages == null) {
			messages = new ArrayList<>();
		}
		return messages;
	}

	/**
	 * Get FIXML message by name.
	 * 
	 * @param name - message name to get
	 * @return FIXML message or null if given message does not exist
	 */
	public FIXMLMessage getMessageByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		return messageMapping.get(name);
	}

	/**
	 * @return List of all FIXML components defined by schema
	 */
	public List<FIXMLComponent> getComponents() {
		if (components == null) {
			components = new ArrayList<>();
		}
		return components;
	}

	/**
	 * @return All FIXML fields
	 */
	public List<FIXMLField> getFields() {
		if (fields == null) {
			fields = new ArrayList<>();
		}
		return fields;
	}

	/**
	 * Get FIXML field by name.
	 * 
	 * @param name - name of the FIXML field
	 * @return FIXML field or null if given field does not exist
	 */
	public FIXMLField getFieldByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		return fieldsMapping.get(name);
	}

	/**
	 * Get FIXML component by its name.
	 * 
	 * @param name - name of FIXML component to get
	 * @return FIXML component or null if given component does not exist
	 */
	public FIXMLComponent getComponentByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		return componentMapping.get(name);
	}

	/**
	 * Called by JAXB after class unmarshall. DO NOT invoke this method - its
	 * pointless.
	 * 
	 * @param unmarshaller
	 * @param parent
	 */
	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
		if (messageMapping.isEmpty()) {
			for (FIXMLMessage m : getMessages()) {
				messageMapping.put(m.getName(), m);
			}
		}
		if (componentMapping.isEmpty()) {
			for (FIXMLComponent c : getComponents()) {
				componentMapping.put(c.getName(), c);
			}
		}
		if (fieldsMapping.isEmpty()) {
			for (FIXMLField f : getFields()) {
				fieldsMapping.put(f.getName(), f);
			}
		}
	}

	/**
	 * @return Minor version number
	 */
	public int getMinor() {
		return minor;
	}

	/**
	 * @return Major version number
	 */
	public int getMajor() {
		return major;
	}

	/**
	 * @return Service pack number
	 */
	public int getServicePack() {
		return servicePack;
	}
}
