package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.clients;

import org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.tables.ZipCodeCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipCodeCityClient extends JpaRepository<ZipCodeCity, Integer> {

}
