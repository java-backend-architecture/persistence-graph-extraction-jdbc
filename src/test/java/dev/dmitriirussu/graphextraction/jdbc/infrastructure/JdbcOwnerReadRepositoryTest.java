package dev.dmitriirussu.graphextraction.jdbc.infrastructure;

import dev.dmitriirussu.graphextraction.jdbc.application.view.OwnerListView;
import dev.dmitriirussu.graphextraction.jdbc.application.view.OwnerView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcOwnerReadRepositoryTest {

    @Autowired
    JdbcClient jdbc;

    JdbcOwnerReadRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JdbcOwnerReadRepository(jdbc);
    }

    @Test
    void findByIdWithGraph_returnsOwnerWithPetsAndVisits() {
        Optional<OwnerView> result = repository.findByIdWithGraph("1");

        assertThat(result).isPresent();
        assertThat(result.get().name()).isEqualTo("jack");
        assertThat(result.get().pets()).hasSize(2);
        assertThat(result.get().pets().get(0).visits()).hasSize(2);
    }

    @Test
    void findByIdWithGraph_returnsOwnerWithNoPets() {
        Optional<OwnerView> result = repository.findByIdWithGraph("3");

        assertThat(result).isPresent();
        assertThat(result.get().pets()).hasSize(4);
        assertThat(result.get().pets().get(0).visits()).isEmpty();
    }

    @Test
    void findByIdWithGraph_returnsEmpty_whenNotFound() {
        Optional<OwnerView> result = repository.findByIdWithGraph("999");

        assertThat(result).isEmpty();
    }

    @Test
    void findAllWithGraph_returnsAllOwners() {
        List<OwnerView> result = repository.findAllWithGraph();

        assertThat(result).hasSize(3);
    }

    @Test
    void findAllFlat_returnsPetNamesPerOwner() {
        List<OwnerListView> result = repository.findAllFlat();

        assertThat(result).hasSize(3);
        assertThat(result.get(0).pets()).containsExactly("buddy1", "buddy2");
        assertThat(result.get(1).pets()).containsExactly("milo");
        assertThat(result.get(2).pets()).containsExactly("hew1", "hew2", "hew3", "hew4");
    }
}
