package dev.dmitriirussu.graphextraction.jdbc.application.view;

import java.util.List;

/**
 * Application read model.
 */
public record PetView (
        String id,
        String name,
        String ownerId,
        List<VisitView> visits
) {
    public PetView { visits = List.copyOf(visits); }
}
