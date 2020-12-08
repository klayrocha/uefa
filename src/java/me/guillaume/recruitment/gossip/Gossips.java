package me.guillaume.recruitment.gossip;

import java.util.ArrayList;
import java.util.List;

public class Gossips {

	private String message;
	private String from;

	private List<Person> persons = new ArrayList<Person>();
	private List<Connection> connections = new ArrayList<Connection>();

	public Gossips(String... newPersons) {
		for (int i = 0; i < newPersons.length; i++) {
			// I'm sure there are always two words with space between, if not, needs to
			// treatment it
			persons.add(new Person(newPersons[i].split(" ")[0], newPersons[i].split(" ")[1], ""));
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
		for (Connection connection : connections) {
			Person from = persons.stream().filter(p -> p.getName().equals(connection.getFrom())).findAny().get();
			Person to = persons.stream().filter(p -> p.getName().equals(connection.getTo())).findAny().get();

			if (!from.getMessage().equals("")) {
				if (!to.isHasAlreadyAGossip()) {
					if (to.getTitle().equals("Dr") && !to.getMessage().equals("")) {
						to.setNewMessage(to.getMessage() + ", " + from.getMessage());
						from.setNewMessage("");
					} else {
						if (from.getMessage().contains(",")) {
							to.setNewMessage(from.getMessage().split(",")[1].trim());
						} else {
							to.setNewMessage(from.getMessage());
						}

					}
					if (!from.isHasAlreadyAGossip() && !from.getTitle().equals("Dr")) {
						from.setNewMessage("");
					}
					to.setHasAlreadyAGossip(true);

				}
			}
		}
		for (Person p : persons) {
			if (p.getNewMessage() != null) {
				p.setMessage(p.getNewMessage());
			}
		}

	}

	private void clearHasAlreadyAGossip() {
		for (Person p : persons) {
			p.setHasAlreadyAGossip(false);
		}
	}
}
