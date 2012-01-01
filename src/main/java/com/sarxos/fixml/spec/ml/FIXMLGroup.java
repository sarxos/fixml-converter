package com.sarxos.fixml.spec.ml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * FIXML schema elements group.
 * 
 * @author Bartosz Firyn (SarXos)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "group")
public class FIXMLGroup extends FIXMLElement {

	@XmlAttribute
	private String name;

	@XmlAttribute
	private String required;

	@XmlElementRef
	private List<FIXMLElement> elements;

	/**
	 * @return Elements inside FIXML group.
	 */
	public List<FIXMLElement> getElements() {
		return elements;
	}

	/**
	 * @return Group name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return true if field is required, false otherwise
	 */
	public boolean isRequired() {
		return "Y".equals(required);
	}

	@Override
	public String toString() {
		return new StringBuffer(super.toString()).append('[').append(getName()).
		append("]").append(isRequired() ? ":R" : "").toString();
	}
}
