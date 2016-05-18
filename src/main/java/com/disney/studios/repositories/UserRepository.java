package com.disney.studios.repositories;

import com.disney.studios.entities.User;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ulises Bocchio, Sergio.U.Bocchio@Disney.com (BOCCS002)
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

}
