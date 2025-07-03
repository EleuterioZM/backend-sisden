/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.repositories;

import mz.sisden.sisden.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u left join u.singlePerson sp where lower(u.username) = lower(:usernameOrEmail) or lower(sp.email) = lower(:usernameOrEmail)")
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
}
