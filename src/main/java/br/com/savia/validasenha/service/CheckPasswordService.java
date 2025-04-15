package br.com.savia.validasenha.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CheckPasswordService {
    private static final Set<Character> SPECIALS = Set.of('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+');
    private final ExecutorService executor = Executors.newFixedThreadPool(6);

    public CompletableFuture<Boolean> checkPwd(String pwd) {
        CompletableFuture<Boolean>[] validations = new CompletableFuture[]{
                checkLength(pwd),
                checkDigit(pwd),
                checkLowerCase(pwd),
                checkUpperCase(pwd),
                checkSpecial(pwd),
                checkNonRepetition(pwd)
        };

        return CompletableFuture.allOf(validations)
                .thenApply(v -> Arrays.stream(validations)
                        .allMatch(CompletableFuture::join));
    }

    private CompletableFuture<Boolean> checkLength(String senha) {
        return CompletableFuture.supplyAsync(() -> {
            sleep();
            return senha.length() >= 9;
        }, executor);
    }

    private CompletableFuture<Boolean> checkDigit(String senha) {
        return CompletableFuture.supplyAsync(() -> {
            sleep();
            return senha.matches(".*\\d.*");
        }, executor);
    }

    private CompletableFuture<Boolean> checkLowerCase(String senha) {
        return CompletableFuture.supplyAsync(() -> {
            sleep();
            return senha.matches(".*[a-z].*");
        }, executor);
    }

    private CompletableFuture<Boolean> checkUpperCase(String senha) {
        return CompletableFuture.supplyAsync(() -> {
            sleep();
            return senha.matches(".*[A-Z].*");
        }, executor);
    }

    private CompletableFuture<Boolean> checkSpecial(String senha) {
        return CompletableFuture.supplyAsync(() -> {
            sleep();
            return senha.chars().mapToObj(c -> (char) c).anyMatch(SPECIALS::contains);
        }, executor);
    }

    private CompletableFuture<Boolean> checkNonRepetition(String senha) {
        return CompletableFuture.supplyAsync(() -> {
            sleep();
            return senha.chars().distinct().count() == senha.length();
        }, executor);
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
