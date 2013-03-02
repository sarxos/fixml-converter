package com.sarxos.fixml.spec;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.sarxos.fixml.spec.fix.ComponentSpec;
import com.sarxos.fixml.spec.fix.FieldSpec;
import com.sarxos.fixml.spec.fix.MessageTypeSpec;
import com.sarxos.fixml.spec.fix.SpecDataRoot;
import com.sarxos.fixml.spec.ml.FIXMLComponent;
import com.sarxos.fixml.spec.ml.FIXMLElement;
import com.sarxos.fixml.spec.ml.FIXMLField;
import com.sarxos.fixml.spec.ml.FIXMLGroup;
import com.sarxos.fixml.spec.ml.FIXMLMessage;
import com.sarxos.fixml.spec.ml.FIXMLSchema;


public class Spec {

	private static final Class<?>[] classes = new Class[] {
	ComponentSpec.class,
	MessageTypeSpec.class,
	SpecDataRoot.class,
	FIXMLComponent.class,
	FIXMLElement.class,
	FIXMLField.class,
	FIXMLGroup.class,
	FIXMLMessage.class,
	FIXMLSchema.class,
	};

	private static Spec instance = null;

	private List<MessageTypeSpec> messageTypes = null;
	private List<ComponentSpec> components = null;
	private List<FieldSpec> fields = null;
	private Map<String, MessageTypeSpec> messageTypesMapping = null;
	private Map<String, MessageTypeSpec> messageAbbrsMapping = null;
	private Map<String, ComponentSpec> componentsMapping = null;
	private Map<String, FieldSpec> fieldsMapping = null;
	private FIXMLSchema schema = null;
	private JAXBContext ctx = null;
	private Unmarshaller unmarshaller = null;

	private Spec() {
		try {
			ctx = JAXBContext.newInstance(classes);
			unmarshaller = ctx.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static Spec getInstance() {
		if (instance == null) {
			instance = new Spec();
		}
		return instance;
	}

	private InputStream getStream(String file) {

		ClassLoader cl = getClass().getClassLoader();
		String dir = getClass().getPackage().getName().replaceAll("\\.", "/");
		String res = dir + "/" + file;

		InputStream is = cl.getResourceAsStream(res);
		if (is == null) {
			throw new RuntimeException("Resource " + res + " is missing!");
		}

		return is;
	}

	/**
	 * @return List of message type objects.
	 */
	public List<MessageTypeSpec> getFIXMessageTypes() {

		if (messageTypes != null) {
			return messageTypes;
		}

		InputStream is = getStream("MsgType.xml");
		SpecDataRoot wrapper = null;
		try {
			wrapper = (SpecDataRoot) unmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

		if (wrapper == null) {
			throw new RuntimeException("Wrapper is null");
		} else {
			messageTypes = wrapper.getTypes();
		}

		return messageTypes;
	}

	/**
	 * @return Name to message type object mapping
	 */
	public Map<String, MessageTypeSpec> getFIXMessageTypesMapping() {
		if (messageTypesMapping != null) {
			return messageTypesMapping;
		}
		messageTypesMapping = new HashMap<>();
		for (MessageTypeSpec mt : getFIXMessageTypes()) {
			messageTypesMapping.put(mt.getName(), mt);
		}
		return messageTypesMapping;
	}

	/**
	 * @return Name to message type object mapping
	 */
	public Map<String, MessageTypeSpec> getFIXMessageAbbrsMapping() {
		if (messageAbbrsMapping != null) {
			return messageAbbrsMapping;
		}
		messageAbbrsMapping = new HashMap<>();
		for (MessageTypeSpec mt : getFIXMessageTypes()) {
			messageAbbrsMapping.put(mt.getAbbr(), mt);
		}
		return messageAbbrsMapping;
	}

	/**
	 * @return List of component objects.
	 */
	public List<ComponentSpec> getFIXComponents() {
		if (components != null) {
			return components;
		}
		InputStream is = getStream("Components.xml");
		SpecDataRoot wrapper = null;
		try {
			wrapper = (SpecDataRoot) unmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		if (wrapper == null) {
			throw new RuntimeException("Wrapper is null");
		} else {
			components = wrapper.getComponents();
		}
		return components;
	}

	/**
	 * @return Name to component object mapping
	 */
	public Map<String, ComponentSpec> getFIXComponentsMapping() {
		if (componentsMapping != null) {
			return componentsMapping;
		}
		componentsMapping = new HashMap<>();
		for (ComponentSpec c : getFIXComponents()) {
			componentsMapping.put(c.getName(), c);
		}
		return componentsMapping;
	}

	public List<FieldSpec> getFIXFields() {
		if (fields != null) {
			return fields;
		}
		InputStream is = getStream("Fields.xml");
		SpecDataRoot wrapper = null;
		try {
			wrapper = (SpecDataRoot) unmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		if (wrapper == null) {
			throw new RuntimeException("Wrapper is null");
		} else {
			fields = wrapper.getFields();
		}
		return fields;
	}

	/**
	 * @return Name to component object mapping
	 */
	public Map<String, FieldSpec> getFIXFieldsMapping() {
		if (fieldsMapping != null) {
			return fieldsMapping;
		}
		fieldsMapping = new HashMap<>();
		for (FieldSpec c : getFIXFields()) {
			fieldsMapping.put(c.getName(), c);
		}
		return fieldsMapping;
	}

	public FIXMLSchema getSchema() {
		if (schema != null) {
			return schema;
		}
		InputStream is = getStream("FIX50SP1.xml");
		try {
			schema = (FIXMLSchema) unmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		return schema;
	}

	public ComponentSpec getComponentSpec(String name) {
		return getFIXComponentsMapping().get(name);
	}

	public MessageTypeSpec getMessageTypeSpec(String name) {
		return getFIXMessageTypesMapping().get(name);
	}

	public FieldSpec getFieldSpec(String name) {
		return getFIXFieldsMapping().get(name);
	}

	public static void main(String[] args) {
		Spec spec = new Spec();
		FIXMLSchema schema = spec.getSchema();
		for (FIXMLComponent e : schema.getComponents()) {
			System.out.println(e);
			for (FIXMLElement f : e.getElements()) {
				System.out.println("    " + f);
			}
		}
	}
}
