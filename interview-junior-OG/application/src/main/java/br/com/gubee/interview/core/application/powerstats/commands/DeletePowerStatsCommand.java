package br.com.gubee.interview.core.application.powerstats.commands;

import br.com.gubee.interview.core.application.ports.repositories.PowerStatsRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletePowerStatsCommand {
    private final PowerStatsRepository powerStatsRepository;

    @Transactional
    public boolean execute(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("PowerStats ID cannot be null");
        }

        if (powerStatsRepository.findById(id).isEmpty()) {
            return false;
        }

        powerStatsRepository.delete(id);
        return true;
    }
}