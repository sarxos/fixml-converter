package com.sarxos.fixml.xstream;

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
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		throw new RuntimeException("Not yet implemented");
	}

}
