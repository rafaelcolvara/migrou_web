package com.migrou.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.sql.SQLException;

@RestController
@RequestMapping(value = "/")
@CrossOrigin(origins = "*")
public class TesteController {

    @Autowired
    public RequestMappingHandlerMapping requestMappingHandlerMapping;
    @GetMapping(value = "/allEndpoints")
    public @ResponseBody Object Teste() throws SQLException {
        return requestMappingHandlerMapping.getHandlerMethods().keySet().stream().map(t ->
                (t.getMethodsCondition().getMethods().size() == 0 ? "GET" : t.getMethodsCondition().getMethods().toArray()[0]) + " " +
                        t.getPatternsCondition().getPatterns().toArray()[0]
        ).toArray();
    }
}
