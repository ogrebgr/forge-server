package com.bolyartech.forge.server.misc;

import com.google.common.io.ByteStreams;

import java.io.*;


/**
 * Utility class for downloading files
 */
public class DownloadUtils {

    private DownloadUtils() {
        throw new AssertionError("Non-instantiable utility class");
    }


    /**
     * Saves an input stream as file
     *
     * @param is          Input stream
     * @param destination file path to be used
     * @throws IOException if there is an IO error
     */
    public static void saveDownloadedFile(InputStream is, String destination) throws IOException {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(new File(destination)));
            ByteStreams.copy(is, out);
        } finally {
            is.close();
            if (out != null) {
                out.close();
            }
        }
    }
}
