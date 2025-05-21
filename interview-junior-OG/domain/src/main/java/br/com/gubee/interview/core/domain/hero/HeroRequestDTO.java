package br.com.gubee.interview.core.domain.hero;

import br.com.gubee.interview.core.domain.enums.Race;
import br.com.gubee.interview.core.domain.powerstats.PowerstatsDTO;
import jakarta.validation.constraints.NotNull;

public record HeroRequestDTO(
		@NotNull(message = "O nome deve ser informado") String name,
		@NotNull(message = "A raça deve ser informada") Race race,
		@NotNull(message = "Os atributos devem ser informados") PowerstatsDTO powerStats,
		Boolean enabled) {
}