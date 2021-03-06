package com.bolyartech.forge.server.misc;

import javax.annotation.Nonnull;


/**
 * MIME type resolver interface
 */
public interface MimeTypeResolver {
    /**
     * Tries to resolve the MIME type by the file extension
     *
     * @param fileName File name
     * @return MIME type literal
     */
    String resolveForFilename(@Nonnull String fileName);
}
