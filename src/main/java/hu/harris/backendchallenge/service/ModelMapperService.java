package hu.harris.backendchallenge.service;

import java.text.ParseException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.harris.backendchallenge.domain.Machine;
import hu.harris.backendchallenge.dto.MachineDto;

@Service
public class ModelMapperService {

	private final ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private ApplicationService service;
	
	public MachineDto convertToDto(Machine machine) {
		MachineDto machineDto = modelMapper.map(machine, MachineDto.class);
		machineDto.setCreatedAt(machine.getCreatedAt());
		machineDto.setUpdatedAt(machine.getUpdatedAt());
		
		return machineDto;
	}
	
	public Machine convertToEntity(MachineDto machineDto) throws ParseException {
		Machine machine = modelMapper.map(machineDto, Machine.class);
		machine.setId(UUID.fromString(machineDto.getId()));
		machine.setCreatedAt(machineDto.getCreatedAtConverted());
		machine.setUpdatedAt(machineDto.getUpdatedAtConverted());
		
		if(machine.getId() != null) {
			Machine oldMachine = service.getMachineById(machine.getId()).orElse(null);
			
			if(oldMachine != null) {
				machine.setActive(oldMachine.isActive());				
			}
		}
		
		return machine;
	}
	
}
