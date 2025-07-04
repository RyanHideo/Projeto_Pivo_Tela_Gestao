package com.sinapse.pivo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tag_records")
@Getter
@Setter
public class TagRecordEntity {
    @Id
    @Column(name="id")
    private Long id;

    private String name;
    private Double value;

    @Column(name="dateOfRecord")
    private LocalDateTime date;

    @Column(name="engineUnits")
    private String unit;
}