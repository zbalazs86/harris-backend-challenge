package hu.harris.backendchallenge.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hu.harris.backendchallenge.domain.Machine;

@Service
//@Transactional
public class ApplicationService {
	
	@Autowired
	private MachineRepository machineRepository;
	
	public List<Machine> getAllMachines() {
		return machineRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt")); 	
	}
	
	public Machine createOrUpdateMachine(Machine machineToSave) {
		
		return machineRepository.findById(machineToSave.getId())
			.map(machine -> {
				machine.setName(machineToSave.getName());
				machine.setUpdatedAt(new Date());
				return machineRepository.save(machine);
			})
			.orElseGet(()->{
				return this.createMachine(machineToSave);
			});
		
	}

	public Machine createMachine(Machine machine) {
		machine.setCreatedAt(new Date());
		machine.setUpdatedAt(new Date());
		
		return machineRepository.save(machine);
	}
	
	public Optional<Machine> getMachineById(UUID id) {
		return machineRepository.findById(id);
	}
	
	public void deleteMachineById(UUID id) {
		machineRepository.deleteById(id);
	}
	
}
