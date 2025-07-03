/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration.envers;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mz.sisden.sisden.entities.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;


@Entity
@Getter
@Setter
@Table(schema = "historic")
@RevisionEntity(RevisionListener.class)
public class Revision {

    @Id
    @SequenceGenerator(name = "revision_id_sequence", allocationSize = 1, schema = "historic")
    @GeneratedValue(generator = "revision_id_sequence")
    @RevisionNumber
    private Long id;

    @RevisionTimestamp
    private Long timestamp;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Revision that)) {
            return false;
        }

        return Objects.equals(id, that.id)
                && Objects.equals(this.timestamp, that.timestamp)
                && Objects.equals(this.createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss");
        return "Revision(id = " + id
                + ", timestamp = " + getTimestamp() + ")"
                + ", createdAt = " + Optional.ofNullable(getCreatedAt()).map(dateTimeFormatter::format).orElse("NO DATE") + ")";
    }

}
