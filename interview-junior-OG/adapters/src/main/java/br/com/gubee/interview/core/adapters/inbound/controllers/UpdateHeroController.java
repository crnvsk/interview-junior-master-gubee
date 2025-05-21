package br.com.gubee.interview.core.adapters.inbound.controllers;

import br.com.gubee.interview.core.application.usecases.UpdateHeroUseCase;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/heroes")
public class UpdateHeroController {
    private final UpdateHeroUseCase updateHeroUseCase;

    public UpdateHeroController(UpdateHeroUseCase updateHeroUseCase) {
        this.updateHeroUseCase = updateHeroUseCase;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid HeroRequestDTO dto) {
        updateHeroUseCase.updateHero(id, dto);
        return ResponseEntity.noContent().build();
    }
}