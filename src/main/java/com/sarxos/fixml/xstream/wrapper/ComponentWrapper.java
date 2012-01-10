package com.sarxos.fixml.xstream.wrapper;

import quickfix.FieldMap;

import com.sarxos.fixml.spec.ml.FIXMLComponent;


public class ComponentWrapper {

	private FieldMap fixComponent = null;
	private FIXMLComponent component = null;

	public ComponentWrapper(FieldMap fixComponent, FIXMLComponent component) {
		super();
		this.fixComponent = fixComponent;
		this.component = component;
	}

	public FieldMap getFixComponent() {
		return fixComponent;
	}

	public FIXMLComponent getComponent() {
		return component;
	}
}
