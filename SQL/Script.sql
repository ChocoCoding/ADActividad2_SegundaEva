--Creamos el esquema
CREATE SCHEMA objetos;

--CREAMOS LOS TIPOS
CREATE TYPE objetos.Persona AS(
	nombre VARCHAR(50),
	edad INT
);

CREATE TYPE objetos.Jugador AS(
	dorsal INT,
	posicion VARCHAR(50),
	altura DECIMAL(3,2)
);

CREATE TYPE objetos.Equipo AS(
	nombre VARCHAR(50),
	ciudad VARCHAR(50),
	entrenador objetos.Persona
);


CREATE TABLE objetos.Equipos(
	equipo_id serial PRIMARY KEY,
	equipo_info objetos.Equipo
);

CREATE TABLE objetos.Jugadores(
	jugador_id serial PRIMARY KEY,
	datos_personales objetos.Persona,
	jugador_info objetos.Jugador,
	equipo_id INT REFERENCES objetos.Equipos(equipo_id)
);

CREATE TABLE objetos.Partidos(
	partido_id serial PRIMARY KEY,
	fecha Date,
	equipo_local INT REFERENCES objetos.Equipos(equipo_id),
	equipo_visitante INT REFERENCES objetos.Equipos(equipo_id)
);

INSERT INTO objetos.equipos (equipo_info) VALUES (ROW('F.C Barcelona','Barcelona',ROW('Joshua','36')));
INSERT INTO objetos.equipos (equipo_info) VALUES (ROW('Atletico de Madrid','Madrid',ROW('Manuel','45')));
INSERT INTO objetos.equipos (equipo_info) VALUES (ROW('R.C Celta','Vigo',ROW('Sonia','55')));

INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Ronaldinho','45'),(ROW('23','delantero','1.83')),2);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Piqué','36'),(ROW('13','defensa','1.80')),1);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Puyol','23'),(ROW('15','central','1.93')),3);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Casillas','19'),(ROW('33','portero','1.85')),1);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Raúl','27'),(ROW('12','deleantero','1.84')),3);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Ronaldo','31'),(ROW('21','central','1.78')),2);


INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-01',1,2);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-07',1,3);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-12',2,3);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-16',3,1);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-25',3,2);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-31',2,1);


