package org.uv.DAPPWEBPractica07;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("1234"); // contraseña que quieras usar
        System.out.println("Nuevo hash: " + hash);
    }
}
