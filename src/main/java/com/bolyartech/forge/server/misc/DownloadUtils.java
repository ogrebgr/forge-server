package com.bolyartech.forge.server.misc;

import com.google.common.io.ByteStreams;

import java.io.*;


public class DownloadUtils {


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
