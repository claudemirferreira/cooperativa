package com.siscred.cooperativa.infrastructure.controller;

import com.siscred.cooperativa.application.usecases.CreateVotoUsecase;
import com.siscred.cooperativa.domain.VotoDomain;
import com.siscred.cooperativa.exception.CpfInvalidException;
import com.siscred.cooperativa.infrastructure.controller.dto.request.CreateVotoRequest;
import com.siscred.cooperativa.infrastructure.controller.dto.response.CreateVotoResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/voto")
public class VotoController {

    private final CreateVotoUsecase createVotoUsecase;

    public VotoController(CreateVotoUsecase createVotoUsecase) {
        this.createVotoUsecase = createVotoUsecase;
    }

    @Operation(summary = "Endpoint para registrar o voto", description = """
            <h3>Retorna o voto criado.</h3>
            """)
    @PostMapping
    public ResponseEntity<CreateVotoResponse> create(@Valid @RequestBody final CreateVotoRequest request) throws CpfInvalidException {
        final var domain = createVotoUsecase.execute(request.getCpf(), request.getSessaoId(), request.getVoto());
        return ResponseEntity.created(URI.create("/voto/" + domain.getCpf())).body(
                CreateVotoResponse
                        .builder()
                        .cpf(domain.getCpf())
                        .voto(domain.getVoto())
                        .sessaoId(domain.getSessao().getId())
                        .build()
        );
    }

}
