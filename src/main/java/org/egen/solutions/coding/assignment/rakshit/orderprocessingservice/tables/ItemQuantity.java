package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "item_quantity")
public class ItemQuantity implements Serializable {

    @EmbeddedId
    private OrderItemComposite orderItemCompositeKey;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_Id")
    private OrderDetails orderDetails;

    private Integer quantity;
}
