package com.sarxos.fixml.xstream;

import java.util.Map;

import quickfix.FieldNotFound;
import quickfix.Message;

import com.sarxos.fixml.spec.Spec;
import com.sarxos.fixml.spec.fix.FIXComponent;
import com.sarxos.fixml.spec.fix.FIXField;
import com.sarxos.fixml.spec.fix.FIXMessageType;
import com.sarxos.fixml.spec.ml.FIXMLElement;
import com.sarxos.fixml.spec.ml.FIXMLField;
import com.sarxos.fixml.spec.ml.FIXMLMessage;
import com.sarxos.fixml.spec.ml.FIXMLSchema;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


public class MessageConverter implements Converter {

	private Spec spec = new Spec();
	private Map<String, FIXMessageType> messageTypesMapping = null;
	private Map<String, FIXComponent> componentsMapping = null;
	private Map<String, FIXField> fieldsMapping = null;
	private FIXMLSchema schema = null;

	public MessageConverter() {
		this.messageTypesMapping = spec.getFIXMessageTypesMapping();
		this.componentsMapping = spec.getFIXComponentsMapping();
		this.fieldsMapping = spec.getFIXFieldsMapping();
		this.schema = spec.getSchema();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class type) {
		return type != null && Message.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		Message message = (Message) source;

		String name = message.getClass().getSimpleName();

		FIXMessageType mt = messageTypesMapping.get(name);
		FIXMLMessage model = schema.getMessageByName(name);

		writer.startNode(mt.getAbbr());

		for (FIXMLElement element : model.getElements()) {
			if (element instanceof FIXMLField) {

				FIXMLField field = (FIXMLField) element;
				FIXField fixField = spec.getFIXFieldsMapping().get(field.getName());
				String abbr = fixField.getAbbr();

				if (message.isSetField(fixField.getTag())) {
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

		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		throw new RuntimeException("Not yet implemented");
	}

}
