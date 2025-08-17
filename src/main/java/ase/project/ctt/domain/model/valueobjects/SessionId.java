package ase.project.ctt.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SessionId {

    private UUID value;

    protected SessionId() {
        // for jpa
    }

    private SessionId(UUID value) {
        this.value = value;
    }

    public static SessionId of(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("SessionId cannot be null");
        }
        return new SessionId(value);
    }

    public static SessionId newId() {
        return new SessionId(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }

    public void setValueFromString(String uuidAsString) {
        this.value = UUID.fromString(uuidAsString);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionId sessionId)) return false;
        return Objects.equals(value, sessionId.value);
    }
}
