package dev.dmitriirussu.graphextraction.jdbc.infrastructure;

import java.util.*;

/**
 * Persistence read model for graph extraction.
 */
class OwnerProjection {
    private final String id;
    private final String name;
    private final Map<String, PetProjection> pets = new LinkedHashMap<>();

    private OwnerProjection(String id, String name) {
        this.id = id;
        this.name = name;
    }

    static OwnerProjection of(String id, String name) {
        return new OwnerProjection(id, name);
    }

    PetProjection getOrCreatePet(String petId, String petName) {
        return pets.computeIfAbsent(petId, k -> PetProjection.of(petId, petName, this.id));
    }

    String id() { return id; }
    String name() { return name; }
    Collection<PetProjection> pets() { return List.copyOf(pets.values()); }
}
