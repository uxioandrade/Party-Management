/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author alumnogreibd
 */
public class Password {
    
    private static int log_rounds = 10;
    
    public static String hashPasssword(String passwordText) {
        String salt = BCrypt.gensalt(log_rounds);
        String hashPassword = BCrypt.hashpw(passwordText, salt);

        return hashPassword;
    }
    
    public static boolean checkPassword(String passwordText, String storedPassword) 
    {
        boolean verifiedPassword = false;

        if(storedPassword == null || !storedPassword.startsWith("$2a$"))
                throw new java.lang.IllegalArgumentException("Hash is not correct");

        verifiedPassword = BCrypt.checkpw(passwordText, storedPassword);

        return verifiedPassword;
    }
}
