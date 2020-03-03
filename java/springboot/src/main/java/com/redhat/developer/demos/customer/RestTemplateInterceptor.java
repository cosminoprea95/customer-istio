//package com.redhat.developer.demos.customer;
//
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Set;
//
///**
// * Created by cosminoprea on 3/3/20.
// */
//public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
//
//    @Override
//    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//
//        HashMap<String, String> headers = RequestContext.getContext().getHeaders();
//        if(headers!=null) {
//            Set<String> headersSet = headers.keySet();
//
//            for (String header : headersSet) {
//                request.getHeaders().add(header, headers.get(header));
//            }
//        }
//        return execution.execute(request, body);
//
//    }
//
//}
