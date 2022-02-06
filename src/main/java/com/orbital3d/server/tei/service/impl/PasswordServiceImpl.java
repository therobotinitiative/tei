package com.orbital3d.server.tei.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.orbital3d.server.tei.service.PasswordService;

@Service
public class PasswordServiceImpl implements PasswordService
{
	private static final Logger LOG = LoggerFactory.getLogger(PasswordServiceImpl.class);

	private static final DigestUtils DIGEST_UTILS = new DigestUtils("SHA3-256");

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] hashPassword(String password, byte[] salt)
	{
		byte[] haashed = hash(password.getBytes(), salt);
		byte[] h = hash(haashed, haashed);
		for (int i = 0; i < 500; i++)
		{
			h = hash(h, haashed);
		}
		return h;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] generateSalt()
	{
		byte[] block = new byte[2048];
		try
		{
			SecureRandom.getInstanceStrong().nextBytes(block);
		}
		catch (NoSuchAlgorithmException e)
		{
			LOG.warn("Could not create secure random", e);
		}
		return Base64.getEncoder().encode(block);
	}

	/*
	 * Combines the two input byte arrays into one and returns it hashed with
	 * SHA3-256.
	 */
	private byte[] hash(byte[] a1, byte[] a2)
	{
		byte[] comb = new byte[a1.length + a2.length];
		System.arraycopy(a1, 0, comb, 0, a1.length);
		System.arraycopy(a2, 0, comb, a1.length, a2.length);
		return (DIGEST_UTILS.digestAsHex(comb)).getBytes();
	}
}