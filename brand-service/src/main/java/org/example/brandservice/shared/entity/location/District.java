package org.example.brandservice.shared.entity.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.brandservice.shared.entity.location.AdministrativeUnit;
import org.example.brandservice.shared.entity.location.Province;
import org.example.brandservice.shared.entity.location.Ward;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "districts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class District {
    @Id
    private String code;

    private String name;
    private String nameEn;
    private String fullName;
    private String fullNameEn;
    private String codeName;

    @ManyToOne
    @JoinColumn(name = "province_code")
    @JsonIgnore
    private Province province;

    @ManyToOne
    @JoinColumn(name = "administrative_unit_id")
    @JsonIgnore
    private AdministrativeUnit administrativeUnit;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "district")
    @JsonIgnore
    private List<Ward> wards;
}
