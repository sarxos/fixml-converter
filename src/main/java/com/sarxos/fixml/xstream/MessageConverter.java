package com.sarxos.fixml.xstream;

import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.Message;

import com.sarxos.fixml.spec.fix.FIXComponent;
import com.sarxos.fixml.spec.fix.FIXField;
import com.sarxos.fixml.spec.fix.FIXMessageType;
import com.sarxos.fixml.spec.ml.FIXMLComponent;
import com.sarxos.fixml.spec.ml.FIXMLElement;
import com.sarxos.fixml.spec.ml.FIXMLField;
import com.sarxos.fixml.spec.ml.FIXMLGroup;
import com.sarxos.fixml.spec.ml.FIXMLMessage;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


public class MessageConverter extends AbstractConverter {

	@Override
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class type) {
		return type != null && Message.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		Message fixMessage = (Message) source;

		String name = fixMessage.getClass().getSimpleName();

		FIXMessageType fixMessageType = getMessageTypesMapping().get(name);
		FIXMLMessage message = getSchema().getMessageByName(name);

		writer.startNode(fixMessageType.getAbbr());

		for (FIXMLElement element : message.getElements()) {
			if (element instanceof FIXMLField) {

				FIXMLField field = (FIXMLField) element;
				FIXField fixField = getFieldsMapping().get(field.getName());
				String abbr = fixField.getAbbr();

				if (fixMessage.isSetField(fixField.getTag())) {
					try {
						writer.addAttribute(abbr, fixMessage.getString(fixField.getTag()));
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
			} else if (element instanceof FIXMLComponent) {

				FIXMLComponent component = (FIXMLComponent) element;
				FIXMLComponent descriptor = getSchema().getComponentByName(component.getName());

				if (descriptor.isGroup()) {

					FIXMLGroup group = FIXMLComponent.toGroup(descriptor);
					FIXMLField field = getSchema().getFieldByName(group.getName());
					int n = fixMessage.getGroupCount(field.getNumber());

					// required components should be present
					if (component.isRequired() && n == 0) {
						throw new RuntimeException("Component " + component + " is required");
					}

					// marshal all groups
					for (Group g : fixMessage.getGroups(field.getNumber())) {

						FIXComponent fixComponent = getComponentsMapping().get(component.getName());
						String abbr = fixComponent.getAbbr();

						writer.startNode(abbr);
						context.convertAnother(new GroupWrapper(g, group));
						writer.endNode();
					}
				}
			} else {
				throw new RuntimeException("Class " + element.getClass() + " should not be present!");
			}
		}

		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		throw new RuntimeException("Not yet implemented");
	}

}
