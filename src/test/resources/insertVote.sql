INSERT INTO associate (id, name, cpf)
VALUES
(1, 'Fulano', '317.932.710-38');

INSERT INTO schedule (id, duration, date_start, date_end, title)
VALUES(1, 10, '2023-03-13T13:30', '2023-03-13T13:40', 'Pauta 1');

INSERT INTO vote (id, vote, schedule_id, associate_id)
VALUES (1, 'SIM', 1, 1);
