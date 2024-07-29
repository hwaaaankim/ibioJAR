package com.dev.IBIOECommerceJAR.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class HtmlRenderer {

    @Autowired
    private SpringTemplateEngine templateEngine;

    public String render(String template, Context context) {
        return templateEngine.process(template, context);
    }
}
