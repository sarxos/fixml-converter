package com.sarxos.fixml;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sarxos.fixml.spec.Spec;
import com.sarxos.fixml.spec.ml.FIXMLComponent;
import com.sarxos.fixml.spec.ml.FIXMLField;
import com.sarxos.fixml.spec.ml.FIXMLMessage;
import com.sarxos.fixml.spec.ml.FIXMLSchema;


public class TestFIXMLSchema {

	private static Spec spec = null;

	@BeforeClass
	public static void setUp() {
		spec = Spec.getInstance();
	}

	@Test
	public void test_getMessages() {
		FIXMLSchema schema = spec.getSchema();
		Assert.assertTrue(schema.getMessages().size() > 0);
	}

	@Test
	public void test_getMessageByName() {
		FIXMLSchema schema = spec.getSchema();
		FIXMLMessage message = schema.getMessageByName("UserRequest");
		Assert.assertNotNull(message);
	}

	@Test
	public void test_getComponent() {
		FIXMLSchema schema = spec.getSchema();
		Assert.assertTrue(schema.getComponents().size() > 0);
	}

	@Test
	public void test_getComponentByName() {
		FIXMLSchema schema = spec.getSchema();
		FIXMLComponent component = schema.getComponentByName("MDReqGrp");
		Assert.assertNotNull(component);
	}

	@Test
	public void test_getFields() {
		FIXMLSchema schema = spec.getSchema();
		Assert.assertTrue(schema.getFields().size() > 0);
	}

	@Test
	public void test_getFieldByName() {
		FIXMLSchema schema = spec.getSchema();
		FIXMLField field = schema.getFieldByName("NoMDEntryTypes");
		Assert.assertNotNull(field);
	}
}
