package com.github.hardlolmaster.actions;

import com.github.hardlolmaster.controller.ResponseObject;

public abstract class AbstractAction {
    public abstract ResponseObject<?> execute(Object input);
}
