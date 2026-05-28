INSERT INTO owners (id, name)
VALUES
    ('1', 'jack'),
    ('2', 'ann'),
    ('3', 'bob');

INSERT INTO pets (id, name, owner_id)
VALUES
    ('1', 'buddy1', '1'),
    ('2', 'buddy2', '1'),
    ('3', 'milo', '2'),
    ('4', 'hew1', '3'),
    ('5', 'hew2', '3'),
    ('6', 'hew3', '3'),
    ('7', 'hew4', '3');

INSERT INTO visits (id, date, pet_id)
VALUES
    ('1', DATE '2026-01-10', '1'),
    ('2', DATE '2026-02-15', '1'),
    ('3', DATE '2026-03-01', '2'),
    ('4', DATE '2026-04-20', '3'),
    ('5', DATE '2026-04-20', '3'),
    ('6', DATE '2026-04-20', '3'),
    ('7', DATE '2026-04-20', '3'),
    ('8', DATE '2026-04-20', '3');