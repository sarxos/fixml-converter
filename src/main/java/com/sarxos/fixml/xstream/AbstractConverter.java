package com.sarxos.fixml.xstream;

import java.util.Map;

import com.sarxos.fixml.spec.Spec;
import com.sarxos.fixml.spec.fix.FIXComponent;
import com.sarxos.fixml.spec.fix.FIXField;
import com.sarxos.fixml.spec.fix.FIXMessageType;
import com.sarxos.fixml.spec.ml.FIXMLSchema;
import com.thoughtworks.xstream.converters.Converter;


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
}
