package com.senla.internship.caveatemptor.repositories;

import com.senla.internship.caveatemptor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
