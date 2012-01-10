package com.sarxos.fixml.xstream;

import quickfix.Message;

import com.sarxos.fixml.spec.ml.FIXMLRoot;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


public class RootConverter implements Converter {

	@Override
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class type) {
		return type != null && FIXMLRoot.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		FIXMLRoot root = (FIXMLRoot) source;
		writer.addAttribute("v", root.getVersion());
		writer.addAttribute("r", root.getReleaseDate());
		writer.addAttribute("s", root.getSchemaDate());
		context.convertAnother(root.getMessage());
	}

	@Override
	public FIXMLRoot unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		FIXMLRoot root = new FIXMLRoot();
		root.setVersion(reader.getAttribute("v"));
		root.setReleaseDate(reader.getAttribute("r"));
		root.setSchemaDate(reader.getAttribute("s"));

		if (reader.hasMoreChildren()) {
			reader.moveDown();
			root.setMessage((Message) context.convertAnother(root, Message.class));
			reader.moveUp();
		}

		return root;
	}
}
