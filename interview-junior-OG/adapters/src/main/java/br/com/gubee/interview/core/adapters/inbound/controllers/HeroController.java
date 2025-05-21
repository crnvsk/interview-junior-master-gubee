package br.com.gubee.interview.core.adapters.inbound.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gubee.interview.core.application.usecases.CompareHeroesUseCase;
import br.com.gubee.interview.core.application.usecases.CreateHeroUseCase;
import br.com.gubee.interview.core.application.usecases.DeleteHeroUseCase;
import br.com.gubee.interview.core.application.usecases.FindHeroByIdUseCase;
import br.com.gubee.interview.core.application.usecases.FindHeroByNameUseCase;
import br.com.gubee.interview.core.application.usecases.UpdateHeroUseCase;
import br.com.gubee.interview.core.domain.hero.HeroComparisonResponseDTO;
import br.com.gubee.interview.core.domain.hero.HeroRequestDTO;
import br.com.gubee.interview.core.domain.hero.HeroResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/heroes")
public class HeroController {
    private final CreateHeroUseCase createHeroUseCase;
    private final FindHeroByIdUseCase findHeroByIdUseCase;
    private final FindHeroByNameUseCase findHeroByNameUseCase;
    private final UpdateHeroUseCase updateHeroUseCase;
    private final DeleteHeroUseCase deleteHeroUseCase;
    private final CompareHeroesUseCase compareHeroesUseCase;

    public HeroController(
            CreateHeroUseCase createHeroUseCase,
            FindHeroByIdUseCase findHeroByIdUseCase,
            FindHeroByNameUseCase findHeroByNameUseCase,
            UpdateHeroUseCase updateHeroUseCase,
            DeleteHeroUseCase deleteHeroUseCase,
            CompareHeroesUseCase compareHeroesUseCase) {
        this.createHeroUseCase = createHeroUseCase;
        this.findHeroByIdUseCase = findHeroByIdUseCase;
        this.findHeroByNameUseCase = findHeroByNameUseCase;
        this.updateHeroUseCase = updateHeroUseCase;
        this.deleteHeroUseCase = deleteHeroUseCase;
        this.compareHeroesUseCase = compareHeroesUseCase;
    }

    @PostMapping
    public ResponseEntity<HeroResponseDTO> create(@RequestBody @Valid HeroRequestDTO dto) {
        return ResponseEntity.ok(createHeroUseCase.createHero(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeroResponseDTO> findById(@PathVariable UUID id) {
        return findHeroByIdUseCase.findHeroById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<HeroResponseDTO> findByName(@PathVariable String name) {
        return findHeroByNameUseCase.findHeroByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid HeroRequestDTO dto) {
        updateHeroUseCase.updateHero(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteHeroUseCase.deleteHero(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/compare")
    public ResponseEntity<HeroComparisonResponseDTO> compareHeroes(
            @RequestParam UUID hero1,
            @RequestParam UUID hero2) {
        HeroComparisonResponseDTO response = compareHeroesUseCase.compare(hero1, hero2);
        return ResponseEntity.ok(response);
    }
}
