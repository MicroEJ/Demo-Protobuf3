/*
 * Java
 *
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.protobuf3example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.protobuf.Timestamp;
import com.microej.example.protobuf3example.AppProto.AppMessage;
import com.microej.example.protobuf3example.AppProto.Encryption;

/**
 * Writes a message to a byte array and then reads from it and prints its values on the console.
 */
public class Main {

	/**
	 * Writes a message to a byte array and then reads from it and prints its values on the console.
	 *
	 * @param args
	 *            useless.
	 * @throws IOException
	 *             if the data cannot be written or read.
	 */
	public static void main(String[] args) throws IOException {
		byte[] array = write();
		read(array);
	}

	private static byte[] write() throws IOException {
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.log(Level.INFO, "* Writing to ByteArray *");

		AppMessage.Builder messageBuilder = AppMessage.newBuilder();
		Timestamp.Builder timestampBuilder = Timestamp.newBuilder();

		timestampBuilder.setSeconds(System.currentTimeMillis() / 1000);
		Timestamp date = timestampBuilder.build();
		messageBuilder.setId(42).setSubject("Hello").setMessageContent("Hello World!").setDate(date)
				.setEncryption(Encryption.PGP);
		AppMessage message = messageBuilder.build();

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		message.writeTo(output);
		byte[] array = output.toByteArray();
		return array;
	}

	private static void read(byte[] array) throws IOException {
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.log(Level.INFO, "* Reading from ByteArray *");

		ByteArrayInputStream input = new ByteArrayInputStream(array);
		AppMessage message = AppMessage.parseFrom(input);

		logger.log(Level.INFO, "id:\t" + message.getId());
		logger.log(Level.INFO, "subject:\t" + message.getSubject());
		logger.log(Level.INFO, "message:\t" + message.getMessageContent());
		logger.log(Level.INFO, "date:\t" + message.getDate().getSeconds());
		logger.log(Level.INFO, "encryption:" + message.getEncryption().name());
	}

}
