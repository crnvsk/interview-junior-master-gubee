package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.application.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.HeroRepositoryStub;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateHeroCommandTest {

    @Test
    void shouldUpdateHeroSuccessfully() {
        HeroRepositoryStub heroRepositoryStub = new HeroRepositoryStub();
        UpdateHeroCommand updateHeroCommand = new UpdateHeroCommand(heroRepositoryStub);

        Hero hero = new HeroTestDataBuilder()
                .withName("Batman")
                .build();

        UUID heroId = heroRepositoryStub.create(hero);

        Hero updatedHero = new HeroTestDataBuilder()
                .withName("Dark Knight")
                .build();

        updateHeroCommand.execute(heroId, updatedHero);

        Hero retrievedHero = heroRepositoryStub.findById(heroId).orElseThrow();
        assertEquals("Dark Knight", retrievedHero.getName());
    }
}