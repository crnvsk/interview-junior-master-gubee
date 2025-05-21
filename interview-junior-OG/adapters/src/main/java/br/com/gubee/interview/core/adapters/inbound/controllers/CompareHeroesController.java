package br.com.gubee.interview.core.adapters.inbound.controllers;

import br.com.gubee.interview.core.application.usecases.CompareHeroesUseCase;
import br.com.gubee.interview.core.domain.hero.HeroComparisonResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/heroes")
public class CompareHeroesController {
    private final CompareHeroesUseCase compareHeroesUseCase;

    public CompareHeroesController(CompareHeroesUseCase compareHeroesUseCase) {
        this.compareHeroesUseCase = compareHeroesUseCase;
    }

    @GetMapping("/compare")
    public ResponseEntity<HeroComparisonResponseDTO> compareHeroes(
            @RequestParam UUID hero1,
            @RequestParam UUID hero2) {
        HeroComparisonResponseDTO response = compareHeroesUseCase.compare(hero1, hero2);
        return ResponseEntity.ok(response);
    }
}