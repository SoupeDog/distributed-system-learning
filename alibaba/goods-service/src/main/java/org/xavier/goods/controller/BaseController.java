package org.xavier.goods.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;

public class BaseController {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> serviceErrorHandler(final Throwable e) {
        MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        builder.contentType(mediaType);
        return builder.body(new LinkedHashMap() {{
            put("code", 500);
            put("msg", e.getMessage());
        }});
    }
}