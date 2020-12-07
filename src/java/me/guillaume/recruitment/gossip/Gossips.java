package me.guillaume.recruitment.gossip;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Gossips {

	private String message;
	private String from;

	private List<Person> persons = new ArrayList<Person>();
	private List<Connection> connections = new ArrayList<Connection>();

	public Gossips(String... newPersons) {
		for (int i = 0; i < newPersons.length; i++) {
			// I'm sure there are always two words with space between, if not, needs to treatment it
			persons.add(new Person(newPersons[i].split(" ")[1], ""));
		}
	}

	public Gossips from(String from) {
		this.from = from;
		return this;
	}

	public Gossips to(String to) {
		if (this.message == null || this.message.equals("")) {
			connections.add(new Connection(from, to));
		} else {
			persons.forEach(p -> {
				if (p.getName().equals(to)) {
					p.setMessage(this.message);
				}
			});
		}
		this.message = "";
		return this;
	}

	public Gossips say(String message) {
		this.message = message;
		return this;
	}

	public String ask(String person) {
		for (Person p : persons) {
			if (p.getName().equals(person)) {
				return p.getMessage();
			}
		}
		return "";
	}

	public void spread() {
		clearHasAlreadyAGossip();
		List<Person> personsWithMessage = persons.stream().filter(p -> !p.getMessage().equals(""))
				.collect(Collectors.toList());

		for (Person person : personsWithMessage) {
			String message = person.getMessage();
			String to = getToOfFromConnection(person.getName());
			if (!to.equals("")) {
				boolean canClean = false;

				for (Person p : persons) {
					if (p.getName().equals(to) && !p.isHasAlreadyAGossip()) {
						canClean = true;
						p.setMessage(message);
						p.setHasAlreadyAGossip(true);
					}
				}
				// Clean from
				for (Person p : persons) {
					if (p.getName().equals(person.getName()) && canClean) {
						p.setMessage("");
					}
				}
			}

		}
	}

	private void clearHasAlreadyAGossip() {
		for (Person p : persons) {
			p.setHasAlreadyAGossip(false);
		}
	}

	private String getToOfFromConnection(String from) {
		for (Connection c : connections) {
			if (c.getFrom().equals(from)) {
				return c.getTo();
			}
		}
		return "";
	}
}
