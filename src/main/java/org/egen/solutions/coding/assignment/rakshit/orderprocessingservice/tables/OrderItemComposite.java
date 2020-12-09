package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderItemComposite implements Serializable {
    @Type(type = "uuid-char")
    private UUID orderId;
    private Long itemId;
}
