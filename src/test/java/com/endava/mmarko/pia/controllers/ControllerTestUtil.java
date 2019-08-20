package com.endava.mmarko.pia.controllers;

import org.springframework.http.MediaType;

import java.nio.charset.Charset;

public class ControllerTestUtil {
    public static MediaType JSON_CONTENT_TYPE = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );
}
