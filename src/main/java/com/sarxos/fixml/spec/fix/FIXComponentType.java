package com.sarxos.fixml.spec.fix;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


@XmlEnum
public enum FIXComponentType {

	@XmlEnumValue("ImplicitBlockRepeating")
	IMPLICIT_BLOCK_REPEATING,

	@XmlEnumValue("BlockRepeating")
	BLOCK_REPEATING,

	@XmlEnumValue("Block")
	BLOCK,
}
