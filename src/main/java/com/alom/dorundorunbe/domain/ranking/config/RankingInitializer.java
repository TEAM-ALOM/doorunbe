package com.alom.dorundorunbe.domain.ranking.config;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.ranking.repository.RankingRepository;
import com.alom.dorundorunbe.global.enums.Tier;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RankingInitializer implements ApplicationRunner {
    private final RankingRepository rankingRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Arrays.stream(Tier.values()).forEach(tier -> {
            if (!rankingRepository.existsByTier(tier)) {
                rankingRepository.save(Ranking.create(tier));
            }
        });
    }
}
