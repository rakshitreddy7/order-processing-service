package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.clients;

import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.ItemQuantity;
import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.OrderItemComposite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemQuantityClient extends JpaRepository<ItemQuantity, OrderItemComposite> {
    Optional<List<ItemQuantity>> findByOrderItemCompositeKey_OrderId(UUID orderId);
}
