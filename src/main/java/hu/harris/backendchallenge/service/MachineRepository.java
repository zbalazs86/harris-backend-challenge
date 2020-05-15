package hu.harris.backendchallenge.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.harris.backendchallenge.domain.Machine;

@Repository
public interface MachineRepository extends JpaRepository<Machine, UUID> {

}
	