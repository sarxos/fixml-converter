package com.sarxos.fixml.spec.fix;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "dataroot")
public class SpecDataRoot {

	@XmlElement(name = "MsgType")
	private List<MessageTypeSpec> types;

	@XmlElement(name = "Components")
	private List<ComponentSpec> components;

	@XmlElement(name = "Fields")
	private List<FieldSpec> fields;

	public List<MessageTypeSpec> getTypes() {
		return types;
	}

	public List<ComponentSpec> getComponents() {
		return components;
	}

	public List<FieldSpec> getFields() {
		return fields;
	}
}
