package br.com.savia.validasenha.controller;

import br.com.savia.validasenha.domain.PasswordDTO;
import br.com.savia.validasenha.service.CheckPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/password")
public class CheckPasswordController {

    private final CheckPasswordService checkPasswordService;


    public CheckPasswordController(CheckPasswordService checkPasswordService) {
        this.checkPasswordService = checkPasswordService;
    }

    @PostMapping("/check")
    public CompletableFuture<ResponseEntity<Boolean>> checkPassword(@RequestBody PasswordDTO passwordDTO) {
        System.out.println("Entrou no PasswordController com sucesso");

        CompletableFuture<ResponseEntity<Boolean>> result = checkPasswordService.checkPwd(passwordDTO.getPassword())
                .thenApply(ResponseEntity::ok);

        return result;

    }
}
