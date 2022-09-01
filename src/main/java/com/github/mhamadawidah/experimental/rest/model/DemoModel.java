package com.github.mhamadawidah.experimental.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Demo")
public record DemoModel(int id, String name) {
}