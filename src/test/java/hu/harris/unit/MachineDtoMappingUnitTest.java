package hu.harris.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import hu.harris.backendchallenge.domain.Machine;
import hu.harris.backendchallenge.dto.MachineDto;
import hu.harris.backendchallenge.service.ApplicationService;
import hu.harris.backendchallenge.service.ModelMapperService;

@ExtendWith(MockitoExtension.class)
public class MachineDtoMappingUnitTest {

	@Spy @InjectMocks
	ModelMapperService mapperService;
	
	@Spy
	ModelMapper modelMapper;
	
	@Mock
	ApplicationService appService;
	
	
	@Test
	public void testEntityToDtoConversion() {
		Machine machine = new Machine();
		machine.setId(UUID.randomUUID());
		machine.setName("name");
		
		MachineDto machineDto = mapperService.convertToDto(machine);
		
		assertEquals(machine.getId().toString(), machineDto.getId());
		assertEquals(machine.getName(), machineDto.getName());
	}
	
	@Test
	public void testDtoToEntityConversion() throws ParseException {
		UUID uuid = UUID.randomUUID();
		
		// There is no old machine
		MachineDto machineDto = new MachineDto();
		machineDto.setId(uuid.toString());
		machineDto.setName("name");
		
		Machine machine = mapperService.convertToEntity(machineDto);
		
		assertEquals(machineDto.getId(), machine.getId().toString());
		assertEquals(machineDto.getName(), machine.getName());
		assertTrue(machine.isActive());
		
		// Inactive old machine
		Machine oldMachine = new Machine();
		Mockito.when(appService.getMachineById(uuid)).thenReturn(Optional.of(oldMachine));
		
		oldMachine.setActive(false);
		
		machine = mapperService.convertToEntity(machineDto);
		
		assertEquals(machineDto.getId(), machine.getId().toString());
		assertEquals(machineDto.getName(), machine.getName());
		assertFalse(machine.isActive());
		
		// Active old machine
		oldMachine.setActive(true);
		machine = mapperService.convertToEntity(machineDto);
		
		assertEquals(machineDto.getId(), machine.getId().toString());
		assertEquals(machineDto.getName(), machine.getName());
		assertTrue(machine.isActive());
		
	}
}
