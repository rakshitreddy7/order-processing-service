package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.constants.OrderStatus;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.constants.ShippingMethod;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;

    @OneToMany(mappedBy = "orderDetails" , cascade= CascadeType.ALL)
    private List<PaymentDetails> payments = new ArrayList<>();

    @OneToMany(mappedBy = "orderDetails" , cascade= CascadeType.ALL)
    private List<ItemQuantity> items = new ArrayList<>();

    private Long customerId;
    private Double subTotal;
    private Double tax;
    private Double total;
    private String shippingAddress;
    private String shippingZipCode;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public void addPayment(PaymentDetails paymentDetails) {
        if (payments == null) {
            payments = new ArrayList<>();
        }
        payments.add(paymentDetails);
        paymentDetails.setOrderDetails(this);
    }

    public void addItem(ItemQuantity itemQuantity) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(itemQuantity);
        itemQuantity.setOrderDetails(this);
    }
}


