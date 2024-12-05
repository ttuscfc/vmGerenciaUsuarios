package com.vm.gerenciaUsuarios.services;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void enviaEmail(boolean update, String email) {
        if (update) {
            System.out.println("O cadastro foi realizado e foi enviado um email para " + email);
        }
        else {
            System.out.println("A edição foi realizada e foi enviado um email para " + email);
        }

    }
}
