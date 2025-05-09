package br.com.gubee.interview.core.web.controllers;

import br.com.gubee.interview.core.application.hero.commands.CreateHeroCommand;
import br.com.gubee.interview.core.application.hero.commands.DeleteHeroCommand;
import br.com.gubee.interview.core.application.hero.commands.UpdateHeroCommand;
import br.com.gubee.interview.core.application.hero.commands.CompareHeroesCommand;
import br.com.gubee.interview.core.application.hero.queries.FindHeroByIdQuery;
import br.com.gubee.interview.core.application.hero.queries.FindHeroByNameQuery;
import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.web.dtos.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final CreateHeroCommand createHeroCommand;
    private final FindHeroByIdQuery findHeroByIdQuery;
    private final FindHeroByNameQuery findHeroByNameQuery;
    private final UpdateHeroCommand updateHeroCommand;
    private final DeleteHeroCommand deleteHeroCommand;
    private final CompareHeroesCommand compareHeroesCommand;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = createHeroCommand.execute(createHeroRequest.toHero(UUID.randomUUID()), createHeroRequest.toPowerStats());
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hero> findById(@PathVariable UUID id) {
        return findHeroByIdQuery.execute(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> findByName(@RequestParam String name) {
        var heroes = findHeroByNameQuery.execute(name);
        return ResponseEntity.ok(heroes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHero(@PathVariable UUID id, @Validated @RequestBody Hero updatedHero) {
        boolean updated = updateHeroCommand.execute(id, updatedHero);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHero(@PathVariable UUID id) {
        boolean deleted = deleteHeroCommand.execute(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/compare")
    public ResponseEntity<?> compareHeroes(@RequestParam UUID hero1Id, @RequestParam UUID hero2Id) {
        var comparisonResult = compareHeroesCommand.execute(hero1Id, hero2Id);
        if (comparisonResult == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comparisonResult);
    }
}