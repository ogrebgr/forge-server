package com.bolyartech.forge.server.misc;

public interface TemplateEngine {
    void assign(String varName, Object object);

    String render(String templateName);
}
