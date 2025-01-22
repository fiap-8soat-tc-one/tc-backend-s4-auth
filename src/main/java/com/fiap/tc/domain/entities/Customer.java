package com.fiap.tc.domain.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class Customer implements Serializable {
    private UUID id;
    private String document;
    private String name;
    private String email;
}
