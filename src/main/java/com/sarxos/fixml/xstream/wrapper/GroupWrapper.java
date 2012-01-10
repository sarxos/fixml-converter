package com.sarxos.fixml.xstream.wrapper;

import quickfix.Group;

import com.sarxos.fixml.spec.ml.FIXMLGroup;


public class GroupWrapper {

	private Group fixGroup = null;
	private FIXMLGroup group = null;

	public GroupWrapper(Group fixGroup, FIXMLGroup group) {
		super();
		this.fixGroup = fixGroup;
		this.group = group;
	}

	public Group getFIXGroup() {
		return fixGroup;
	}

	public FIXMLGroup getGroup() {
		return group;
	}
}
