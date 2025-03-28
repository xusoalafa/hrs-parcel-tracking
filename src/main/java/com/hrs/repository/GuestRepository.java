package com.hrs.repository;

import com.hrs.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
  @Query(
      "SELECT g FROM Guest g WHERE (g.firstName LIKE %:name% OR g.lastName LIKE %:name%) AND g.checkedOut = false AND g.checkedIn = true")
  List<Guest> searchAvailableGuestsByName(@Param("name") String name);
}
