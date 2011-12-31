package com.sarxos.fixml.spec.ml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "element")
public class FIXMLElement {

	@Override
	public String toString() {
		return new StringBuffer(getClass().getSimpleName()).toString();
	}
	
}
