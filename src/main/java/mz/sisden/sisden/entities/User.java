/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mz.sisden.sisden.configuration.BaseEntity;
import mz.sisden.sisden.configuration.envers.Revision;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.zkoss.bind.annotation.Transient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Table(name = "`user`")
@Getter
@Setter
@Entity
@Audited
@AuditOverride
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class User extends BaseEntity {
    @Id
    @SequenceGenerator(name = "user_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "user_id_sequence")
    private Long id;

    private String username;
    private String password;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private Integer tries = 5;

    @OneToOne
    @Fetch(FetchMode.SELECT)
    private SinglePerson singlePerson;

    @ManyToMany
    @JoinTable(
            name = "user_vs_user_group",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_group_id", referencedColumnName = "id")
            }
    )
    @Builder.Default
    private List<UserGroup> userGroupList = new ArrayList<>();

    @Builder.Default
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "user")
    private List<UserUserGroupPermission> userUserGroupPermissionList = new ArrayList<>();

    @NotAudited
    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Revision> revisionList = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private List<Instituition> instituicoes;

    @OneToMany(mappedBy = "user")
    private List<TeamUser> teamUsers;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", singlePerson=" + Optional.ofNullable(singlePerson).map(SinglePerson::getName).orElse("NO SINGLE PERSON RELATED!") +
                ", active=" + active +
                '}';
    }

    @Transient
    public String getEmail() {
        if (nonNull(this.singlePerson)) {
            return this.singlePerson.getEmail();
        }
        return null;
    }

    @Transient
    public String getName() {
        return Optional.of(this)
                .map(User::getSinglePerson)
                .map(SinglePerson::getName)
                .filter(StringUtils::isNotBlank)
                .orElse(this.getUsername());
    }

    @Transient
    public List<SimpleGrantedAuthority> getAuthorityList() {
        List<SimpleGrantedAuthority> authorities = this.getUserUserGroupPermissionList()
                .stream()
                .flatMap(uugp -> Stream.of(
                                Optional.ofNullable(uugp.getPermission()).map(Permission::getCode).stream(),
                                Optional.ofNullable(uugp.getPermission()).map(Permission::getModule).map(Module::getCode).stream(),
                                Optional.ofNullable(uugp.getUserGroup()).map(UserGroup::getCode).stream())
                        .map(Stream::toList)
                        .flatMap(List::stream)
                )
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .toList();
        
        // Se não há permissões específicas, adicionar uma permissão básica
        if (authorities.isEmpty()) {
            authorities = List.of(new SimpleGrantedAuthority("USER"));
        }
        
        return authorities;
    }
}
