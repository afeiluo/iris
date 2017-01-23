package com.afeiluo.jdk_serialization.compress;

import org.apache.curator.framework.api.CompressionProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipCompressionProvider implements CompressionProvider {
    @Override
    public byte[] compress(String path, byte[] data) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        GZIPOutputStream out = new GZIPOutputStream(bytes);
        try {
            out.write(data);
            out.finish();
        } finally {
            out.close();
        }
        return bytes.toByteArray();
    }

    @Override
    public byte[] decompress(String path, byte[] compressedData) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(compressedData.length);
        GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(compressedData));
        try {
            byte[] buffer = new byte[compressedData.length];
            for (;;) {
                int bytesRead = in.read(buffer, 0, buffer.length);
                if (bytesRead < 0) {
                    break;
                }
                bytes.write(buffer, 0, bytesRead);
            }
        } finally {
            in.close();
        }
        return bytes.toByteArray();
    }
}
