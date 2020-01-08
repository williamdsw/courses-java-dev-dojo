package com.williamdsw.springbootessentials.javaclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

/**
 * @author William
 */
public class JavaClientTest
{
    public static void main (String[] args)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String username = "dave";
        String password = "devdojo";
        
        try
        {
            // Conexao
            String property = "Basic " + encodeUsernamePassword (username, password);
            URL url = new URL ("http://localhost:8080/v1/protected/students/10");
            connection = (HttpURLConnection) url.openConnection ();
            connection.setRequestMethod ("GET");
            connection.addRequestProperty ("Authorization", property);
            InputStreamReader input = new InputStreamReader (connection.getInputStream ());
            reader = new BufferedReader (input);
            
            // Resultado
            StringBuilder builder = new StringBuilder ();
            String line;
            
            while ((line = reader.readLine ()) != null)
            {
                builder.append (line);
            }
            
            System.out.println (builder.toString ());
        }
        catch (IOException exception)
        {
            System.out.println (exception.getMessage ());
            exception.printStackTrace ();
        }
        finally
        {
            IOUtils.closeQuietly (reader);
            if (connection != null)
            {
                connection.disconnect ();
            }
        }
    }
    
    //------------------------------------------------------------------------//
    // HELPER FUNCTIONS
    
    private static String encodeUsernamePassword (String username, String password)
    {
        String userPassword = String.format ("%s:%s", username, password);
        return new String (Base64.encodeBase64 (userPassword.getBytes ()));
    }
}