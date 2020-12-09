package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.clients;

import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderDetailsClient extends JpaRepository<OrderDetails, UUID> {

    Optional<List<OrderDetails>> findByCustomerIdOrderByCreatedDateDesc(Long customerId);
}
