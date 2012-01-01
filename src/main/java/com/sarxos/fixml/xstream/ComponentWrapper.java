package com.sarxos.fixml.xstream;

import quickfix.Message;

import com.sarxos.fixml.spec.ml.FIXMLComponent;


public class ComponentWrapper {

	private Message fixComponent = null;
	private FIXMLComponent component = null;

	public ComponentWrapper(Message fixComponent, FIXMLComponent component) {
		super();
		this.fixComponent = fixComponent;
		this.component = component;
	}

	public Message getFixComponent() {
		return fixComponent;
	}

	public FIXMLComponent getComponent() {
		return component;
	}
}
