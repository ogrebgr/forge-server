package com.bolyartech.forge.server.misc;

import javax.annotation.Nonnull;


/**
 * Template engine interface
 */
public interface TemplateEngine {
    /**
     * Assign value to a variable name
     * If variable already assigned, value will be overwritten
     *
     * @param varName Variable name
     * @param object  Value
     */
    void assign(@Nonnull String varName, @Nonnull Object object);

    /**
     * Assigns boolean <code>true</code> to a variable
     * If variable already assigned, value will be overwritten
     *
     * @param varName Variable name
     */
    void assign(@Nonnull String varName);

    /**
     * Alias of {@link #assign(String, Object)}
     */
    void export(@Nonnull String varName, @Nonnull Object object);

    /**
     * Alias of {@link #export(String)}
     */
    void export(@Nonnull String varName);

    /**
     * Renders a template to string
     *
     * @param templateName Template name
     * @return Rendered string, usually HTML
     */
    String render(@Nonnull String templateName);
}
