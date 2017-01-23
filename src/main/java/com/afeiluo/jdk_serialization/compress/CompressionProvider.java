package com.afeiluo.jdk_serialization.compress;

public interface CompressionProvider {
    public byte[] compress(String path, byte[] data) throws Exception;

    public byte[] decompress(String path, byte[] compressedData) throws Exception;
}
