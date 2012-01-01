package com.sarxos.fixml.xstream;

import java.util.Map;

import quickfix.FieldMap;
import quickfix.FieldNotFound;

import com.sarxos.fixml.spec.Spec;
import com.sarxos.fixml.spec.fix.FIXComponent;
import com.sarxos.fixml.spec.fix.FIXField;
import com.sarxos.fixml.spec.fix.FIXMessageType;
import com.sarxos.fixml.spec.ml.FIXMLElement;
import com.sarxos.fixml.spec.ml.FIXMLField;
import com.sarxos.fixml.spec.ml.FIXMLSchema;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


public abstract class AbstractConverter implements Converter {

	private Spec spec = new Spec();
	private FIXMLSchema schema = null;
	private Map<String, FIXMessageType> messageTypesMapping = null;
	private Map<String, FIXComponent> componentsMapping = null;
	private Map<String, FIXField> fieldsMapping = null;

	public AbstractConverter() {
		this.messageTypesMapping = spec.getFIXMessageTypesMapping();
		this.componentsMapping = spec.getFIXComponentsMapping();
		this.fieldsMapping = spec.getFIXFieldsMapping();
		this.schema = spec.getSchema();
	}

	public FIXMLSchema getSchema() {
		return schema;
	}

	public Map<String, FIXMessageType> getMessageTypesMapping() {
		return messageTypesMapping;
	}

	public Map<String, FIXComponent> getComponentsMapping() {
		return componentsMapping;
	}

	public Map<String, FIXField> getFieldsMapping() {
		return fieldsMapping;
	}

	protected void marshalField(FieldMap message, FIXMLElement element, HierarchicalStreamWriter writer) {

		FIXMLField field = (FIXMLField) element;
		String name = message.getClass().getSimpleName();
		FIXField fixField = getFieldsMapping().get(field.getName());

		if (message.isSetField(fixField.getTag())) {
			String abbr = fixField.getAbbr();
			try {
				writer.addAttribute(abbr, message.getString(fixField.getTag()));
			} catch (FieldNotFound e) {
				if (field.isRequired()) {
					throw new RuntimeException("Required field " + fixField + " is missing in message " + name, e);
				}
				// ignore non required fields
			}
		} else {
			if (field.isRequired()) {
				throw new RuntimeException("Required field " + fixField + " is missing in message " + name);
			}
		}
	}
}
