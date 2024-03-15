package com.github.yumyum.map.repository.entity;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rstr")
public class RstrEntity {
    @Id
    @JsonProperty("RSTR_ID")
    @Column(name = "rstr_id")
    private Long rstrId;

    @JsonProperty("RSTR_NM")
    @Column(name = "rstr_name")
    private String rstrName;

    @JsonProperty("RSTR_RDNMADR")
    @Column(name = "address")
    private String address;

    @JsonProperty("RSTR_LA")
    @Column(name = "latitude")
    private String latitude;

    @JsonProperty("RSTR_LO")
    @Column(name = "longtitude")
    private BigDecimal longtitude;

    @JsonProperty("RSTR_TELNO")
    @Column(name = "tell_number")
    private String tell_number;

    @JsonProperty("BSNS_STATM_BZCND_NM")
    @Column(name = "cuisine")
    private String cuisine;
}

