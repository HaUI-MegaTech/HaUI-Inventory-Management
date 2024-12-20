package org.example.authservice.dto.home;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.authservice.shared.constant.DatetimeFormat;

import java.util.Date;

public record LoginStatisticResponseDTO(
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = DatetimeFormat.INDOCHINA_DATETIME_FORMAT,
                timezone = DatetimeFormat.VIETNAM_TIMEZONE
        )
        Date date,
        Integer loggedIn
) {
}
