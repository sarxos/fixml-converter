package com.sarxos.fixml.xstream;

import quickfix.Message;
import quickfix.fix50.UserRequest;

import com.sarxos.fixml.spec.fix.MessageTypeSpec;
import com.sarxos.fixml.spec.ml.FIXMLComponent;
import com.sarxos.fixml.spec.ml.FIXMLElement;
import com.sarxos.fixml.spec.ml.FIXMLField;
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
		MessageTypeSpec messageTypeSpec = getMessageTypesMapping().get(name);
		FIXMLMessage mlMessage = getSchema().getMessageByName(name);

		writer.startNode(messageTypeSpec.getAbbr());

		for (FIXMLElement element : mlMessage.getElements()) {
			if (element instanceof FIXMLField) {
				marshalField(message, element, writer);
			} else if (element instanceof FIXMLComponent) {
				marshalComponent(message, element, writer, context);
			} else {
				throw new RuntimeException("Class " + element.getClass() + " should not be present!");
			}
		}

		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		String nodeName = reader.getNodeName();

		System.out.println(nodeName);

		return new UserRequest();
	}

}
