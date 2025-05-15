package com.JobPortal.job_portal.utils;

public class FileUtils {

    /**
     * Returns the extension of a file (without the dot).
     * If the file name has no extension, returns an empty string.
     *
     * @param fileName The name of the file (e.g., "resume.pdf").
     * @return The file extension (e.g., "pdf").
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1 || fileName.lastIndexOf('.') == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}

