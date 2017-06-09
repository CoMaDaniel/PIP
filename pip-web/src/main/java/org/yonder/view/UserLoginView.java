package org.yonder.view;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.yonder.services.TestService;
 
@ManagedBean (name = "userLoginView")
public class UserLoginView {
    
	@ManagedProperty("#{testService}")
	private TestService testService;
	
	final static Logger logger = Logger.getLogger(UserLoginView.class);
	
    private String username;
     
    private String password;
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
   
    public void login() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
        boolean validPsw = validatePasswordSha(username, password);
        if(validPsw) {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
            logger.info("Is password valid??? -" + validPsw);
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid user or password.");
            logger.info("Is password valid??? -" + validPsw);
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }   
    
    public boolean validatePasswordSha(String userName, String pass) {
    	
    	boolean valid = false;
    	String hash = hashPasswordSha(pass);
    	
    	if(username != null && password != null && hash.equals(testService.getPasswordForUser(userName))) {
    		valid = true;
    	}
    	return valid;
    }
    
    public String hashPasswordSha(String password) {
    	
        String hashedPassword = null;
        try {
            // Create MessageDigest object for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Update input string in message digest
            digest.update(password.getBytes(), 0, password.length());

            // Converts message digest value in base 16 (hex)
            hashedPassword = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        logger.info("Hashed password is: " + hashedPassword);
        password = hashedPassword;
        return password;
    }
    
    public void setTestService(TestService testService) {
		this.testService = testService;
	}
}
