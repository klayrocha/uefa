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

			// 1 - bePropagatedByAnyMister & 2 - beRetainedIfRecipientHasAlreadyAGossip
			if (!from.getMessage().equals("") && !to.isHasAlreadyAGossip() || to.getTitle().equals("Sir")) {

				// 3 - beRememberedByDoctors
				if (to.getTitle().equals("Dr") || from.getTitle().equals("Dr")) {
					if (!to.getTitle().equals("Lady") && !from.getTitle().equals("Lady")) {
						beRememberedByDoctors(from, to);
					} else {
						to.setNewMessage(from.getMessage());
						if (!from.isHasAlreadyAGossip()) {
							from.setNewMessage("");
						}
						if (from.getTitle().equals("Dr")) {
							to.setComingADoctor(true);
						}
						to.setHasAlreadyAGossip(true);
					}
					// 4 - alwaysBeListenedByAnAgent 5- beStoppedByAnAgent
				} else if (to.getTitle().equals("Agent") || from.getTitle().equals("Agent")) {
					if (to.getTitle().equals("Lady") || from.getTitle().equals("Lady")) {
						notBePropagatedByALadyWhenComingAMister(from, to);
					} else {
						alwaysBeListenedByAnAgent(from, to);
					}
					// 6 - beDelayedOneTurnByAProfessor 7 - beDelayedOneTurnByAProfessor
				} else if (to.getTitle().equals("Pr") || from.getTitle().equals("Pr")) {
					beDelayedOneTurnByAProfessor(from, to);
					// notBePropagatedByALadyWhenComingAMister
				} else if (to.getTitle().equals("Lady") || from.getTitle().equals("Lady")) {
					notBePropagatedByALadyWhenComingAMister(from, to);
				} else if (to.getTitle().equals("Sir") || from.getTitle().equals("Sir")) {
					beReturnedAndInvertedByGentlemen(from, to);
				} else {
					to.setNewMessage(from.getMessage());
					if (!from.isHasAlreadyAGossip()) {
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

	private void beReturnedAndInvertedByGentlemen(Person from, Person to) {
		if (to.getTitle().equals("Sir") && !to.getMessage().equals("")) {
			from.setNewMessage(new StringBuilder(to.getMessage()).reverse().toString());
			if (!to.isHasAlreadyAGossip()) {
				to.setNewMessage("");
			}
			to.setNewMessage("");
		} else {
			if (!from.getTitle().equals("Sir")) {
				to.setNewMessage(from.getMessage());
				if (!from.isHasAlreadyAGossip()) {
					from.setNewMessage("");
				}
			}
		}
		to.setHasAlreadyAGossip(true);
	}

	private void notBePropagatedByALadyWhenComingAMister(Person from, Person to) {
		if (!from.getTitle().equals("Lady")) {
			if (!from.isComingAMister()) {
				to.setNewMessage(from.getMessage());
				if (!from.isHasAlreadyAGossip()) {
					from.setNewMessage("");
				}
				if (from.getTitle().equals("Mr")) {
					to.setComingAMister(true);
				}
				if (from.getTitle().equals("Dr")) {
					to.setComingADoctor(true);
				}
			}
		} else {
			if (from.isComingADoctor()) {
				to.setNewMessage(from.getMessage());
				if (!from.isHasAlreadyAGossip()) {
					from.setNewMessage("");
				}
			}
		}
		to.setHasAlreadyAGossip(true);
	}

	private void beDelayedOneTurnByAProfessor(Person from, Person to) {
		if (from.getTitle().equals("Pr")) {
			if (from.getDelayedOneTurnByAProfessor() > 0) {
				to.setNewMessage(from.getMessage());
				if (!from.isHasAlreadyAGossip()) {
					from.setNewMessage("");
				}
				to.setHasAlreadyAGossip(true);
				from.setDelayedOneTurnByAProfessor(0);
			} else {
				from.setDelayedOneTurnByAProfessor(1);
			}
		} else {
			to.setNewMessage(from.getMessage());
			if (!from.isHasAlreadyAGossip()) {
				from.setNewMessage("");
			}
		}
		to.setHasAlreadyAGossip(true);
	}

	private void alwaysBeListenedByAnAgent(Person from, Person to) {
		if (to.getNewMessage() != null && !to.getNewMessage().equals("")) {
			to.setNewMessage(to.getNewMessage() + ", " + from.getMessage());
		} else if (to.getMessage() != null && !to.getMessage().equals("")) {
			to.setNewMessage(to.getMessage() + ", " + from.getMessage());
		} else {
			to.setNewMessage(from.getMessage());
		}
		from.setNewMessage("");
		if (!to.getTitle().equals("Agent")) {
			to.setNewMessage("");
		}
	}

	private void beRememberedByDoctors(Person from, Person to) {
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

	private void clearHasAlreadyAGossip() {
		for (Person p : persons) {
			p.setHasAlreadyAGossip(false);
		}
	}
}