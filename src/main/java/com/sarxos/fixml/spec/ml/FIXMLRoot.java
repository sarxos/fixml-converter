package com.sarxos.fixml.spec.ml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import quickfix.Message;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * Root element for all FIXML messages.
 * 
 * @author Bartosz Firyn (SarXos)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FIXML")
@XStreamAlias("FIXML")
public class FIXMLRoot {
	
	@XmlAttribute(name = "v")
	@XStreamAlias("v")
	private String version = "5.0";
	
	@XmlAttribute(name = "r")
	@XStreamAlias("r")
	private String releaseDate = "20080317";
	
	@XmlAttribute(name = "s")
	@XStreamAlias("s")
	private String schemaDate = "20080314";

	@XmlElementRef
	private Message message; 
	
	
	public FIXMLRoot() {
	}

	public FIXMLRoot(Message message) {
		this.message = message;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getSchemaDate() {
		return schemaDate;
	}

	public void setSchemaDate(String schemaDate) {
		this.schemaDate = schemaDate;
	}
	
	public Message getMessage() {
		return message;
	}
	
	public void setMessage(Message message) {
		this.message = message;
	}
}
