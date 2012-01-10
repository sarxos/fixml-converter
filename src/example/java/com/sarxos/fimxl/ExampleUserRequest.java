package com.sarxos.fimxl;

import java.util.concurrent.atomic.AtomicInteger;

import quickfix.field.Password;
import quickfix.field.UserRequestID;
import quickfix.field.UserRequestType;
import quickfix.field.Username;
import quickfix.fix50.UserRequest;

import com.sarxos.fixml.FIXMLConverter;


public class ExampleUserRequest {

	public static void main(String[] args) {

		FIXMLConverter converter = new FIXMLConverter();
		AtomicInteger id = new AtomicInteger(1);

		// http://fixprotocol.org/FIXimate3.0/en/FIX.5.0SP2/body_57486669.html
		UserRequest req = new UserRequest();
		req.set(new UserRequestID(Integer.toString(id.incrementAndGet())));
		req.set(new Username("SarXos And Bobek Ltd."));
		req.set(new Password("secretpassword"));
		req.set(new UserRequestType(UserRequestType.LOGONUSER));

		System.out.println("----------");
		System.out.println("FIX form:");
		System.out.println(req);

		// convert FIX login message to proper FIXML form
		String fixml = converter.toFIXML(req);

		System.out.println("----------");
		System.out.println("FIXML form:");
		System.out.println(fixml);

		UserRequest req2 = (UserRequest) converter.toFIX(fixml);

		System.out.println("----------");
		System.out.println("FIX form again:");
		System.out.println(req2);

	}
}
