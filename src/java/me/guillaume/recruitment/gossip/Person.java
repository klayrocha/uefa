package me.guillaume.recruitment.gossip;

public class Person {

	private String title;
	private String name;
	private String message;
	private String newMessage;
	private boolean hasAlreadyAGossip;
	private int delayedOneTurnByAProfessor;

	public Person(String title, String name, String message) {
		this.title = title;
		this.name = name;
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getNewMessage() {
		return newMessage;
	}

	public void setNewMessage(String newMessage) {
		this.newMessage = newMessage;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public boolean isHasAlreadyAGossip() {
		return hasAlreadyAGossip;
	}

	public void setHasAlreadyAGossip(boolean hasAlreadyAGossip) {
		this.hasAlreadyAGossip = hasAlreadyAGossip;
	}

	public int getDelayedOneTurnByAProfessor() {
		return delayedOneTurnByAProfessor;
	}

	public void setDelayedOneTurnByAProfessor(int delayedOneTurnByAProfessor) {
		this.delayedOneTurnByAProfessor = delayedOneTurnByAProfessor;
	}

}
