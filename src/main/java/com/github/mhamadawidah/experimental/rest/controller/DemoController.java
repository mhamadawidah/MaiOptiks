package com.github.mhamadawidah.experimental.rest.controller;

import com.github.mhamadawidah.experimental.rest.model.DemoModel;
import com.sun.net.httpserver.Authenticator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Demo")
public class DemoController {

    @GetMapping(name = "GET Demo", value = "/demo/{id}", produces = "application/json")
    public DemoModel get(int id) {
        return new DemoModel(id, "Demo");
    }

    @PostMapping(name = "POST Demo", value = "/demo")
    public ResponseEntity<Authenticator.Success> post(@RequestBody DemoModel model) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(name = "DELETE Demo", value = "/demo/{id}")
    public ResponseEntity<?> delete(int id) {
        if (id > 0)
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}