package br.com.gubee.interview.core.application.hero.commands;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.application.helpers.HeroTestDataBuilder;
import br.com.gubee.interview.core.application.testdoubles.HeroRepositoryStub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeleteHeroCommandTest {

    private HeroRepositoryStub heroRepositoryStub;
    private DeleteHeroCommand deleteHeroCommand;

    @BeforeEach
    void setUp() {
        heroRepositoryStub = new HeroRepositoryStub();
        deleteHeroCommand = new DeleteHeroCommand(heroRepositoryStub);
    }

    @Test
    void shouldDeleteHeroSuccessfully() {
        Hero hero = new HeroTestDataBuilder()
                .withName("Wonder Woman")
                .build();

        UUID heroId = heroRepositoryStub.create(hero);

        deleteHeroCommand.execute(heroId);

        assertFalse(heroRepositoryStub.findById(heroId).isPresent(), "Expected hero to be deleted from the repository");
    }

    @Test
    void shouldNotThrowExceptionWhenHeroDoesNotExist() {
        UUID nonExistentHeroId = UUID.randomUUID();

        assertDoesNotThrow(() -> deleteHeroCommand.execute(nonExistentHeroId),
                "Expected no exception when deleting a non-existent hero");
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deleteHeroCommand.execute(null);
        });

        assertEquals("Hero ID cannot be null", exception.getMessage(),
                "Expected exception message to indicate null ID");
    }
}