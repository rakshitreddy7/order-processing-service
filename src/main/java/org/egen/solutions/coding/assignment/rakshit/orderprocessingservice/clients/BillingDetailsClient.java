package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.clients;

import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingDetailsClient extends JpaRepository<BillingDetails, Long> {

}
