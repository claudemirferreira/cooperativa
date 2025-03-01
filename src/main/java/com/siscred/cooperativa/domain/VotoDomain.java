package com.siscred.cooperativa.domain;

import com.siscred.cooperativa.exception.CpfInvalidException;
import com.siscred.cooperativa.infrastructure.enuns.VotoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.IntStream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotoDomain {

    private Long id;
    private SessaoDomain sessao;
    private String cpf;
    private VotoEnum voto;

    public void validateCPF() {
        String sanitizedCpf = cpf.replaceAll("[^0-9]", "");

        if (sanitizedCpf.length() != 11) {
            throw new CpfInvalidException("Invalid CPF: incorrect length");
        }
        Set<String> invalidCpfs = Set.of(
            "00000000000", "11111111111", "22222222222", "33333333333",
            "44444444444", "55555555555", "66666666666", "77777777777",
            "88888888888", "99999999999"
        );
        if (invalidCpfs.contains(sanitizedCpf)) {
            throw new CpfInvalidException("Invalid CPF: repetitive numbers");
        }
        if (!isValidCpfDigits(sanitizedCpf)) {
            throw new CpfInvalidException("Invalid CPF: incorrect verification digits");
        }
    }

    private boolean isValidCpfDigits(String cpf) {
        return calculateDigit(cpf, 10) == Character.getNumericValue(cpf.charAt(9)) &&
               calculateDigit(cpf, 11) == Character.getNumericValue(cpf.charAt(10));
    }

    private int calculateDigit(String cpf, int weight) {
        int sum = IntStream.range(0, weight - 1)
            .map(i -> Character.getNumericValue(cpf.charAt(i)) * (weight - i))
            .sum();

        int digit = 11 - (sum % 11);
        return (digit >= 10) ? 0 : digit;
    }
}
