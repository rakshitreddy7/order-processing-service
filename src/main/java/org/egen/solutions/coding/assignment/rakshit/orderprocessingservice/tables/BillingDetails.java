package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "billing_details")
public class BillingDetails {
    @Id
    private Long customerId;

    private String address;
    private String zip;
}
