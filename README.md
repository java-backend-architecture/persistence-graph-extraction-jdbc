# persistence-graph-extraction-jdbc

Demonstrates how to assemble an object graph from a relational database using plain JDBC (`JdbcClient`) without ORM or pagination.

## What it shows

* Assembling a three-level object graph (`Owner → Pet → Visit`) from flat SQL `JOIN` results
* Using `package-private` classes as an encapsulation boundary inside the infrastructure layer
* Separating persistence projections from application-level read models via `ViewMapper`
* Two query strategies:
  * full graph — Owner with Pets and Visits (`OwnerView`)
  * flat list — Owner with pet names only (`OwnerListView`)

## Stack

* Java 25
* Spring Boot
* Spring JDBC (`JdbcClient`)
* H2 (in-memory database)

## Structure

```
application/
    OwnerReadRepository       # port (interface)
    view/                     # read models: OwnerView, OwnerListView, PetView, VisitView

infrastructure/
    JdbcOwnerReadRepository   # JDBC implementation (package-private)
    OwnerProjectionExtractor  # assembles graph from ResultSet (package-private)
    ViewMapper                # maps projections to read models (package-private)
    OwnerProjection           # mutable accumulator (package-private)
    PetProjection             # mutable accumulator (package-private)
    VisitProjection           # mutable accumulator (package-private)
    OwnerListProjection       # mutable accumulator (package-private)
```

## Key idea

A `LEFT JOIN` across three tables returns flat rows with duplicates:

```
owner_id | owner_name | pet_id | pet_name | visit_id | visit_date
---------|------------|--------|----------|----------|------------
1        | jack       | 1      | buddy1   | 1        | 2026-01-10
1        | jack       | 1      | buddy1   | 2        | 2026-02-15
1        | jack       | 2      | buddy2   | 3        | 2026-03-01
3        | bob        | 4      | hew1     | null     | null
```

`OwnerProjectionExtractor` reassembles them into an object graph in a single pass:

* `computeIfAbsent` — get or create Owner
* `getOrCreatePet` — get or create Pet under that Owner
* `getOrCreateVisit` — add Visit if `visit_id` is not null

```
Owner(jack)
  └── Pet(buddy1)
        ├── Visit(2026-01-10)
        └── Visit(2026-02-15)
  └── Pet(buddy2)
        └── Visit(2026-03-01)
```

Compare with the jOOQ and JPA + Blaze approaches:

|                       | JDBC                                      | jOOQ                  | JPA + Blaze                        |
|-----------------------|-------------------------------------------|-----------------------|------------------------------------|
| SQL result            | flat rows (cartesian JOIN)                | nested collections    | optimized per-view query           |
| Deduplication         | `LinkedHashMap` in projections            | not needed            | not needed                         |
| Intermediate classes  | `OwnerProjection`, `PetProjection`, etc.  | none                  | `@EntityView` interfaces           |
| Mapping               | `ViewMapper`                              | `Records.mapping()`   | `ViewMapper`                       |
| N+1 problem           | no (single JOIN query)                    | no (single query)     | no (Blaze optimizes automatically) |

No ORM, no JSON parsing, no nested queries — just plain JDBC and a single pass.

All internal classes are `package-private` by design; only `OwnerReadRepository` (the interface) is exposed outside the infrastructure package.

## Tests

Integration and unit tests in `src/test/java` cover graph assembly, flat list extraction, and view mapping.

## Related

* [persistence-graph-extraction-jooq](https://github.com/java-backend-architecture/persistence-graph-extraction-jooq) — same approach with jOOQ MULTISET
* [persistence-graph-extraction-jpa](https://github.com/java-backend-architecture/persistence-graph-extraction-jpa) — same approach with JPA + Blaze Persistence
* [persistence-graph-pagination-jdbc](https://github.com/java-backend-architecture/persistence-graph-pagination-jdbc) — same approach with pagination support

## Run

```bash
./mvnw spring-boot:run
```
