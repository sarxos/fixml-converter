package com.sarxos.fixml;

import java.io.IOException;
import java.io.StringWriter;

import quickfix.Message;

import com.sarxos.fixml.spec.ml.FIXMLRoot;
import com.sarxos.fixml.xstream.ComponentConverter;
import com.sarxos.fixml.xstream.GroupConverter;
import com.sarxos.fixml.xstream.MessageConverter;
import com.sarxos.fixml.xstream.RootConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;


/**
 * This converter converts FIX messages to FIXML and vice-versa.
 * 
 * @author Bartosz Firyn (SarXos)
 */
public class FIXMLConverter {

	private static boolean initialized = false;
	private static XStream xstream = null;
	private boolean pretty = false;

	public FIXMLConverter() {
	}

	/**
	 * Perform converter initialization. This operation is time consuming, so
	 * you can call this before you start conversion, otherwise this will be
	 * called before first conversion and due to that you can notice some delay
	 * in this case.
	 * 
	 * @return true if converter has been initialized, false otherwise
	 */
	public static synchronized boolean initialize() {

		if (initialized) {
			return false;
		}

		xstream = new XStream();
		xstream.processAnnotations(FIXMLRoot.class);
		xstream.registerConverter(new GroupConverter());
		xstream.registerConverter(new ComponentConverter());
		xstream.registerConverter(new MessageConverter());
		xstream.registerConverter(new RootConverter());

		initialized = true;
		return true;
	}

	public static synchronized boolean isInitialized() {
		return initialized;
	}

	/**
	 * Converts FIX message to FIXML.
	 * 
	 * @param message - FIX message to convert
	 * @return XML representation of FIX message as String
	 */
	public String toFIXML(Message message) {
		if (!initialized) {
			initialize();
		}
		StringWriter sw = new StringWriter();
		try {
			if (pretty) {
				xstream.toXML(new FIXMLRoot(message), sw);
			} else {
				xstream.marshal(new FIXMLRoot(message), new CompactWriter(sw));
			}
			return sw.toString();
		} finally {
			try {
				sw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public Message toFIX(String fixml) {
		if (!initialized) {
			initialize();
		}

		FIXMLRoot root = (FIXMLRoot) xstream.fromXML(fixml);
		return root.getMessage();
	}

	/**
	 * Set pretty formatting .
	 * 
	 * @param pretty
	 */
	public void setPretty(boolean pretty) {
		this.pretty = pretty;
	}

	/**
	 * Is pretty formatting enabled.
	 * 
	 * @return true if it is enabled, false otherwise
	 */
	public boolean isPretty() {
		return pretty;
	}
}
