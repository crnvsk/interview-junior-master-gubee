package br.com.gubee.interview.core.application.hero.queries;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.application.ports.repositories.HeroRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindHeroByNameQuery {
    private final HeroRepository heroRepository;

    @Transactional
    public List<Hero> execute(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        return heroRepository.findByName(name);
    }
}