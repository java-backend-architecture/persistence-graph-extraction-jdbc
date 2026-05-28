package dev.dmitriirussu.graphextraction.jdbc.application.view;

import java.util.List;

/**
 * Application read model.
 */
public record OwnerView(
        String id,
        String name,
        List<PetView> pets
) {
    public OwnerView { pets = List.copyOf(pets); }
}
