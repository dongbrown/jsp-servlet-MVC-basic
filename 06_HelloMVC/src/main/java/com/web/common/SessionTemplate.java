package com.web.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SessionTemplate {
	
	public static SqlSession getSession() {
		SqlSession session = null;
		String fileName = "mybatis-configxml";
		try(InputStream is = Resources.getResourceAsStream(fileName);){
			SqlSessionFactoryBuilder sfb = new SqlSessionFactoryBuilder();
			SqlSessionFactory sf = sfb.build(is);
			session = sf.openSession(false);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return session;
	}

}
