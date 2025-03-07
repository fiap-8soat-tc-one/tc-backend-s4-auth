package com.fiap.tc.infrastructure.persistence.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "system_module", schema = "security")
@Data
public class ModuleEntity {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_system")
    private SystemEntity system;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

}
