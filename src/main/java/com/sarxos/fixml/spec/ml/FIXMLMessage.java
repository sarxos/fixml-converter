package com.sarxos.fixml.spec.ml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "message")
public class FIXMLMessage extends FIXMLElement {

	@XmlAttribute
	private String name;

	@XmlAttribute(name = "msgcat")
	private String category;

	@XmlAttribute(name = "msgtype")
	private String type;

	@XmlElementRef
	private List<FIXMLElement> elements;

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getType() {
		return type;
	}

	public List<FIXMLElement> getElements() {
		if (elements == null) {
			elements = new ArrayList<>();
		}
		return elements;
	}

	@Override
	public String toString() {
		return new StringBuffer(super.toString()).append('[').append(getName()).
		append(':').append(getElements().size()).append(']').
		append('(').append(getCategory()).append(")(").append(getType()).append(')').toString();
	}
}
