package dev.dmitriirussu.graphextraction.jdbc.infrastructure;

import java.util.*;
import java.time.LocalDate;

/**
 * Persistence read model for graph extraction.
 */
class PetProjection {
    private final String id;
    private final String name;
    private final String ownerId;
    private final Map<String, VisitProjection> visits = new LinkedHashMap<>();

    private PetProjection(String id, String name, String ownerId) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
    }

    static PetProjection of(String id, String name, String ownerId) {
        return new PetProjection(id, name, ownerId);
    }

    VisitProjection getOrCreateVisit(String visitId, LocalDate date) {
        return visits.computeIfAbsent(visitId, k -> VisitProjection.of(visitId, date, this.id));
    }

    String id() { return id; }
    String name() { return name; }
    String ownerId() { return ownerId; }
    Collection<VisitProjection> visits() { return List.copyOf(visits.values()); }
}
