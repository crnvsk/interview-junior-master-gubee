package br.com.gubee.interview.core.adapters.inbound.controllers;

import br.com.gubee.interview.core.application.usecases.FindHeroByNameUseCase;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/heroes")
public class FindHeroByNameController {
    private final FindHeroByNameUseCase findHeroByNameUseCase;

    public FindHeroByNameController(FindHeroByNameUseCase findHeroByNameUseCase) {
        this.findHeroByNameUseCase = findHeroByNameUseCase;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<HeroResponseDTO> findByName(@PathVariable String name) {
        return findHeroByNameUseCase.findHeroByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}