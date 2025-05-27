package br.com.gubee.interview.core.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.gubee.interview.core.application.usecases.CompareHeroesUseCase;
import br.com.gubee.interview.core.domain.hero.HeroComparisonResponseDTO;
import br.com.gubee.interview.core.domain.hero.HeroQueryPort;
import br.com.gubee.interview.core.domain.powerstats.Powerstats;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsComparisonResult;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsQueryPort;

@Service
public class CompareHeroesService implements CompareHeroesUseCase {
	private final HeroQueryPort heroQueryPort;
	private final PowerstatsQueryPort powerstatsQueryPort;

	public CompareHeroesService(HeroQueryPort heroQueryPort, PowerstatsQueryPort powerstatsQueryPort) {
		this.heroQueryPort = heroQueryPort;
		this.powerstatsQueryPort = powerstatsQueryPort;
	}

	@Override
	public HeroComparisonResponseDTO compare(UUID hero1Id, UUID hero2Id) {
		var hero1 = heroQueryPort.findById(hero1Id)
				.orElseThrow(() -> new RuntimeException("Hero 1 not found"));
		var hero2 = heroQueryPort.findById(hero2Id)
				.orElseThrow(() -> new RuntimeException("Hero 2 not found"));

		Powerstats stats1 = powerstatsQueryPort.findById(hero1.getPowerStatsId())
				.orElseThrow(() -> new RuntimeException("Powerstats for Hero 1 not found"));
		Powerstats stats2 = powerstatsQueryPort.findById(hero2.getPowerStatsId())
				.orElseThrow(() -> new RuntimeException("Powerstats for Hero 2 not found"));

		PowerstatsComparisonResult result = stats1.compareWith(stats2);

		return new HeroComparisonResponseDTO(
				hero1.getId(),
				hero2.getId(),
				result.strengthDifference(),
				result.agilityDifference(),
				result.dexterityDifference(),
				result.intelligenceDifference());
	}
}