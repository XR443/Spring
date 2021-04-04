package com.github.hardlolmaster.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject<Payload> {
    private boolean ok;
    private Payload payload;
}
