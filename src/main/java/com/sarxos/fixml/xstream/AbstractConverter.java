package com.sarxos.fixml.xstream;

import java.util.Map;

import quickfix.FieldMap;
import quickfix.FieldNotFound;
import quickfix.Group;

import com.sarxos.fixml.spec.Spec;
import com.sarxos.fixml.spec.fix.ComponentSpec;
import com.sarxos.fixml.spec.fix.FieldSpec;
import com.sarxos.fixml.spec.fix.MessageTypeSpec;
import com.sarxos.fixml.spec.ml.FIXMLComponent;
import com.sarxos.fixml.spec.ml.FIXMLElement;
import com.sarxos.fixml.spec.ml.FIXMLField;
import com.sarxos.fixml.spec.ml.FIXMLGroup;
import com.sarxos.fixml.spec.ml.FIXMLSchema;
import com.sarxos.fixml.xstream.wrapper.ComponentWrapper;
import com.sarxos.fixml.xstream.wrapper.GroupWrapper;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


public abstract class AbstractConverter implements Converter {

	private Spec spec = Spec.getInstance();
	private FIXMLSchema schema = null;
	private Map<String, MessageTypeSpec> messageTypesMapping = null;
	private Map<String, MessageTypeSpec> messageAbbrsMapping = null;
	private Map<String, ComponentSpec> componentsMapping = null;
	private Map<String, FieldSpec> fieldsMapping = null;

	public AbstractConverter() {
		this.messageTypesMapping = spec.getFIXMessageTypesMapping();
		this.messageAbbrsMapping = spec.getFIXMessageAbbrsMapping();
		this.componentsMapping = spec.getFIXComponentsMapping();
		this.fieldsMapping = spec.getFIXFieldsMapping();
		this.schema = spec.getSchema();
	}

	public FIXMLSchema getSchema() {
		return schema;
	}

	public Map<String, MessageTypeSpec> getMessageTypesMapping() {
		return messageTypesMapping;
	}

	public Map<String, MessageTypeSpec> getMessageAbbrsMapping() {
		return messageAbbrsMapping;
	}

	public Map<String, ComponentSpec> getComponentsMapping() {
		return componentsMapping;
	}

	public Map<String, FieldSpec> getFieldsMapping() {
		return fieldsMapping;
	}

	protected void marshalField(FieldMap message, FIXMLElement element, HierarchicalStreamWriter writer) {

		FIXMLField field = (FIXMLField) element;
		String name = message.getClass().getSimpleName();
		FieldSpec fixField = getFieldsMapping().get(field.getName());

		if (message.isSetField(fixField.getTag())) {
			String abbr = fixField.getAbbr();
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

	protected void unmarshalField(FieldMap message, FIXMLElement element, HierarchicalStreamReader reader) {

		FIXMLField field = (FIXMLField) element;
		String name = message.getClass().getSimpleName();
		FieldSpec fixField = getFieldsMapping().get(field.getName());

		String value = reader.getAttribute(fixField.getAbbr());

		if (value == null) {
			if (field.isRequired()) {
				throw new RuntimeException("Required field " + fixField + " is missing in message " + name);
			}
			// ignore not required fields
		} else {
			message.setString(fixField.getTag(), value);
		}
	}

	protected void marshalComponent(FieldMap fixComponent, FIXMLElement element, HierarchicalStreamWriter writer, MarshallingContext context) {

		FIXMLComponent component = (FIXMLComponent) element;
		FIXMLComponent descriptor = getSchema().getComponentByName(component.getName());
		ComponentSpec componentSpec = getComponentsMapping().get(component.getName());

		if (descriptor.isGroup()) {

			// group case

			FIXMLGroup group = FIXMLComponent.toGroup(descriptor);
			FIXMLField field = getSchema().getFieldByName(group.getName());
			int n = fixComponent.getGroupCount(field.getNumber());

			// required components should be present
			if (component.isRequired() && n == 0) {
				throw new RuntimeException("Component " + component + " is required");
			}

			// marshal all groups
			for (Group g : fixComponent.getGroups(field.getNumber())) {
				String abbr = componentSpec.getAbbr();
				writer.startNode(abbr);
				context.convertAnother(new GroupWrapper(g, group));
				writer.endNode();
			}

		} else {

			// component case
			FIXMLComponent innerComponent = getSchema().getComponentByName(componentSpec.getName());
			if (hasChildren(fixComponent, innerComponent)) {
				context.convertAnother(new ComponentWrapper(fixComponent, innerComponent));
			}
		}
	}

	protected void unmarshalComponent(FieldMap fixComponent, FIXMLElement element, HierarchicalStreamReader reader, UnmarshallingContext context) {
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			System.out.println("====");
			System.out.println(reader.getAttributeCount());
			System.out.println(reader.getNodeName());
			reader.moveUp();
		}
	}

	protected boolean hasChildren(FieldMap fixComponent, FIXMLComponent component) {

		component = getSchema().getComponentByName(component.getName());

		for (FIXMLElement element : component.getElements()) {
			if (element instanceof FIXMLField) {
				FIXMLField mlField = getSchema().getFieldByName(((FIXMLField) element).getName());
				try {
					String s = fixComponent.getString(mlField.getNumber());
					if (s != null && !s.isEmpty()) {
						return true;
					}
				} catch (FieldNotFound e) {
					// ignore
				}
			} else if (element instanceof FIXMLComponent) {
				FIXMLComponent descriptor = getSchema().getComponentByName(component.getName());
				if (descriptor.isGroup()) {
					FIXMLGroup group = FIXMLComponent.toGroup(descriptor);
					FIXMLField field = getSchema().getFieldByName(group.getName());
					int n = fixComponent.getGroupCount(field.getNumber());
					if (n > 0) {
						return true;
					}
				} else {
					return hasChildren(fixComponent, (FIXMLComponent) element);
				}
			}
		}
		return false;
	}
}
