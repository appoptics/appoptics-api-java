package com.appoptics.metrics.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Encapsulates logic about versions found in POM files
 */
public class Versions {

    private Versions() {
        // do not construct.
    }

    /**
     * Attempts to get a version property from a specified resource
     *
     * @param path  the path of the properties file resource
     * @param klass the Class whose classloader will be used to load the resource
     * @return the found version, "unknown" if it could not be found / determined
     */
    public static String getVersion(String path, Class<?> klass) {
        try {
            final InputStream in = klass.getClassLoader().getResourceAsStream(path);
            if (in != null) {
                try (final BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                    String line = reader.readLine();
                    while (line != null) {
                        if (line.startsWith("version")) {
                            return line.split("=")[1];
                        }
                        line = reader.readLine();
                    }
                }
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(Versions.class.getName());
            logger.log(Level.SEVERE, e, () -> "Could not read package version using path " + path);
        }
        return "unknown";
    }

}
