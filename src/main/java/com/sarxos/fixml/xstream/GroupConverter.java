package com.sarxos.fixml.xstream;

import quickfix.Group;

import com.sarxos.fixml.spec.ml.FIXMLComponent;
import com.sarxos.fixml.spec.ml.FIXMLElement;
import com.sarxos.fixml.spec.ml.FIXMLField;
import com.sarxos.fixml.spec.ml.FIXMLGroup;
import com.sarxos.fixml.xstream.wrapper.GroupWrapper;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


public class GroupConverter extends AbstractConverter {

	public GroupConverter() {
		super();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		return GroupWrapper.class.isAssignableFrom(clazz);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {

		GroupWrapper wrapper = (GroupWrapper) source;
		FIXMLGroup mlGroup = wrapper.getGroup();
		Group fixGroup = wrapper.getFIXGroup();

		for (FIXMLElement element : mlGroup.getElements()) {
			if (element instanceof FIXMLField) {
				marshalField(fixGroup, element, writer);
			} else if (element instanceof FIXMLComponent) {
				marshalComponent(fixGroup, element, writer, context);
			} else {
				throw new RuntimeException("Class " + element.getClass() + " should not be present!");
			}
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		throw new RuntimeException("Not yet implemented :(");
	}

}
