package com.sarxos.fixml.xstream;

import quickfix.FieldNotFound;
import quickfix.Group;

import com.sarxos.fixml.spec.fix.FIXComponent;
import com.sarxos.fixml.spec.fix.FIXField;
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
		FIXMLGroup ggroup = wrapper.getGroup();
		Group fixGroup = wrapper.getFIXGroup();

		for (FIXMLElement element : ggroup.getElements()) {
			if (element instanceof FIXMLField) {

				FIXMLField field = (FIXMLField) element;
				FIXField fixField = getFieldsMapping().get(field.getName());
				System.out.println(field.getName());
				String abbr = fixField.getAbbr();

				if (fixGroup.isSetField(fixField.getTag())) {
					try {
						writer.addAttribute(abbr, fixGroup.getString(fixField.getTag()));
					} catch (FieldNotFound e) {
						if (field.isRequired()) {
							throw new RuntimeException("Required field " + fixField + " is missing in group " + ggroup, e);
						}
						// ignore non required fields
					}
				} else {
					if (field.isRequired()) {
						throw new RuntimeException("Required field " + fixField + " is missing in group " + ggroup);
					}
				}
			} else if (element instanceof FIXMLComponent) {

				FIXMLComponent component = (FIXMLComponent) element;
				FIXMLComponent descriptor = getSchema().getComponentByName(component.getName());

				if (descriptor.isGroup()) {

					FIXMLGroup group = FIXMLComponent.toGroup(descriptor);
					FIXMLField field = getSchema().getFieldByName(group.getName());
					int n = fixGroup.getGroupCount(field.getNumber());

					// required components should be present
					if (component.isRequired() && n == 0) {
						throw new RuntimeException("Component " + component + " is required");
					}

					// marshal all groups
					for (Group g : fixGroup.getGroups(field.getNumber())) {

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
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		throw new RuntimeException("Not yet implemented :(");
	}

}
