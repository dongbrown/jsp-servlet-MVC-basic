package com.web.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy {
	
	@Override
	public File rename(File ori) {
		File newFile = null;
		do {
			//명칭_날짜(ms)_랜덤값.확장자
			long currentTime = System.currentTimeMillis(); //시간(ms까지)
			int rndNum = (int)(Math.random() * 10000) + 1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
			
			//원본파일에서 확장자 가져오기
			String oriFilename = ori.getName();
			String ext = oriFilename.substring(oriFilename.lastIndexOf("."));// 마지막 점까지 자르기
			String newName = "BSLOVE_" + sdf.format(currentTime) + "_" + rndNum + ext;
			newFile = new File(ori.getParent(), newName);
			
		}while(!createFile(newFile));
		
		return newFile;
	}
	
	//생성 되는지 안 되는지 (false -> while문 한 번 더 돌게)
	private boolean createFile(File newFile) {
		try {
			return newFile.createNewFile();
		}catch(IOException e) {
			return false;
		}
	}
	
}

//
//
//
//
//
//
//

