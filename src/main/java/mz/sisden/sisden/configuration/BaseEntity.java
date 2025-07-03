/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mz.sisden.sisden.utils.Texter;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Hibernate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.zkoss.bind.proxy.FormProxyObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.isNull;

@Getter
@Setter
@Audited
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EntityListeners(value = {BaseEntityListener.class})
public abstract class BaseEntity implements Serializable {
    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private Long createdByUserId;
    private Long updatedByUserId;

    public abstract Long getId();

    public abstract void setId(Long id);

    @Override
    public boolean equals(Object thatObject) {
        if (this == thatObject) return true;
        if (isNull(thatObject)) return false;

        //this is for ZK, this converts a ZkFormProxy object to its origin.
        if (thatObject instanceof FormProxyObject formProxyObject) {
            thatObject = formProxyObject.getOriginObject();
        }

        if (!Objects.equals(Hibernate.getClass(this), Hibernate.getClass(thatObject))) return false;

        BaseEntity that = (BaseEntity) thatObject;
        if (ObjectUtils.allNull(that.getId(), this.getId())) return false;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    public Class<?> getHibernateClass() {
        return Hibernate.getClass(this);
    }

    @Override
    public String toString() {
        return Texter.format("{}{id: {}, createdAt: {}, createdByUserId: {}}", this.getHibernateClass().getSimpleName(), this.getId(), this.getCreatedAt(), this.getCreatedByUserId());
    }

    @Override
    public BaseEntity clone() {
        try {
            return (BaseEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}