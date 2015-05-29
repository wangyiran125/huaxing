package org.chinalbs.logistics.common.utils;

import java.io.IOException;

public class ImageUtils {
    public static void copyAndScale(String sourceFile, String targetFile,
            int expectWidth, int expectHeight) throws IOException {
    	ScaleImage si = new ScaleImage();
    	si.saveImageAsJpg(sourceFile, targetFile, expectWidth, expectHeight);
    }
}
