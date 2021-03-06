package de.jbee.poker.board;

import java.util.EnumSet;
import java.util.Iterator;


public final class Roles implements Iterable<Role> {

	private final EnumSet<Role> roles;

	private Roles(EnumSet<Role> roles) {
		this.roles = roles;
	}

	public static Roles takenAre(Role... roles) {

		return new Roles(EnumSet.of(roles[0], roles));
	}

	public boolean isPresent(Role role) {

		return roles.contains(role);
	}

	@Override
	public Iterator<Role> iterator() {
		return roles.iterator();
	}

}
