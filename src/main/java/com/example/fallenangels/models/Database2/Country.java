package com.example.fallenangels.models.Database2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EnableJpaAuditing
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue
    Integer id;

    @Column(name = "name")
    String name;

}
