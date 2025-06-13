package au.com.telstra.simcardactivator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.telstra.simcardactivator.entity.SimCard;

public interface SimCardRepository extends JpaRepository<SimCard, Long>{
    
}
