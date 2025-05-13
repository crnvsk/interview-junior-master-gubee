package br.com.gubee.interview.core.application.hero.queries;

import br.com.gubee.interview.core.domain.Hero;
import br.com.gubee.interview.core.application.ports.repositories.HeroRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindHeroByNameQuery {
    private final HeroRepository heroRepository;

    public List<Hero> execute(String name) {
        return heroRepository.findByName(name);
    }
}