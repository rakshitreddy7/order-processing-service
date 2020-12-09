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
@Table(name = "item_details")
public class ItemDetails {
    @Id
    private Long id;

    private String name;
}
