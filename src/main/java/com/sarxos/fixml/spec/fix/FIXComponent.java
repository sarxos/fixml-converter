package com.sarxos.fixml.spec.fix;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Components")
public class FIXComponent {

	@XmlElement(name = "ComponentName")
	private String name;
	
	@XmlElement(name = "ComponentType")
	private String type;
	
	@XmlElement(name = "Category")
	private String category;
	
	@XmlElement(name = "MsgID")
	private String id;
	
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
	
	@Override
	public String toString() {
		return new StringBuffer(getName()).append('[').append(getAbbr()).append(']').toString();
	}
}
