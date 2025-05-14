package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.application.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.HeroRepositoryStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UpdateHeroCommandTest {

    private HeroRepositoryStub heroRepositoryStub;
    private UpdateHeroCommand updateHeroCommand;

    @BeforeEach
    void setUp() {
        heroRepositoryStub = new HeroRepositoryStub();
        updateHeroCommand = new UpdateHeroCommand(heroRepositoryStub);
    }

    @Test
    void shouldUpdateHeroSuccessfully() {
        Hero hero = new HeroTestDataBuilder()
                .withName("Batman")
                .build();

        UUID heroId = heroRepositoryStub.create(hero);

        Hero updatedHero = new HeroTestDataBuilder()
                .withName("Dark Knight")
                .build();

        boolean result = updateHeroCommand.execute(heroId, updatedHero);

        assertTrue(result, "Expected hero to be updated successfully");
        Hero retrievedHero = heroRepositoryStub.findById(heroId).orElseThrow();
        assertEquals("Dark Knight", retrievedHero.getName(), "Expected hero name to be updated to 'Dark Knight'");
    }

    @Test
    void shouldReturnFalseWhenHeroDoesNotExist() {
        UUID nonExistentHeroId = UUID.randomUUID();

        Hero updatedHero = new HeroTestDataBuilder()
                .withName("Dark Knight")
                .build();

        boolean result = updateHeroCommand.execute(nonExistentHeroId, updatedHero);

        assertFalse(result, "Expected update to return false for non-existent hero");
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        Hero updatedHero = new HeroTestDataBuilder()
                .withName("Dark Knight")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updateHeroCommand.execute(null, updatedHero);
        });

        assertEquals("Hero ID cannot be null", exception.getMessage(),
                "Expected exception message to indicate null ID");
    }

    @Test
    void shouldThrowExceptionWhenUpdatedHeroIsNull() {
        Hero hero = new HeroTestDataBuilder()
                .withName("Batman")
                .build();

        UUID heroId = heroRepositoryStub.create(hero);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updateHeroCommand.execute(heroId, null);
        });

        assertEquals("Updated hero cannot be null", exception.getMessage(),
                "Expected exception message to indicate null updated hero");
    }
}