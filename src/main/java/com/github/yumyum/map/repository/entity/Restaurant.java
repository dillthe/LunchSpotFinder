package com.github.yumyum.map.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {
//        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//        @Column(name= "restaurant_id")
//        private Integer restaurantId;

        @Id @Column(name = "rstr_id")
        private Integer rstrId;

        @Column(name = "rstr_nm")
        private String rstrNm;

        @Column(name = "rstr_rdnmadr")
        private String rstrRdnmadr;

        @Column(name = "rstr_lnno_adres")
        private String rstrLnnoAdres;

        @Column(name = "rstr_la")
        private Double rstrLa;

        @Column(name = "rstr_lo")
        private Double rstrLo;

        @Column(name = "rstr_telno")
        private String rstrTelno;

        @Column(name = "bsns_statm_bzcnd_nm")
        private String bsnsStatmBzcndNm;

        @Column(name = "bsns_lcnc_nm")
        private String bsnsLcncNm;

        @Column(name = "rstr_intrcn_cont")
        private String rstrIntrcnCont;

        @Column(name = "rstr_area_clsf_nm")
        private String rstrAreaClsfNm;
}