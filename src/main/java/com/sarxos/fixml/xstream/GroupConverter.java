package com.sarxos.fixml.xstream;

import quickfix.Group;

import com.sarxos.fixml.spec.fix.FIXComponent;
import com.sarxos.fixml.spec.ml.FIXMLComponent;
import com.sarxos.fixml.spec.ml.FIXMLElement;
import com.sarxos.fixml.spec.ml.FIXMLField;
import com.sarxos.fixml.spec.ml.FIXMLGroup;
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

				FIXMLComponent component = (FIXMLComponent) element;
				FIXMLComponent descriptor = getSchema().getComponentByName(component.getName());
				FIXComponent fixComponent = getComponentsMapping().get(component.getName());

				if (descriptor.isGroup()) {

					// group case

					FIXMLGroup group = FIXMLComponent.toGroup(descriptor);
					FIXMLField field = getSchema().getFieldByName(group.getName());
					int n = fixGroup.getGroupCount(field.getNumber());

					// required components should be present
					if (component.isRequired() && n == 0) {
						throw new RuntimeException("Component " + component + " is required");
					}

					// marshal all groups
					for (Group g : fixGroup.getGroups(field.getNumber())) {

						String abbr = fixComponent.getAbbr();

						writer.startNode(abbr);
						context.convertAnother(new GroupWrapper(g, group));
						writer.endNode();
					}

				} else {

					// component case

					String abbr = fixComponent.getAbbr();

					writer.startNode(abbr);

					FIXMLComponent innerComponent = getSchema().getComponentByName(fixComponent.getName());

					// fixGroup.getField(new Instrument());

					// writer.startNode(abbr);
					// context.convertAnother(new GroupWrapper(g, group));
					writer.endNode();
				}
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
