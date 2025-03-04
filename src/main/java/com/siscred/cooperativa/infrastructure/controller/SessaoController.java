package com.siscred.cooperativa.infrastructure.controller;

import com.siscred.cooperativa.application.usecases.CreateSessaoUsecase;
import com.siscred.cooperativa.domain.SessaoDomain;
import com.siscred.cooperativa.infrastructure.controller.dto.response.CreateSessaoVotacaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sessao/v1")
public class SessaoController {

    private final CreateSessaoUsecase createSessaoUsecase;

    public SessaoController(CreateSessaoUsecase createSessaoUsecase) {
        this.createSessaoUsecase = createSessaoUsecase;
    }

    @Operation(summary = "Cria uma nova Sessão de Votação", description = """
            <h3>Retorna a Sessão de Votação criada.</h3>
            """)
    @PostMapping("/pauta/{pautaId}")
    public ResponseEntity<CreateSessaoVotacaoResponse> create(
            @PathVariable Long pautaId,
            @RequestParam(required = false, defaultValue = "1") Integer tempoExpiracaoEmMinutos) { // Definindo 1 minuto como padrão
        SessaoDomain sessaoDomain = createSessaoUsecase.execute(pautaId, tempoExpiracaoEmMinutos);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CreateSessaoVotacaoResponse(
                        sessaoDomain.getId(),
                        sessaoDomain.getInicio(),
                        sessaoDomain.getFim(),
                        sessaoDomain.getPauta(),
                        sessaoDomain.getStatus()));
    }
}
