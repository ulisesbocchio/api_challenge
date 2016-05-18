package com.disney.studios.repositories;

import com.disney.studios.entities.Breed;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Ulises Bocchio, Sergio.U.Bocchio@Disney.com (BOCCS002)
 */
public interface BreedRepository extends PagingAndSortingRepository<Breed, Long> {
    Breed findByNameIgnoreCase(String name);
}
