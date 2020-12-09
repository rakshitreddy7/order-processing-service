package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "zip_city")
public class ZipCodeCity {

    private String city;

    @Id
    @Column(name = "zip_code")
    private String zipCode;
}
