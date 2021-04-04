package com.github.hardlolmaster.controller;

import lombok.Data;

@Data
public class CommandObject {
    private String command;
    private Object payload;
}
