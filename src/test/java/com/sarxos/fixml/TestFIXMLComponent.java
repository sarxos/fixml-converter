package com.sarxos.fixml;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sarxos.fixml.spec.Spec;
import com.sarxos.fixml.spec.ml.FIXMLComponent;
import com.sarxos.fixml.spec.ml.FIXMLGroup;
import com.sarxos.fixml.spec.ml.FIXMLSchema;


public class TestFIXMLComponent {

	private static Spec spec = null;

	@BeforeClass
	public static void setUp() {
		spec = new Spec();
	}

	@Test
	public void test_toGroup() {
		FIXMLSchema schema = spec.getSchema();
		FIXMLComponent component = schema.getComponentByName("MDReqGrp");
		FIXMLGroup group = FIXMLComponent.toGroup(component);
		Assert.assertTrue(group.getName().equals("NoMDEntryTypes"));
	}
}
