package com.bolyartech.forge.server.misc;

/**
 * Template Engine Factory interface
 */
public interface TemplateEngineFactory {
    /**
     * Creates new {@link TemplateEngine}
     *
     * @return Template engine
     */
    TemplateEngine createNew();
}
