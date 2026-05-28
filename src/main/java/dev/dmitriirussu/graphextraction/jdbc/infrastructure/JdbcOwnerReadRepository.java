package dev.dmitriirussu.graphextraction.jdbc.infrastructure;

import dev.dmitriirussu.graphextraction.jdbc.application.OwnerReadRepository;
import dev.dmitriirussu.graphextraction.jdbc.application.view.OwnerListView;
import dev.dmitriirussu.graphextraction.jdbc.application.view.OwnerView;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of {@link OwnerReadRepository}.
 *
 * <p>Uses {@link OwnerProjectionExtractor} to assemble object graphs
 * from flat SQL result sets, and {@link ViewMapper} to map them to read models.
 */
@Repository
public class JdbcOwnerReadRepository implements OwnerReadRepository {

    private final JdbcClient jdbc;

    JdbcOwnerReadRepository(JdbcClient jdbc) { this.jdbc = jdbc; }

    /** SQL queries for owner read operations. */
    private interface Sql {

        String SELECT_BY_ID = """
        SELECT o.id          AS owner_id,
               o.name        AS owner_name,
               p.id          AS pet_id,
               p.name        AS pet_name,
               v.id          AS visit_id,
               v.date        AS visit_date
        FROM owners o
        LEFT JOIN pets  p ON p.owner_id = o.id
        LEFT JOIN visits v ON v.pet_id  = p.id
        WHERE o.id = :ownerId
        """;

        String SELECT_ALL = """
        SELECT o.id          AS owner_id,
               o.name        AS owner_name,
               p.id          AS pet_id,
               p.name        AS pet_name,
               v.id          AS visit_id,
               v.date        AS visit_date
        FROM owners o
        LEFT JOIN pets  p ON p.owner_id = o.id
        LEFT JOIN visits v ON v.pet_id  = p.id
        ORDER BY o.id
        """;

        String SELECT_ALL_LIST = """
        SELECT o.id          AS owner_id,
               o.name        AS owner_name,
               p.name        AS pet_name
        FROM owners o
        LEFT JOIN pets p ON p.owner_id = o.id
        ORDER BY o.id
        """;
    }

    public Optional<OwnerView> findByIdWithGraph(String ownerId) {
        return jdbc.sql(Sql.SELECT_BY_ID)
                .param("ownerId", ownerId)
                .query(OwnerProjectionExtractor::extractWithGraph)
                .stream()
                .findFirst()
                .map(ViewMapper::toView);
    }

    public List<OwnerView> findAllWithGraph() {
        return jdbc.sql(Sql.SELECT_ALL)
                .query(OwnerProjectionExtractor::extractWithGraph)
                .stream()
                .map(ViewMapper::toView)
                .toList();
    }

    // owner + pet names only
    public List<OwnerListView> findAllFlat() {
        return jdbc.sql(Sql.SELECT_ALL_LIST)
                .query(OwnerProjectionExtractor::extractFlat)
                .stream()
                .map(ViewMapper::toListView)
                .toList();
    }
}
