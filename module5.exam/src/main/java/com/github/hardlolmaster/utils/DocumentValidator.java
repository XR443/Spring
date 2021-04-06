package com.github.hardlolmaster.utils;

import com.github.hardlolmaster.domain.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentValidator implements Validator {
    @Override
    public boolean valid(Object object) {
        if (!(object instanceof Document))
            return false;
        Document document = (Document) object;
        if (document.getNumber() == null || document.getNumber().length() != 6)
            return false;
        return document.getSeries() != null && document.getSeries().length() == 4;
    }
}
