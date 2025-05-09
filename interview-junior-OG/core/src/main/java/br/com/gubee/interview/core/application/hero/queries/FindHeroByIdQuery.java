package br.com.gubee.interview.core.application.hero.queries;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.adapters.persistence.HeroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindHeroByIdQuery {
    private final HeroRepository heroRepository;

    public Optional<Hero> execute(UUID id) {
        return heroRepository.findById(id);
    }
}