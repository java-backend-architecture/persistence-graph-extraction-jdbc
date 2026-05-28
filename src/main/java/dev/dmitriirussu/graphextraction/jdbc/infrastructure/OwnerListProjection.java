package dev.dmitriirussu.graphextraction.jdbc.infrastructure;

import java.util.*;

/**
 * Persistence read model for graph extraction.
 */
class OwnerListProjection {
    private final String id;
    private final String name;
    private final List<String> petNames = new ArrayList<>();

    private OwnerListProjection(String id, String name) {
        this.id = id;
        this.name = name;
    }

    static OwnerListProjection of(String id, String name) {
        return new OwnerListProjection(id, name);
    }

    void addPet(String petName) {
        if (petName != null) petNames.add(petName);
    }

    String id() { return id; }
    String name() { return name; }
    List<String> petNames() { return petNames; }
}
