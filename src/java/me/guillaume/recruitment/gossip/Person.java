package me.guillaume.recruitment.gossip;

public class Person {

	private String name;
	private String message;

	public Person(String name) {
		this.name = name;
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
}
