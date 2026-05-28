package dev.dmitriirussu.graphextraction.jdbc.infrastructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * Assembles owner projections from a flat ResultSet into an object graph.
 */
final class OwnerProjectionExtractor {

    private OwnerProjectionExtractor() {} // никто не создаст экземпляр

    // owner + pets + visits
    static List<OwnerProjection> extractWithGraph(ResultSet rs) throws SQLException {

        Map<String, OwnerProjection> owners = new LinkedHashMap<>();

        while (rs.next()) {
            String ownerId  = rs.getString("owner_id");
            String ownerName = rs.getString("owner_name");

            OwnerProjection owner = owners.computeIfAbsent(
                    ownerId, id -> OwnerProjection.of(id, ownerName)
            );

            String petId = rs.getString("pet_id");
            if (petId != null) {
                String petName = rs.getString("pet_name");
                PetProjection pet = owner.getOrCreatePet(petId, petName);

                String visitId = rs.getString("visit_id");
                if (visitId != null) {
                    LocalDate visitDate = rs.getDate("visit_date").toLocalDate();
                    pet.getOrCreateVisit(visitId, visitDate);
                }
            }
        }
        return List.copyOf(owners.values());
    }

    // owner + pet names only
    static List<OwnerListProjection> extractFlat(ResultSet rs) throws SQLException {

        Map<String, OwnerListProjection> owners = new LinkedHashMap<>();

        while (rs.next()) {
            String id = rs.getString("owner_id");
            String name = rs.getString("owner_name");

            OwnerListProjection owner = owners.computeIfAbsent(
                    id, k -> OwnerListProjection.of(id, name)
            );

            owner.addPet(rs.getString("pet_name"));
        }
        return List.copyOf(owners.values());
    }
}
