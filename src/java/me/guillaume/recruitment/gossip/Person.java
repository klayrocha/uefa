package me.guillaume.recruitment.gossip;

public class Person {

	private String name;
	private String message;
	private boolean hasAlreadyAGossip;

	public Person(String name, String message) {
		this.name = name;
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	public boolean isHasAlreadyAGossip() {
		return hasAlreadyAGossip;
	}

	public void setHasAlreadyAGossip(boolean hasAlreadyAGossip) {
		this.hasAlreadyAGossip = hasAlreadyAGossip;
	}

}
