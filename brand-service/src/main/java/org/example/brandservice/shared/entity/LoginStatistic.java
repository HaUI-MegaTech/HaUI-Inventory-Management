package org.example.brandservice.shared.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "login_statistics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginStatistic {
    @Id
    @Temporal(TemporalType.DATE)
    private Date    date;
    private Integer loggedIn;
    private Date    lastUpdated;
}
