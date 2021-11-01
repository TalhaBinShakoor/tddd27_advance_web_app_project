package org.liu.master.cs.apimonitor.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class CryptoHelper {

    static String publicKey() throws IOException {
        System.out.println("public key");
        return read("public_key.pem");
    }

    static String privateKey() throws IOException {
        System.out.println("private key");
        return read("private_key.pem");
    }

    private static String read(String file) throws IOException {
        Path path = Paths.get(".", file);
        if (!path.toFile().exists()) {
            path = Paths.get("..", ".", file);
        }
        System.out.println("public key");
        return String.join("\n", Files.readAllLines(path, StandardCharsets.UTF_8));
    }
}
