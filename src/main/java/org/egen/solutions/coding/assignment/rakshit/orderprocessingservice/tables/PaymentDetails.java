package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.models.PaymentMethod;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "payment_details")
public class PaymentDetails {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID confirmationNumber;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderDetails orderDetails;

    private Double amount;
    private LocalDate date;
}
