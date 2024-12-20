package org.example.authservice.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.example.authservice.shared.constant.DatetimeFormat;
import org.example.authservice.shared.entity.Address;
import org.example.authservice.shared.entity.enums.Gender;
import org.example.authservice.shared.entity.enums.Role;

import java.util.Date;
import java.util.List;

@Builder
public record FullUserResponseDTO(
        Integer id,
        String username,
        String firstName,
        String lastName,
        Gender gender,
        String email,
        String avatarImageUrl,
        String phoneNumber,

        Date lastUpdated,

        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = DatetimeFormat.INDOCHINA_FULL_DATETIME_FORMAT,
                timezone = DatetimeFormat.VIETNAM_TIMEZONE
        )
        Date whenCreated,

        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = DatetimeFormat.INDOCHINA_FULL_DATETIME_FORMAT,
                timezone = DatetimeFormat.VIETNAM_TIMEZONE
        )
        Date lastLoggedIn,
        Integer loggedIn,
        Role role,
        List<Address> addresses
) {
}
