package com.wm.brta.utility;


import java.util.Properties;








import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



import com.wm.brta.domain.User;


public class MailServiceUtility {
	static String  host = "mail.wm.com";
	static String port = "25";
	
	static String to = "dameya@quinnox.com"; 
	static String from = "do-notreply@wm.com"; 
    
    
    
    public static void sendMail(User persistenceObject) {
    	Properties _properties = new Properties();
    	_properties.setProperty("mail.smtp.host", host);
    	_properties.setProperty("mail.smtp.port", port);
    	Session session = Session.getDefaultInstance(_properties);
    	try {
    		MimeMessage _message = new MimeMessage(session);
    		_message.setFrom(new InternetAddress(from));
    		_message.addRecipient(Message.RecipientType.TO, new InternetAddress(
    				persistenceObject.getEmailId().trim()));
    		_message.addRecipient(Message.RecipientType.CC, new InternetAddress(
    	    		"chandans@quinnox.com"));
    		_message.setSubject("Welcome to Bale Route tracking Application");
    		_message.setText("Hello,You can download your app by clicking the below link. Your registered phone number is:"+persistenceObject.getMobilePhone());

    		// Send message
    		Transport.send(_message);
    		

    		} catch (MessagingException mex) {
    		mex.printStackTrace();
    		}

    		}



}
