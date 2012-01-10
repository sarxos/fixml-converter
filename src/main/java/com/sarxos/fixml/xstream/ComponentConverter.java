package com.sarxos.fixml.xstream;

import quickfix.FieldMap;

import com.sarxos.fixml.spec.ml.FIXMLComponent;
import com.sarxos.fixml.spec.ml.FIXMLElement;
import com.sarxos.fixml.spec.ml.FIXMLField;
import com.sarxos.fixml.xstream.wrapper.ComponentWrapper;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


/**
 * Converter for FIX component wrappers.
 * 
 * @author Bartosz Firyn (SraXos)
 */
public class ComponentConverter extends AbstractConverter {

	@Override
	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class clazz) {
		return ComponentWrapper.class.isAssignableFrom(clazz);
	}

	@Override
	public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {

		ComponentWrapper wrapper = (ComponentWrapper) o;
		FIXMLComponent mlComponent = wrapper.getComponent();
		FieldMap fieldMap = wrapper.getFixComponent();

		String abbr = mlComponent.getSpec().getAbbr();
		if (abbr == null || abbr.isEmpty()) {
			throw new RuntimeException("Incorrect abbreviation '" + abbr + "' for component " + mlComponent);
		}

		writer.startNode(abbr);

		for (FIXMLElement element : mlComponent.getElements()) {
			if (element instanceof FIXMLField) {
				marshalField(fieldMap, element, writer);
			} else if (element instanceof FIXMLComponent) {
				marshalComponent(fieldMap, element, writer, context);
			} else {
				throw new RuntimeException("Unknown class found " + element.getClass());
			}
		}

		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}
