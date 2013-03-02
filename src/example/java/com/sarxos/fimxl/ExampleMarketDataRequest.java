package com.sarxos.fimxl;

import java.util.concurrent.atomic.AtomicInteger;

import quickfix.Message;
import quickfix.field.MDEntryType;
import quickfix.field.MDReqID;
import quickfix.field.MarketDepth;
import quickfix.field.SecurityID;
import quickfix.field.SecurityIDSource;
import quickfix.field.SubscriptionRequestType;
import quickfix.field.Symbol;
import quickfix.fix50.MarketDataRequest;
import quickfix.fix50.MarketDataRequest.NoMDEntryTypes;
import quickfix.fix50.MarketDataRequest.NoRelatedSym;
import quickfix.fix50.component.Instrument;

import com.sarxos.fixml.FIXMLConverter;


public class ExampleMarketDataRequest {

	public static void main(String[] args) {

		FIXMLConverter converter = new FIXMLConverter();
		AtomicInteger id = new AtomicInteger(1);

		MarketDataRequest req = new MarketDataRequest();
		req.set(new MDReqID(Integer.toString(id.incrementAndGet())));
		req.set(new SubscriptionRequestType(SubscriptionRequestType.SNAPSHOT_PLUS_UPDATES));
		req.set(new MarketDepth(1));

		// add repeating group for OHLC and bid/offer

		NoMDEntryTypes types = new NoMDEntryTypes();

		// OHLC
		types.set(new MDEntryType(MDEntryType.OPENING_PRICE));
		req.addGroup(types);
		types.set(new MDEntryType(MDEntryType.TRADING_SESSION_HIGH_PRICE));
		req.addGroup(types);
		types.set(new MDEntryType(MDEntryType.TRADING_SESSION_LOW_PRICE));
		req.addGroup(types);
		types.set(new MDEntryType(MDEntryType.CLOSING_PRICE));
		req.addGroup(types);

		// bid/offer
		types.set(new MDEntryType(MDEntryType.BID));
		req.addGroup(types);
		types.set(new MDEntryType(MDEntryType.OFFER));
		req.addGroup(types);

		// add symbols
		NoRelatedSym symbols = new NoRelatedSym();

		Instrument instr = new Instrument();

		// KGHM SA
		instr.set(new Symbol("KGHM")); // symbol
		instr.set(new SecurityID("PLKGH200001")); // ISIN
		instr.set(new SecurityIDSource(SecurityIDSource.ISIN_NUMBER));
		symbols.set(instr);
		req.addGroup(symbols);

		// MCI Management
		instr.set(new Symbol("MCI")); // symbol
		instr.set(new SecurityID("PLMCI000AAX")); // ISIN
		instr.set(new SecurityIDSource(SecurityIDSource.ISIN_NUMBER));
		symbols.set(instr);
		req.addGroup(symbols);

		System.out.println(req);

		// convert FIX login message to proper FIXML form

		String fixml = converter.toFIXML(req);
		System.out.println(fixml);

		Message message = converter.toFIX(fixml);

		System.out.println(message);

	}
}
