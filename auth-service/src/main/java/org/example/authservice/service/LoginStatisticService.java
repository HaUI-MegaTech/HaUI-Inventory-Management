package org.example.authservice.service;

import org.example.authservice.dto.home.LoginStatisticResponseDTO;
import org.example.authservice.shared.global.GlobalResponseDTO;
import org.example.authservice.shared.global.NoPaginatedMeta;

import java.util.List;

public interface LoginStatisticService {
    void saveOrUpdate();

    GlobalResponseDTO<NoPaginatedMeta, List<LoginStatisticResponseDTO>> getListByDay();
}
