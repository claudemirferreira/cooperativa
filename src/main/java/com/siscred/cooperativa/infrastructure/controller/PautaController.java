package com.siscred.cooperativa.infrastructure.controller;

import com.siscred.cooperativa.application.usecases.CreatePautaUsecase;
import com.siscred.cooperativa.domain.PautaDomain;
import com.siscred.cooperativa.infrastructure.controller.dto.request.PautaRequest;
import com.siscred.cooperativa.infrastructure.controller.dto.response.PautaResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "/pauta")
public class PautaController {

    private final CreatePautaUsecase createPautaUsecase;

    public PautaController(CreatePautaUsecase createPautaUsecase) {
        this.createPautaUsecase = createPautaUsecase;
    }

    @Operation(summary = "Endpoint para criar uma nova pauta", description = """
            <h3>Retorna a pauta criada.</h3>
            """)
    @PostMapping
    public ResponseEntity<PautaResponse> create(@Valid @RequestBody final PautaRequest request) {
        final var newPauta = createPautaUsecase.execute(PautaDomain.builder().nome(request.getNome()).build());
        return ResponseEntity.created(URI.create("/pauta/" + newPauta.getId())).body(
                PautaResponse.builder().id(newPauta.getId()).nome(newPauta.getNome()).build());
    }

}
