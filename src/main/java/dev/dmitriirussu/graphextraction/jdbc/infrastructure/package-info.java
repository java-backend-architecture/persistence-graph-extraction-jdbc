/**
 * JDBC implementation of the read repository.
 *
 * <p>All classes are intentionally kept in one package to leverage
 * package-private visibility as an encapsulation boundary.
 *
 * <p>Public API: {@link dev.dmitriirussu.graphextraction.jdbc.infrastructure.JdbcOwnerReadRepository}
 *
 * <p>Internal (package-private by design):
 * {@link dev.dmitriirussu.graphextraction.jdbc.infrastructure.OwnerProjection},
 * {@link dev.dmitriirussu.graphextraction.jdbc.infrastructure.PetProjection},
 * {@link dev.dmitriirussu.graphextraction.jdbc.infrastructure.VisitProjection},
 * {@link dev.dmitriirussu.graphextraction.jdbc.infrastructure.OwnerListProjection},
 * {@link dev.dmitriirussu.graphextraction.jdbc.infrastructure.OwnerProjectionExtractor},
 * {@link dev.dmitriirussu.graphextraction.jdbc.infrastructure.ViewMapper}
 */
package dev.dmitriirussu.graphextraction.jdbc.infrastructure;