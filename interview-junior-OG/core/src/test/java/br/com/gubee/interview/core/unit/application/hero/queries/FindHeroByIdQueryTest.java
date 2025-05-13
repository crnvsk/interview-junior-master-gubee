package br.com.gubee.interview.core.unit.application.hero.queries;

import br.com.gubee.interview.core.application.hero.queries.FindHeroByIdQuery;
import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.unit.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.unit.stubs.HeroRepositoryStub;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FindHeroByIdQueryTest {

    @Test
    void shouldFindHeroByIdSuccessfully() {
        HeroRepositoryStub heroRepositoryStub = new HeroRepositoryStub();
        FindHeroByIdQuery findHeroByIdQuery = new FindHeroByIdQuery(heroRepositoryStub);

        Hero hero = new HeroTestDataBuilder()
                .withName("Superman")
                .build();

        UUID heroId = heroRepositoryStub.create(hero);

        Optional<Hero> result = findHeroByIdQuery.execute(heroId);

        assertTrue(result.isPresent());
        assertEquals("Superman", result.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenHeroNotFound() {
        HeroRepositoryStub heroRepositoryStub = new HeroRepositoryStub();
        FindHeroByIdQuery findHeroByIdQuery = new FindHeroByIdQuery(heroRepositoryStub);

        Optional<Hero> result = findHeroByIdQuery.execute(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }
}