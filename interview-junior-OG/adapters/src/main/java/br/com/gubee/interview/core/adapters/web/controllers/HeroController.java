package br.com.gubee.interview.core.adapters.web.controllers;

import br.com.gubee.interview.core.application.hero.commands.CreateHeroCommand;
import br.com.gubee.interview.core.application.hero.commands.DeleteHeroCommand;
import br.com.gubee.interview.core.application.hero.commands.UpdateHeroCommand;
import br.com.gubee.interview.core.application.hero.commands.CompareHeroesCommand;
import br.com.gubee.interview.core.application.hero.queries.FindHeroByIdQuery;
import br.com.gubee.interview.core.application.hero.queries.FindHeroByNameQuery;
import br.com.gubee.interview.core.adapters.web.dtos.CreateHeroRequest;
import br.com.gubee.interview.core.adapters.web.dtos.HeroResponse;
import br.com.gubee.interview.core.adapters.web.mappers.HeroMapper;
import br.com.gubee.interview.core.domain.Hero;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final CreateHeroCommand createHeroCommand;
    private final UpdateHeroCommand updateHeroCommand;
    private final DeleteHeroCommand deleteHeroCommand;
    private final CompareHeroesCommand compareHeroesCommand;
    private final FindHeroByIdQuery findHeroByIdQuery;
    private final FindHeroByNameQuery findHeroByNameQuery;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createHero(@RequestBody CreateHeroRequest createHeroRequest) {
        var powerStats = HeroMapper.toPowerStats(createHeroRequest);
        var hero = HeroMapper.toHero(createHeroRequest, UUID.randomUUID());
        UUID id = createHeroCommand.execute(hero, powerStats);
        return created(buildHeroUri(id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeroResponse> getHeroById(@PathVariable UUID id) {
        return findHeroByIdQuery.execute(id)
                .map(HeroResponse::fromDomain)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<HeroResponse>> getHeroesByName(@RequestParam String name) {
        var heroes = findHeroByNameQuery.execute(name)
                .stream()
                .map(HeroResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(heroes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHero(@PathVariable UUID id, @RequestBody Hero updatedHero) {
        boolean updated = updateHeroCommand.execute(id, updatedHero);
        return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHero(@PathVariable UUID id) {
        boolean deleted = deleteHeroCommand.execute(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/compare")
    public ResponseEntity<?> compareHeroes(@RequestParam UUID hero1Id, @RequestParam UUID hero2Id) {
        var comparisonResult = compareHeroesCommand.execute(hero1Id, hero2Id);
        return comparisonResult != null ? ResponseEntity.ok(comparisonResult) : ResponseEntity.notFound().build();
    }

    private URI buildHeroUri(UUID id) {
        return URI.create(format("/api/v1/heroes/%s", id));
    }
}