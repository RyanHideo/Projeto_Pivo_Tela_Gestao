package com.sinapse.pivo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "tagshistorian")
@Getter
@Setter
public class TagHistorianEntity {

        @Id
        @Column(name="id")      // ajuste se o nome for diferente
        private Long id;

        private String name;
        private String engineUnits;
    }



