package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.clients;

import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.CityState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityStateDetailsClient extends JpaRepository<CityState, String> {

}
