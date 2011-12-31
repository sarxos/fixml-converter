package com.sarxos.fixml.spec.ml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * FIXML schema field.
 * 
 * @author Bartosz Firyn (SarXos)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "field")
public class FIXMLField extends FIXMLElement {

	@XmlAttribute
	private String name;

	@XmlAttribute
	private String required;

	/**
	 * @return Field name
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
