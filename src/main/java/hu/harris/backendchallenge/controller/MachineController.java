package hu.harris.backendchallenge.controller;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.harris.backendchallenge.domain.Machine;
import hu.harris.backendchallenge.dto.MachineDto;
import hu.harris.backendchallenge.service.ApplicationService;
import hu.harris.backendchallenge.service.ModelMapperService;

@RestController
public class MachineController {

	@Autowired
	private ApplicationService service;
	
	@Autowired
	private ModelMapperService mapperService;
	
	@GetMapping("/machines")
	List<MachineDto> all() {
		List<Machine> machines = service.getAllMachines();
		
		return machines.stream()
				.map(mapperService::convertToDto)
				.collect(Collectors.toList());
	}
	
	@PostMapping("/machines")
	@ResponseStatus(HttpStatus.CREATED)
	MachineDto createMachine(@RequestBody MachineDto machineDto) throws ParseException {
		Machine machine = mapperService.convertToEntity(machineDto);
		Machine machineCreated = service.createMachine(machine);
		
		return mapperService.convertToDto(machineCreated);
	}
	
	@GetMapping("/machines/{id}")
	MachineDto getMachine(@PathVariable UUID id) {
		Machine machine = service.getMachineById(id)
			.orElseThrow(() -> new MachineNotFoundException(id));
		
		return mapperService.convertToDto(machine);
	}
	
	@PutMapping("/machines/{id}")
	@ResponseStatus(HttpStatus.OK)
	MachineDto updateOrCreate(@PathVariable UUID id, @RequestBody MachineDto machineDto) throws ParseException {
		Machine machine = mapperService.convertToEntity(machineDto);
		Machine machineSaved = service.createOrUpdateMachine(machine);
		
		return mapperService.convertToDto(machineSaved);
	}
	
	@DeleteMapping("/machines/{id}")
	void deleteMachine(@PathVariable UUID id) {
		service.deleteMachineById(id);
	}
	
}
