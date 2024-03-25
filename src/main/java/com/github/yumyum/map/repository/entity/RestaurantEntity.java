package com.github.yumyum.map.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @JsonProperty("RSTR_ID")
    @Column(name = "rstr_id")
    private Integer rstrId;

    @JsonProperty("RSTR_NM")
    @Column(name = "rstr_name")
    private String rstrName;

    @JsonProperty("RSTR_RDNMADR")
    @Column(name = "address")
    private String address;

    @JsonProperty("RSTR_LA")
    @Column(name = "latitude")
    private BigDecimal rsLatitude;

    @JsonProperty("RSTR_LO")
    @Column(name = "longitude")
    private BigDecimal rsLongitude;

    @JsonProperty("RSTR_TELNO")
    @Column(name = "tell_number")
    private String tellNumber;

    @JsonProperty("BSNS_STATM_BZCND_NM")
    @Column(name = "cuisine")
    private String cuisine;
}

