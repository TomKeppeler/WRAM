/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WRAMApplication extends SpringBootServletInitializer {
    /**
     * Main Methode
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(WRAMApplication.class, args);
    }

}
