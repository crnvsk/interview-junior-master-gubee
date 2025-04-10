package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final HeroService heroService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = heroService.create(createHeroRequest);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hero> findById(@PathVariable UUID id) {
        return heroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> findByName(@RequestParam String name) {
        var heroes = heroService.findByName(name);
        if (heroes.isEmpty()) {
            return ResponseEntity.ok().body(heroes);
        }
        return ResponseEntity.ok(heroes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHero(@PathVariable UUID id, @Validated @RequestBody Hero updatedHero) {
        boolean updated = heroService.updateHero(id, updatedHero);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHero(@PathVariable UUID id) {
        boolean deleted = heroService.deleteHero(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/compare")
    public ResponseEntity<?> compareHeroes(@RequestParam UUID hero1Id, @RequestParam UUID hero2Id) {
        var comparisonResult = heroService.compareHeroes(hero1Id, hero2Id);
        if (comparisonResult == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comparisonResult);
    }
}
