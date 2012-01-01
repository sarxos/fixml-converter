package com.sarxos.fixml.xstream;

import quickfix.Group;
import quickfix.Message;

import com.sarxos.fixml.spec.fix.FIXComponent;
import com.sarxos.fixml.spec.fix.FIXComponentType;
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
		Message message = (Message) source;

		String name = message.getClass().getSimpleName();
		FIXMessageType fixMessageType = getMessageTypesMapping().get(name);
		FIXMLMessage mlMessage = getSchema().getMessageByName(name);

		writer.startNode(fixMessageType.getAbbr());

		for (FIXMLElement element : mlMessage.getElements()) {
			if (element instanceof FIXMLField) {
				marshalField(message, element, writer);
			} else if (element instanceof FIXMLComponent) {

				FIXMLComponent component = (FIXMLComponent) element;
				FIXMLComponent descriptor = getSchema().getComponentByName(component.getName());
				FIXComponent fixComponent = getComponentsMapping().get(component.getName());

				if (descriptor.isGroup()) {

					// group case

					FIXMLGroup group = FIXMLComponent.toGroup(descriptor);
					FIXMLField field = getSchema().getFieldByName(group.getName());
					int n = message.getGroupCount(field.getNumber());

					// required components should be present
					if (component.isRequired() && n == 0) {
						throw new RuntimeException("Component " + component + " is required");
					}

					if (n > 0 && fixComponent.getType() != FIXComponentType.IMPLICIT_BLOCK_REPEATING) {
						writer.startNode(fixComponent.getAbbr());
					}

					// marshal all groups
					for (Group g : message.getGroups(field.getNumber())) {

						String abbr = fixComponent.getAbbr();

						writer.startNode(abbr);
						context.convertAnother(new GroupWrapper(g, group));
						writer.endNode();
					}

					if (n > 0 && fixComponent.getType() != FIXComponentType.IMPLICIT_BLOCK_REPEATING) {
						writer.endNode();
					}
				} else {
					// component case
					// TODO
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
