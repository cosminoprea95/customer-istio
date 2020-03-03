package com.redhat.developer.demos.customer;

/**
 * Created by cosminoprea on 3/3/20.
 */
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

public class RequestFilter implements Filter{



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        HashMap<String, String> headers = new HashMap<>();
        while(headerNames.hasMoreElements()) {
            String headerKey = headerNames.nextElement();
            String headerValue = httpServletRequest.getHeader(headerKey);
            headers.put(headerKey,headerValue);
        }

        RequestContext.getContext().setHeaders(headers);
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() { }

    @Override
    public void init(FilterConfig arg0) throws ServletException {}


}