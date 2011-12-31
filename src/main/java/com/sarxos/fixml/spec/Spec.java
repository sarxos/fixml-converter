package com.sarxos.fixml.spec;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.sarxos.fixml.spec.fix.FIXComponent;
import com.sarxos.fixml.spec.fix.FIXDataRoot;
import com.sarxos.fixml.spec.fix.FIXField;
import com.sarxos.fixml.spec.fix.FIXMessageType;
import com.sarxos.fixml.spec.ml.FIXMLComponent;
import com.sarxos.fixml.spec.ml.FIXMLElement;
import com.sarxos.fixml.spec.ml.FIXMLField;
import com.sarxos.fixml.spec.ml.FIXMLMessage;
import com.sarxos.fixml.spec.ml.FIXMLSchema;


public class Spec {

	private static final Class<?>[] classes = new Class[] {
	FIXComponent.class,
	FIXMessageType.class,
	FIXDataRoot.class,
	FIXMLComponent.class,
	FIXMLElement.class,
	FIXMLField.class,
	FIXMLMessage.class,
	FIXMLSchema.class,
	};

	private List<FIXMessageType> messageTypes = null;
	private List<FIXComponent> components = null;
	private List<FIXField> fields = null;
	private Map<String, FIXMessageType> messageTypesMapping = null;
	private Map<String, FIXComponent> componentsMapping = null;
	private Map<String, FIXField> fieldsMapping = null;
	private FIXMLSchema schema = null;
	private JAXBContext ctx = null;
	private Unmarshaller unmarshaller = null;

	public Spec() {
		try {
			ctx = JAXBContext.newInstance(classes);
			unmarshaller = ctx.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
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
	public List<FIXMessageType> getFIXMessageTypes() {

		if (messageTypes != null) {
			return messageTypes;
		}

		InputStream is = getStream("MsgType.xml");
		FIXDataRoot wrapper = null;
		try {
			wrapper = (FIXDataRoot) unmarshaller.unmarshal(is);
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
	public Map<String, FIXMessageType> getFIXMessageTypesMapping() {
		if (messageTypesMapping != null) {
			return messageTypesMapping;
		}
		messageTypesMapping = new HashMap<>();
		for (FIXMessageType mt : getFIXMessageTypes()) {
			messageTypesMapping.put(mt.getName(), mt);
		}
		return messageTypesMapping;
	}

	/**
	 * @return List of component objects.
	 */
	public List<FIXComponent> getFIXComponents() {
		if (components != null) {
			return components;
		}
		InputStream is = getStream("Components.xml");
		FIXDataRoot wrapper = null;
		try {
			wrapper = (FIXDataRoot) unmarshaller.unmarshal(is);
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
	public Map<String, FIXComponent> getFIXComponentsMapping() {
		if (componentsMapping != null) {
			return componentsMapping;
		}
		componentsMapping = new HashMap<>();
		for (FIXComponent c : getFIXComponents()) {
			componentsMapping.put(c.getName(), c);
		}
		return componentsMapping;
	}

	public List<FIXField> getFIXFields() {
		if (fields != null) {
			return fields;
		}
		InputStream is = getStream("Fields.xml");
		FIXDataRoot wrapper = null;
		try {
			wrapper = (FIXDataRoot) unmarshaller.unmarshal(is);
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
	public Map<String, FIXField> getFIXFieldsMapping() {
		if (fieldsMapping != null) {
			return fieldsMapping;
		}
		fieldsMapping = new HashMap<>();
		for (FIXField c : getFIXFields()) {
			fieldsMapping.put(c.getName(), c);
		}
		return fieldsMapping;
	}

	public FIXMLSchema getSchema() {
		if (schema != null) {
			return schema;
		}
		InputStream is = getStream("FIX50SP2.xml");
		try {
			schema = (FIXMLSchema) unmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		return schema;
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
