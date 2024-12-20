package org.example.authservice.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.home.LoginStatisticResponseDTO;
import org.example.authservice.mapper.LoginStatisticMapper;
import org.example.authservice.repository.LoginStatisticRepository;
import org.example.authservice.shared.entity.LoginStatistic;
import org.example.authservice.shared.global.GlobalResponseDTO;
import org.example.authservice.shared.global.NoPaginatedMeta;
import org.example.authservice.shared.global.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginStatisticServiceImpl implements LoginStatisticService {
    public static Integer counter;
    public static Boolean modified;

    private final LoginStatisticRepository loginStatisticRepository;

    @PostConstruct
    public void postConstruct() {
        modified = false;
        counter = 0;
    }

    @Override
    @PreDestroy
    public void saveOrUpdate() {
        if (!modified) return;

        Optional<LoginStatistic> found = loginStatisticRepository.findById(new Date());
        found.ifPresentOrElse(
                (item) -> {
                    item.setLoggedIn(item.getLoggedIn() + counter);
                    item.setLastUpdated(new Date(Instant.now().toEpochMilli()));
                    loginStatisticRepository.save(item);
                },
                () -> {
                    loginStatisticRepository.save(
                            LoginStatistic.builder()
                                          .date(new Date())
                                          .loggedIn(counter)
                                          .lastUpdated(new Date(Instant.now().toEpochMilli()))
                                          .build()
                    );
                }
        );
        postConstruct();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<LoginStatisticResponseDTO>> getListByDay() {
        Sort sort = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(0, 7, sort);
        Page<LoginStatistic> page = loginStatisticRepository.findAll(pageable);
        List<LoginStatistic> list = page.getContent();

        return GlobalResponseDTO
                .<NoPaginatedMeta, List<LoginStatisticResponseDTO>>builder()
                .meta(NoPaginatedMeta.builder()
                                     .status(Status.SUCCESS)
                                     .build())
                .data(list.stream()
                          .map(LoginStatisticMapper.INSTANCE::toLoginStatisticResponseDTO)
                          .collect(Collectors.toList())
                          .reversed()
                )
                .build();
    }


}
