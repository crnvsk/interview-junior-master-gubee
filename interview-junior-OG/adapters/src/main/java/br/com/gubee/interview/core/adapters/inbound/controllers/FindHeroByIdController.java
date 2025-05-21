package br.com.gubee.interview.core.adapters.inbound.controllers;

import br.com.gubee.interview.core.application.usecases.FindHeroByIdUseCase;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/heroes")
public class FindHeroByIdController {
    private final FindHeroByIdUseCase findHeroByIdUseCase;

    public FindHeroByIdController(FindHeroByIdUseCase findHeroByIdUseCase) {
        this.findHeroByIdUseCase = findHeroByIdUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeroResponseDTO> findById(@PathVariable UUID id) {
        return findHeroByIdUseCase.findHeroById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}