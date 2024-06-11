package com.web.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

//대칭키 암호화하기
//key 1개로 암호화, 복호화 다 처리하는 것

public class AESEncryptor {
	
	//1. key 생성 및 가져오기 - 한번 생성된 key를 이용해서 처리 -> 파일로 저장해서 관리
	// 1) 생성된 key가 있으면 그 key를 이용
	// 2) 생성된 key가 없으면 생성
	
	// 로직 구성하기
	// SecretKey클래스를 이용해서 대칭키를 관리
	private static SecretKey key;
	private String path; //key를 저장하는 파일의 위치 저장하는 변수
	
	public AESEncryptor() {
		this.path = AESEncryptor.class.getResource("/").getPath();
		// "/" -> 06_HelloMVC/src/main/webapp/WEB-INF/classes (Default output folder)
		this.path = this.path.substring(0, this.path.indexOf("classes"));
		File keyFile = new File(this.path + "bslove.bs");

		//파일에 키가 있으면
		if(keyFile.exists()) {
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(keyFile));){
				AESEncryptor.key = (SecretKey)ois.readObject();
			}catch(IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else {
			generateKey();
		}
	}
	private void generateKey() {
		SecureRandom rnd = new SecureRandom();
		//Math.Random 사용해도 됨
		KeyGenerator keygen = null;
		
		try(ObjectOutputStream oos 
				= new ObjectOutputStream(
						new FileOutputStream(this.path + "bslove.bs"))) {
			keygen = KeyGenerator.getInstance("AES");
			keygen.init(128, rnd); //128비트
			AESEncryptor.key = keygen.generateKey();
			oos.writeObject(AESEncryptor.key);
			
		}catch(NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}
//	리스너 만들기
//	contextInitialized()
//	    	new AESEncryptor();

	
	//Cipher클래스를 이용해서 암호화, 복호화 처리!
	
	//암호화 메소드 설정하기
	public static String encryptData(String oriVal) throws Exception{

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, AESEncryptor.key);
		//(암호화 모드, 키)
		
		byte[] oribyte = oriVal.getBytes(Charset.forName("UTF-8"));
		byte[] encryptByte = cipher.doFinal(oribyte);
		
		return Base64.getEncoder().encodeToString(encryptByte);
	}
	
	//복호화 메소드 설정하기
	public static String decryptData(String encryptVal) throws Exception{
		
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, AESEncryptor.key);
		//암호화 반대로
		byte[] encryptByte = Base64.getDecoder().decode(encryptVal.getBytes(Charset.forName("UTF-8")));
		byte[] decryptByte = cipher.doFinal(encryptByte);
		
		return new String(decryptByte);
	}

}
