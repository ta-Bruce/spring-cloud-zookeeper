package com.myproject.common.exception.api.handler;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        String body = "";
        try {
            Reader reader = response.body().asReader();
            body = IOUtils.toString(reader);
        } catch (IOException ex){
            body = "";
        }
        finally {
            throw new FeignException.FeignClientException(response.status(), response.reason(),
                    response.request(), body.getBytes());
        }
    }
}
