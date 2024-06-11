package com.web.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.web.common.AESEncryptor;

/**
 * Application Lifecycle Listener implementation class ServerConfigListener
 *
 */
@WebListener
public class ServerConfigListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ServerConfigListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	System.out.println("키 생성");
    	new AESEncryptor();
    }
	
}
