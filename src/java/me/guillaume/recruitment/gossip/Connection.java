package me.guillaume.recruitment.gossip;

import java.io.Serializable;

public class Connection implements Serializable {

	private static final long serialVersionUID = 3708366247565104888L;
	private String from;
	private String to;

	public Connection(String from, String to) {
		this.from = from;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
}
