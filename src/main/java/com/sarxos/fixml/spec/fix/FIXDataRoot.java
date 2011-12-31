package com.sarxos.fixml.spec.fix;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "dataroot")
public class FIXDataRoot {

	@XmlElement(name = "MsgType")
	private List<FIXMessageType> types;

	@XmlElement(name = "Components")
	private List<FIXComponent> components;

	@XmlElement(name = "Fields")
	private List<FIXField> fields;

	public List<FIXMessageType> getTypes() {
		return types;
	}

	public List<FIXComponent> getComponents() {
		return components;
	}

	public List<FIXField> getFields() {
		return fields;
	}
}
