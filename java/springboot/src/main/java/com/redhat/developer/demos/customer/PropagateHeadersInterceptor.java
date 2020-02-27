//package com.redhat.developer.demos.customer;
//
//import java.util.Enumeration;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
///**
// * Created by cosminoprea on 2/27/20.
// */
//@Component
//public class PropagateHeadersInterceptor implements RequestInterceptor {
//
//    private @Autowired
//    HttpServletRequest request;
//
//    public void apply(RequestTemplate template) {
//        try
//        {
//            Enumeration<String> e = request.getHeaderNames();
//            while (e.hasMoreElements())
//            {
//                String header = e.nextElement();
//                if (header.startsWith("x-"))
//                {
//                    String value = request.getHeader(header);
//                    template.header(header, value);
//                }
//            }
//        }
//        catch (IllegalStateException e) {}
//    }
//}