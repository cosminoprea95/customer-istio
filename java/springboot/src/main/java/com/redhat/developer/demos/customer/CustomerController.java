package com.redhat.developer.demos.customer;

import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sun.jvm.hotspot.memory.HeapBlock;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
public class CustomerController {

    private static final String RESPONSE_STRING_FORMAT = "customer => %s\n";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;

    @Value("${preferences.api.url:http://preference:8080}")
    private String remoteURL;

    @Autowired
    private Tracer tracer;

    public CustomerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/")
    public ResponseEntity<String> getCustomer(@RequestHeader("User-Agent") String userAgent, @RequestHeader(value = "user-preference", required = false) String userPreference,
                                              HttpServletRequest request) throws Exception {
        try {

            /**
             * Set baggage
             */
            String header = request.getHeader("x-api-key");
            if(header == null){
                throw new Exception("There is no x-api-key");
            }

            tracer.activeSpan().setBaggageItem("user-agent", userAgent);
            if (userPreference != null && !userPreference.isEmpty()) {
                tracer.activeSpan().setBaggageItem("user-preference", userPreference);
            }
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("x-api-key", header);
            ResponseEntity<String> entity = new RestTemplate().exchange(
                    remoteURL, HttpMethod.GET, new HttpEntity<Object>(headers),
                    String.class);

//            ResponseEntity<String> responseEntity = restTemplate.getForEntity(remoteURL, String.class);
            String response = entity.getBody();
            return ResponseEntity.ok(String.format(RESPONSE_STRING_FORMAT, response.trim()));
        } catch (HttpStatusCodeException ex) {
            logger.warn("Exception trying to get the response from preference service.", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(String.format(RESPONSE_STRING_FORMAT,
                            String.format("%d %s", ex.getRawStatusCode(), createHttpErrorResponseString(ex))));
        } catch (RestClientException ex) {
            logger.warn("Exception trying to get the response from preference service.", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(String.format(RESPONSE_STRING_FORMAT, ex.getMessage()));
        }
    }

    private String createHttpErrorResponseString(HttpStatusCodeException ex) {
        String responseBody = ex.getResponseBodyAsString().trim();
        if (responseBody.startsWith("null")) {
            return ex.getStatusCode().getReasonPhrase();
        }
        return responseBody;
    }

}
