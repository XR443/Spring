package com.github.hardlolmaster.actions;

import com.github.hardlolmaster.controller.ResponseObject;
import org.springframework.stereotype.Component;

@Component
public class LoginAction extends AbstractAction
{
    @Override public ResponseObject<?> execute(Object input)
    {
        return new ResponseObject<>(true, "OK");
    }
}
