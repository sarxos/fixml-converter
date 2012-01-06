package com.sarxos.fixml.spec.fix;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MsgType")
public class MessageTypeSpec {

	@XmlElement(name = "MsgType")
	private String type;
	
	@XmlElement(name = "MessageName")
	private String name;
	
	@XmlElement(name = "ComponentType")
	private String componentType;;
	
	@XmlElement(name = "Category")
	private String category;
	
	@XmlElement(name = "MsgID")
	private String id;
	
	@XmlElement(name = "Section")
	private String section;
	
	@XmlElement(name = "AbbrName")
	private String abbr;
	
	@XmlElement(name = "OverrideAbbr")
	private String overrideAbbr;
	
	@XmlElement(name = "Volume")
	private String volume;
	
	@XmlElement(name = "NotReqXML")
	private int notRequiredXML;
	
	public String getName() {
		return name;
	}
	
	public String getAbbr() {
		return abbr;
	}
	
	@Override
	public String toString() {
		return new StringBuffer(getName()).append('[').append(getAbbr()).append(']').toString();
	}
}
