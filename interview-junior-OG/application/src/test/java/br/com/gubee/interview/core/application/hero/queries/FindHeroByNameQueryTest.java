package br.com.gubee.interview.core.application.hero.queries;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.application.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.HeroRepositoryStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FindHeroByNameQueryTest {

    private HeroRepositoryStub heroRepositoryStub;
    private FindHeroByNameQuery findHeroByNameQuery;

    @BeforeEach
    void setUp() {
        heroRepositoryStub = new HeroRepositoryStub();
        findHeroByNameQuery = new FindHeroByNameQuery(heroRepositoryStub);
    }

    @Test
    void shouldReturnHeroesWhenNameMatches() {
        Hero hero1 = new HeroTestDataBuilder()
                .withName("Superman")
                .build();

        Hero hero2 = new HeroTestDataBuilder()
                .withName("Supergirl")
                .build();

        heroRepositoryStub.create(hero1);
        heroRepositoryStub.create(hero2);

        List<Hero> result = findHeroByNameQuery.execute("Super");

        assertEquals(2, result.size(), "Expected 2 heroes to be found");
        assertTrue(result.stream().anyMatch(hero -> hero.getName().equals("Superman")), "Expected to find Superman");
        assertTrue(result.stream().anyMatch(hero -> hero.getName().equals("Supergirl")), "Expected to find Supergirl");
    }

    @Test
    void shouldReturnEmptyListWhenNoHeroesMatch() {
        List<Hero> result = findHeroByNameQuery.execute("Batman");

        assertTrue(result.isEmpty(), "Expected no heroes to be found");
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            findHeroByNameQuery.execute(null);
        });

        assertEquals("Name cannot be null or blank", exception.getMessage(),
                "Expected exception message to indicate null name");
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            findHeroByNameQuery.execute(" ");
        });

        assertEquals("Name cannot be null or blank", exception.getMessage(),
                "Expected exception message to indicate blank name");
    }
}