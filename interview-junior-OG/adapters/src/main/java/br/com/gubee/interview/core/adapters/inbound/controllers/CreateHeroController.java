package br.com.gubee.interview.core.adapters.inbound.controllers;

import br.com.gubee.interview.core.application.usecases.CreateHeroUseCase;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/heroes")
public class CreateHeroController {
    private final CreateHeroUseCase createHeroUseCase;

    public CreateHeroController(CreateHeroUseCase createHeroUseCase) {
        this.createHeroUseCase = createHeroUseCase;
    }

    @PostMapping
    public ResponseEntity<HeroResponseDTO> create(@RequestBody @Valid HeroRequestDTO dto) {
        return ResponseEntity.ok(createHeroUseCase.createHero(dto));
    }
}