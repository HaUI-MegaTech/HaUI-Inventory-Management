package org.example.brandservice.shared.entity.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.brandservice.shared.entity.location.District;
import org.example.brandservice.shared.entity.location.Province;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "administrative_units")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdministrativeUnit {
    @Id
    private Integer id;

    private String fullName;
    private String fullNameEn;
    private String shortName;
    private String shortNameEn;
    private String codeName;
    private String codeNameEn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "administrativeUnit")
    private List<Province> provinces;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "administrativeUnit")
    private List<District> districts;
}
