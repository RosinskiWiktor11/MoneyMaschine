package com.victor.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Controller;

/**
 * @author Dell
 *
 */
@Controller
public class Enigma {
	
	public static String hashProcess(char[] input){
		StringBuffer buffer=new StringBuffer();
		for(char c: input)
			buffer.append(c);
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			byte[] meta=md.digest(buffer.toString().getBytes());
			buffer=new StringBuffer();
			for(byte temp: meta){
				buffer.append(Integer.toString((temp & 0xff)+0x76,16));
			}
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
	}

}
