package com.siscred.cooperativa.domain;

import com.siscred.cooperativa.exception.CpfInvalidException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VotoDomainTest {

    @Test
    void shouldValidateCpfSuccessfully() {
        VotoDomain voto = VotoDomain.builder()
                .cpf("529.982.247-25") // CPF válido
                .build();

        assertThatCode(voto::validateCPF).doesNotThrowAnyException();
    }

    @Test
    void shouldValidateCpfWithSpecialCharactersSuccessfully() {
        VotoDomain voto = VotoDomain.builder()
                .cpf("52998224725") // CPF válido sem pontuação
                .build();

        assertThatCode(voto::validateCPF).doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionForInvalidLengthCpf() {
        VotoDomain voto = VotoDomain.builder()
                .cpf("123456789") // CPF com menos de 11 caracteres
                .build();

        assertThatThrownBy(voto::validateCPF)
                .isInstanceOf(CpfInvalidException.class)
                .hasMessage("Invalid CPF: incorrect length");
    }

    @Test
    void shouldThrowExceptionForRepetitiveNumberCpf() {
        VotoDomain voto = VotoDomain.builder()
                .cpf("111.111.111-11") // CPF inválido: números repetitivos
                .build();

        assertThatThrownBy(voto::validateCPF)
                .isInstanceOf(CpfInvalidException.class)
                .hasMessage("Invalid CPF: repetitive numbers");
    }

    @Test
    void shouldThrowExceptionForInvalidCpfDigits() {
        VotoDomain voto = VotoDomain.builder()
                .cpf("529.982.247-24") // CPF com dígito verificador incorreto
                .build();

        assertThatThrownBy(voto::validateCPF)
                .isInstanceOf(CpfInvalidException.class)
                .hasMessage("Invalid CPF: incorrect verification digits");
    }
}
