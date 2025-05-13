package br.com.gubee.interview.core.application.hero.queries;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.application.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.HeroRepositoryStub;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindHeroByNameQueryTest {

    @Test
    void shouldFindHeroesByNameSuccessfully() {
        HeroRepositoryStub heroRepositoryStub = new HeroRepositoryStub();
        FindHeroByNameQuery findHeroByNameQuery = new FindHeroByNameQuery(heroRepositoryStub);

        Hero hero1 = new HeroTestDataBuilder()
                .withName("Superman")
                .build();

        Hero hero2 = new HeroTestDataBuilder()
                .withName("Supergirl")
                .build();

        heroRepositoryStub.create(hero1);
        heroRepositoryStub.create(hero2);

        List<Hero> result = findHeroByNameQuery.execute("Super");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(hero -> hero.getName().equals("Superman")));
        assertTrue(result.stream().anyMatch(hero -> hero.getName().equals("Supergirl")));
    }

    @Test
    void shouldReturnEmptyListWhenNoHeroesFound() {
        HeroRepositoryStub heroRepositoryStub = new HeroRepositoryStub();
        FindHeroByNameQuery findHeroByNameQuery = new FindHeroByNameQuery(heroRepositoryStub);

        List<Hero> result = findHeroByNameQuery.execute("Batman");

        assertTrue(result.isEmpty());
    }
}