--CREATE DATABASE futbol;

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

INSERT INTO objetos.equipos (equipo_info) VALUES (ROW('F.C Barcelona','Barcelona',ROW('Xavi Hernández','36')));
INSERT INTO objetos.equipos (equipo_info) VALUES (ROW('Atletico de Madrid','Madrid',ROW('Diego Simeone','45')));
INSERT INTO objetos.equipos (equipo_info) VALUES (ROW('R.C Celta','Vigo',ROW('Rafa Benítez','55')));
INSERT INTO objetos.equipos (equipo_info) VALUES (ROW('Real Madrid','Madrid',ROW('Carlo Ancelotti','55')));

INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Ronaldinho','45'),(ROW('23','delantero','1.83')),1);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Piqué','36'),(ROW('13','defensa','1.80')),1);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Carles Puyol','23'),(ROW('15','central','1.93')),1);

INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Antoine Griezmann','31'),(ROW('23','delantero','1.87')),2);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Álvaro Morata','32'),(ROW('13','defensa','1.75')),2);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Jan Oblak','29'),(ROW('15','central','1.84')),2);

INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Iago Aspas','31'),(ROW('21','delantero','1.85')),3);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Renato Tapia','37'),(ROW('21','central','1.89')),3);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Hugo Sotelo','32'),(ROW('21','defensa','1.82')),3);

INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Casillas','19'),(ROW('33','portero','1.85')),4);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Raúl','27'),(ROW('12','deleantero','1.84')),4);
INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW('Ronaldo','31'),(ROW('21','central','1.78')),4);


INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-01',1,2);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-06',1,3);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-11',1,4);

INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-17',2,1);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-22',2,3);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-26',2,4);

INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-31',3,1);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-04-03',3,2);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-03-09',3,4);

INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-04-11',4,1);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-04-16',4,2);
INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES ('2024-04-23',4,3);


