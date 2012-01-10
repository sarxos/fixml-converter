package com.sarxos.fixml.spec.fix;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Fields")
public class FieldSpec {

	@XmlElement(name = "FieldName")
	private String name;

	@XmlElement(name = "Type")
	private String type;

	@XmlElement(name = "Tag")
	private int tag;

	@XmlElement(name = "Desc")
	private String description;

	@XmlElement(name = "AbbrName")
	private String abbr;

	@XmlElement(name = "NotReqXML")
	private int notRequiredXML;

	public String getName() {
		return name;
	}

	public String getAbbr() {
		return abbr;
	}

	public String getDescription() {
		return description;
	}

	public int getTag() {
		return tag;
	}

	@Override
	public String toString() {
		return new StringBuffer(getName()).append('[').append(getAbbr()).append(']').toString();
	}

	public String getType() {
		return type;
	}
}
