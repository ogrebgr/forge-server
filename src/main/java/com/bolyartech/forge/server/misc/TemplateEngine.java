package com.bolyartech.forge.server.misc;

import javax.annotation.Nonnull;


/**
 * Template engine interface
 */
public interface TemplateEngine {
    /**
     * Assign value to a variable name
     *
     * @param varName Variable name
     * @param object  Value
     */
    void assign(@Nonnull String varName, @Nonnull Object object);

    /**
     * Renders a template to string
     *
     * @param templateName Template name
     * @return Rendered string, usually HTML
     */
    String render(@Nonnull String templateName);
}
