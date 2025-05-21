package br.com.gubee.interview.core.adapters.inbound.controllers;

import br.com.gubee.interview.core.application.usecases.DeleteHeroUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/heroes")
public class DeleteHeroController {
    private final DeleteHeroUseCase deleteHeroUseCase;

    public DeleteHeroController(DeleteHeroUseCase deleteHeroUseCase) {
        this.deleteHeroUseCase = deleteHeroUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteHeroUseCase.deleteHero(id);
        return ResponseEntity.noContent().build();
    }
}