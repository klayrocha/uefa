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
			persons.add(new Person(newPersons[i].split(" ")[1]));
		}
	}

	public Gossips from(String from) {
		this.from = from;
		return this;
	}

	public Gossips to(String to) {
		if (this.message == null || this.message.equals("")) {
			connections.add(new Connection(from,to));
		} else {
			persons.forEach(p -> {
				if (p.getName().equals(to)) {
					p.setMessage(this.message);
				} else {
					p.setMessage("");
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
		Person personFrom = persons.stream().filter( p -> !p.getMessage().equals("")).findAny().get();
		String message = personFrom.getMessage();
		String to = getToFromFromConnection(personFrom.getName());
		for (Person p : persons) {
			if (p.getName().equals(to)) {
				p.setMessage(message);
			} else {
				p.setMessage("");
			}
		}
	}
	
	private String getToFromFromConnection(String from) {
		for (Connection c : connections) {
			if (c.getFrom().equals(from)) {
				return c.getTo();
			}
		}
		return "";
	}
}
