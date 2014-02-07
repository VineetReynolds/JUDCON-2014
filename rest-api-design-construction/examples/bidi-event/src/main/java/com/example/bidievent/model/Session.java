package com.example.bidievent.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import com.example.bidievent.model.Event;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Session implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	private String sessionName;

	@Column
	private String speaker;

	@ManyToOne
	private Event event;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		if (id != null) {
			return id.equals(((Session) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
	}

	public String getSessionName() {
		return this.sessionName;
	}

	public void setSessionName(final String sessionName) {
		this.sessionName = sessionName;
	}

	public String getSpeaker() {
		return this.speaker;
	}

	public void setSpeaker(final String speaker) {
		this.speaker = speaker;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (sessionName != null && !sessionName.trim().isEmpty())
			result += "sessionName: " + sessionName;
		if (speaker != null && !speaker.trim().isEmpty())
			result += ", speaker: " + speaker;
		return result;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(final Event event) {
		this.event = event;
	}
}