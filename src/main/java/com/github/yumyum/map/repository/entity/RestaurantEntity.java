package com.github.yumyum.map.repository.entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "RSTR_ID")
    private Integer rstrId;

    @Column(name = "RSTR_NM")
    private String rstrNm;

    @Column(name = "RSTR_RDNMADR")
    private String rstrRdnmadr;

    @Column(name = "RSTR_LNNO_ADRES")
    private String rstrLnnoAdres;

    @Column(name = "RSTR_LA")
    private Double rstrLa;

    @Column(name = "RSTR_LO")
    private Double rstrLo;

    @Column(name = "RSTR_TELNO")
    private String rstrTelno;

    @Column(name = "BSNS_STATM_BZCND_NM")
    private String bsnsStatmBzcndNm;

    @Column(name = "BSNS_LCNC_NM")
    private String bsnsLcncNm;

    @Column(name = "RSTR_INTRCN_CONT")
    private String rstrIntrcnCont;

    @Column(name = "RSTR_AREA_CLSF_NM")
    private String rstrAreaClsfNm;
}


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
//    @Column(name = "rstr_la")
//    private Double rstrLa;
//
//    @Column(name = "rstr_lo")
//    private Double rstrLo;
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