package com.vm.gerenciaUsuarios.services;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailServiceTest {
    private final EmailService emailService = new EmailService();

    @Test
    void testEnviaEmailUpdateTrue() throws Exception {
        // Arrange
        boolean update = true;
        String email = "teste@exemplo.com";

        // Act and Assert
        String output = SystemLambda.tapSystemOut(() -> {
            emailService.enviaEmail(update, email);
        });

        // Assert
        assertTrue(output.contains("O cadastro foi realizado e foi enviado um email para " + email));
    }

    @Test
    void testEnviaEmailUpdateFalse() throws Exception {
        // Arrange
        boolean update = false;
        String email = "teste@exemplo.com";

        // Act and Assert
        String output = SystemLambda.tapSystemOut(() -> {
            emailService.enviaEmail(update, email);
        });

        // Assert
        assertTrue(output.contains("A edição foi realizada e foi enviado um email para " + email));
    }
}
