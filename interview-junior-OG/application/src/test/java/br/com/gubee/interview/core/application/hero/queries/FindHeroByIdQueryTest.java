package br.com.gubee.interview.core.application.hero.queries;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.application.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.HeroRepositoryStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FindHeroByIdQueryTest {

    private HeroRepositoryStub heroRepositoryStub;
    private FindHeroByIdQuery findHeroByIdQuery;

    @BeforeEach
    void setUp() {
        heroRepositoryStub = new HeroRepositoryStub();
        findHeroByIdQuery = new FindHeroByIdQuery(heroRepositoryStub);
    }

    @Test
    void shouldReturnHeroWhenIdExists() {
        Hero hero = new HeroTestDataBuilder()
                .withName("Superman")
                .build();
        UUID heroId = heroRepositoryStub.create(hero);

        Optional<Hero> result = findHeroByIdQuery.execute(heroId);

        assertTrue(result.isPresent(), "Expected hero to be found");
        assertEquals("Superman", result.get().getName(), "Expected hero name to match");
    }

    @Test
    void shouldReturnEmptyWhenHeroDoesNotExist() {
        Optional<Hero> result = findHeroByIdQuery.execute(UUID.randomUUID());

        assertTrue(result.isEmpty(), "Expected no hero to be found");
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            findHeroByIdQuery.execute(null);
        });

        assertEquals("ID cannot be null", exception.getMessage(), "Expected exception message to indicate null ID");
    }
}