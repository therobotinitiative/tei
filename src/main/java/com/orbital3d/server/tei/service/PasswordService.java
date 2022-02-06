package com.orbital3d.server.tei.service;

/**
 * Service for password related operations.
 * 
 * @author msiren
 *
 */
public interface PasswordService
{
	/**
	 * @return New salt for password
	 */
	byte[] generateSalt();

	/**
	 * @param password Plain text password
	 * @param salt     Password salr
	 * @return Hashed secure password
	 */
	byte[] hashPassword(String password, byte[] salt);
}
