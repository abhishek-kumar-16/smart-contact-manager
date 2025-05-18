package com.smartmanager.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class sessionHelpers {
     
    public static void removeNotification() {
        //  fetching the current session object
        try{
            System.out.println("Removing notification");
 HttpSession sesssion = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        sesssion.removeAttribute("message");
        }
        catch (Exception e){
            System.out.println("Error removing notification: " + e.getMessage());
        }
       
    }


}
