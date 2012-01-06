package com.sarxos.fixml.spec.ml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import com.sarxos.fixml.spec.Spec;
import com.sarxos.fixml.spec.fix.ComponentSpec;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "component")
public class FIXMLComponent extends FIXMLElement {

	@XmlAttribute
	private String name;

	@XmlAttribute
	private String required;

	@XmlElementRef
	private List<FIXMLElement> elements;

	private transient ComponentSpec spec = null;

	public String getName() {
		return name;
	}

	public boolean isRequired() {
		return "Y".equals(required);
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
		append(isRequired() ? ":R" : "").toString();
	}

	public boolean isGroup() {
		List<FIXMLElement> elements = getElements();
		if (elements.size() == 1) {
			if (elements.get(0) instanceof FIXMLGroup) {
				return true;
			}
		}
		return false;
	}

	public static FIXMLGroup toGroup(FIXMLComponent component) {
		if (!component.isGroup()) {
			throw new RuntimeException("Component " + component + " is not a group!");
		}
		return (FIXMLGroup) component.getElements().get(0);
	}

	/**
	 * @return FIX component specification.
	 */
	public ComponentSpec getSpec() {
		if (spec == null) {
			spec = Spec.getInstance().getComponentSpec(getName());
		}
		return spec;
	}
}
