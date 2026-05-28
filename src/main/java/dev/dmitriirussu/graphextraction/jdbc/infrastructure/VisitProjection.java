package dev.dmitriirussu.graphextraction.jdbc.infrastructure;

import java.time.LocalDate;

/**
 * Persistence read model for graph extraction.
 */
class VisitProjection {
    private final String id;
    private final LocalDate date;
    private final String petId;

    private VisitProjection(String id, LocalDate date, String petId){
        this.id = id;
        this.date = date;
        this.petId = petId;
    }

    static VisitProjection of(String id, LocalDate date, String petId) {
        return new VisitProjection(id, date, petId);
    }

    String id(){ return id;}
    LocalDate date(){ return date;}
    String petId(){ return petId;}
}
