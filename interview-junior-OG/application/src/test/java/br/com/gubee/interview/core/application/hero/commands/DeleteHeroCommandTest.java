package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.application.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.HeroRepositoryStub;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DeleteHeroCommandTest {

    @Test
    void shouldDeleteHeroSuccessfully() {
        HeroRepositoryStub heroRepositoryStub = new HeroRepositoryStub();
        DeleteHeroCommand deleteHeroCommand = new DeleteHeroCommand(heroRepositoryStub);

        Hero hero = new HeroTestDataBuilder()
                .withName("Wonder Woman")
                .build();

        UUID heroId = heroRepositoryStub.create(hero);
        deleteHeroCommand.execute(heroId);

        assertFalse(heroRepositoryStub.findById(heroId).isPresent());
    }
}