package dev.dmitriirussu.graphextraction.jdbc.infrastructure;

import java.time.LocalDate;

import dev.dmitriirussu.graphextraction.jdbc.application.view.OwnerListView;
import dev.dmitriirussu.graphextraction.jdbc.application.view.OwnerView;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ViewMapperTest {

    @Test
    void toView_mapsOwnerWithPetsAndVisits() {
        OwnerProjection owner = OwnerProjection.of("1", "jack");
        PetProjection pet = owner.getOrCreatePet("10", "buddy");
        pet.getOrCreateVisit("100", LocalDate.of(2026, 1, 10));

        OwnerView view = ViewMapper.toView(owner);

        assertThat(view.id()).isEqualTo("1");
        assertThat(view.name()).isEqualTo("jack");
        assertThat(view.pets()).hasSize(1);
        assertThat(view.pets().get(0).name()).isEqualTo("buddy");
        assertThat(view.pets().get(0).visits()).hasSize(1);
    }

    @Test
    void toListView_mapsOwnerWithPetNames() {
        OwnerListProjection owner = OwnerListProjection.of("1", "jack");
        owner.addPet("buddy1");
        owner.addPet("buddy2");

        OwnerListView view = ViewMapper.toListView(owner);

        assertThat(view.id()).isEqualTo("1");
        assertThat(view.pets()).containsExactly("buddy1", "buddy2");
    }

    @Test
    void toView_mapsOwnerWithNoPets() {
        OwnerProjection owner = OwnerProjection.of("1", "jack");

        OwnerView view = ViewMapper.toView(owner);

        assertThat(view.pets()).isEmpty();
    }
}
