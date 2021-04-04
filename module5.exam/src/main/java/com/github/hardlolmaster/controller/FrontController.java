package com.github.hardlolmaster.controller;

import com.github.hardlolmaster.actions.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FrontController {
    private final Map<String, AbstractAction> actionMap;

    @Autowired
    public FrontController(Map<String, AbstractAction> actionMap) {
        this.actionMap = actionMap;
    }

    @PostMapping(path = "/property.insurance")
    public ResponseObject<?> executeAction(@RequestBody CommandObject commandObject) {
        String command = commandObject.getCommand();
        if (command == null || command.isEmpty())
            return new ResponseObject<>(false, "Command must be not null");
        AbstractAction action = actionMap.get(command);
        return action.execute(commandObject.getPayload());
    }
}
