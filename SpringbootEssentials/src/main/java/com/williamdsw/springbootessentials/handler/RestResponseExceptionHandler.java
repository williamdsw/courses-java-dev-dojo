package com.williamdsw.springbootessentials.handler;

import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * @author William
 */

@ControllerAdvice
public class RestResponseExceptionHandler extends DefaultResponseErrorHandler
{
    //------------------------------------------------------------------------//
    // OVERRIDED FUNCTIONS

    @Override
    public boolean hasError (ClientHttpResponse response) throws IOException
    {
        System.out.println ("Inside hasError ");
        return super.hasError (response); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleError (ClientHttpResponse response) throws IOException
    {
        System.out.println ("Doing something with status code: " + response.getStatusCode ());
        System.out.println ("Doing something with body: " + IOUtils.toString (response.getBody (), "UTF-8"));
    }
}