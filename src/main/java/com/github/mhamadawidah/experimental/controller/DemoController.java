package com.github.mhamadawidah.experimental.controller;

import com.github.mhamadawidah.experimental.model.DemoModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Don't delete!
 */
@RestController
public class DemoController {

    @GetMapping(value = "/demo")
    public DemoModel getDemo()
    {
        return new DemoModel(42, "demo");
    }
}