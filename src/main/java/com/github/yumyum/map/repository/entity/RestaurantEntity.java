package com.github.yumyum.map.repository.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Id
//    @Column(name = "rstr_id")
//    private Integer rstrId;
//
//    @Column(name = "rstr_nm")
//    private String rstrNm;
//
//    @Column(name = "rstr_rdnmadr")
//    private String rstrRdnmadr;
//
//    @Column(name = "rstr_lnno_adres")
//    private String rstrLnnoAdres;
//
//    //@Column(name = "rstr_la", precision = 10, scale = 8)
//    @Column(name = "rstr_la")
//    private String rstrLa;
//
////    @Column(name = "rstr_lo", precision = 11, scale = 8)
//    @Column(name = "rstr_lo")
//    private String rstrLo;
//
//    @Column(name = "rstr_telno")
//    private String rstrTelno;
//
//    @Column(name = "bsns_statm_bzcnd_nm")
//    private String bsnsStatmBzcndNm;
//
//    @Column(name = "bsns_lcnc_nm")
//    private String bsnsLcncNm;
//
//    @Column(name = "rstr_intrcn_cont")
//    private String rstrIntrcnCont;
//
//    @Column(name = "rstr_area_clsf_nm")
//    private String rstrAreaClsfNm;
//}
////////////////////////////////////
    @Id
    @JsonProperty("RSTR_ID")
    @Column(name = "RSTR_ID")
    private Long rstrId;

    @JsonProperty("RSTR_NM")
    @Column(name = "RSTR_NM")
    private String rstrNm;


    @JsonProperty("RSTR_RDNMADR")
    @Column(name = "RSTR_RDNMADR")
    private String rstrRdnmadr;

    @JsonProperty("RSTR_LNNO_ADRES")
    @Column(name = "RSTR_LNNO_ADRES")
    private String rstrLnnoAdres;

    @JsonProperty("RSTR_LA")
    @Column(name = "RSTR_LA")
    private BigDecimal rstrLa;

    @JsonProperty("RSTR_LO")
    @Column(name = "RSTR_LO")
    private BigDecimal rstrLo;

    @JsonProperty("RSTR_TELNO")
    @Column(name = "RSTR_TELNO")
    private String rstrTelno;

    @JsonProperty("BSNS_STATM_BZCND_NM")
    @Column(name = "BSNS_STATM_BZCND_NM")
    private String bsnsStatmBzcndNm;

    @JsonProperty("BSNS_LCNC_NM")
    @Column(name = "BSNS_LCNC_NM")
    private String bsnsLcncNm;

    @JsonProperty("RSTR_INTRCN_CONT")
    @Column(name = "RSTR_INTRCN_CONT")
    private String rstrIntrcnCont;

    @JsonProperty("RSTR_AREA_CLSF_NM")
    @Column(name = "RSTR_AREA_CLSF_NM")
    private String rstrAreaClsfNm;
}

