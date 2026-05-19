package com.uma.example.springuma.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    public static byte[] compressImage(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] tmp = new byte[4 * 1024];
            while (!deflater.finished()) {
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            deflater.end();
        }
    }

    public static byte[] decompressImage(byte[] data) throws IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] tmp = new byte[4 * 1024];
            try {
                while (!inflater.finished()) {
                    int count = inflater.inflate(tmp);
                    outputStream.write(tmp, 0, count);
                }
            } catch (DataFormatException e) {
                throw new IOException("Failed to decompress image data", e);
            }
            return outputStream.toByteArray();
        } finally {
            inflater.end();
        }
    }

}
