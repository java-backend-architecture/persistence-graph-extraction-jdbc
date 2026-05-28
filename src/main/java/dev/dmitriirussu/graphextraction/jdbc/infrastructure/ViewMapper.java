package dev.dmitriirussu.graphextraction.jdbc.infrastructure;

import dev.dmitriirussu.graphextraction.jdbc.application.view.OwnerListView;
import dev.dmitriirussu.graphextraction.jdbc.application.view.OwnerView;
import dev.dmitriirussu.graphextraction.jdbc.application.view.PetView;
import dev.dmitriirussu.graphextraction.jdbc.application.view.VisitView;

import java.util.List;

/**
 * Maps persistence projections to application read models.
 */
final class ViewMapper {

    private ViewMapper() {}

    public static OwnerView toView(OwnerProjection owner) {
        List<PetView> pets = owner.pets().stream().map(ViewMapper::toView).toList();
        return new OwnerView(owner.id(), owner.name(), pets);
    }

    private static PetView toView(PetProjection pet) {
        List<VisitView> visits = pet.visits().stream().map(ViewMapper::toView).toList();
        return new PetView(pet.id(), pet.name(), pet.ownerId(), visits);
    }

    private static VisitView toView(VisitProjection visit) {
        return new VisitView(visit.id(), visit.date(), visit.petId());
    }

    static OwnerListView toListView(OwnerListProjection owner) {
        return new OwnerListView(owner.id(), owner.name(), owner.petNames());
    }
}
