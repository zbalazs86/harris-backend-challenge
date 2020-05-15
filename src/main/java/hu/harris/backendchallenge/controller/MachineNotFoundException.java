package hu.harris.backendchallenge.controller;

import java.util.UUID;

public class MachineNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MachineNotFoundException(UUID uuid) {
		super("Could not find employee " + uuid.toString());
	}
}
