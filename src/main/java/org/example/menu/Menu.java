package org.example.menu;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    static final String dbURL = "jdbc:postgresql://localhost:5432/futbol";
    static final String user = "root";
    static final String password = "root";

    //SENTENCIAS SQL
    static private final String SQL_INSERTAR_EQUIPO = "INSERT INTO objetos.equipos (equipo_info) VALUES (ROW(?,?,ROW(?,?)));";
    static private final String SQL_MODIFICAR_EQUIPO = "UPDATE objetos.equipos SET equipo_info = (ROW(?,?,ROW(?,?))) WHERE equipo_id = ?;";
    static private final String SQL_ELIMINAR_EQUIPO = "";
    static private final String SQL_INSERTAR_JUGADOR = "INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW(?,?),(ROW(?,?,?)),?);";
    static private final String SQL_MODIFICAR_JUGADOR = "UPDATE objetos.jugadores SET datos_personales = (ROW(?,?)), jugador_info = (ROW(?,?,?)), equipo_id = ? WHERE jugador_id = ?;";
    static private final String SQL_ELIMINAR_JUGADOR = "";
    static private final String SQL_INSERTAR_PARTIDO = "INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES (?,?,?);";
    static private final String SQL_MODIFICAR_PARTIDO = "UPDATE objetos.partidos SET fecha = ?, equipo_local = ?, equipo_visitante = ? WHERE partido_id = ?";
    static private final String SQL_ELIMINAR_PARTIDO = "";
    static private final String SQL_INSCRIBIR_JUGADOR_EQUIPO = "";
    static private final String SQL_LISTAR_EQUIPO_ID= "";
    static private final String SQL_LISTAR_EQUIPOS = "";
    static private final String SQL_MOSTRAR_INFO_JUGADOR_ID = "SELECT jugador_id, (datos_personales).nombre as Nombre, (datos_personales).edad as Edad,(jugador_info).dorsal as Dorsal,(jugador_info).posicion as Posicion, (jugador_info).altura as Altura, equipo_id as Equipo FROM objetos.Jugadores WHERE jugador_id = ?";
    static private final String SQL_MOSTRAR_INFO_JUGADOR_NOMBRE = "SELECT jugador_id, (datos_personales).nombre as Nombre, (datos_personales).edad as Edad,(jugador_info).dorsal as Dorsal,(jugador_info).posicion as Posicion, (jugador_info).altura as Altura, equipo_id as Equipo FROM objetos.Jugadores WHERE (datos_personales).nombre = ?";
    static private final String SQL_PARTIDOS_EQUIPO_LOCAL_ID = "SELECT partido_id,fecha, equipo_local,equipo_visitante FROM objetos.partidos WHERE equipo_local = ?";
    static private final String SQL_PARTIDOS_EQUIPO_VISITANTE_ID = "";
    static private final String SQL_MOSTRAR_INFO_SEGUN_POSICION = "";
    static private final String SQL_MOSTRAR_INFO_SEGUN_DORSAL = "";
    static private final String SQL_MOSTRAR_PARTIDOS_SEGUN_FECHA = "";



    public static void dialog(){
        try(Connection conn = DriverManager.getConnection(dbURL,user,password)) {
            int opt = 10000;
            do {
                System.out.println("1.- Insertar un Equipo");
                System.out.println("2.- Modificar un Equipo");
                System.out.println("3.- Eliminar un Equipo");
                System.out.println("4.- Insertar un Jugador");
                System.out.println("5.- Modificar un Jugador");
                System.out.println("6.- Eliminar un Jugador");
                System.out.println("7.- Insertar un Partido");
                System.out.println("8.- Modificar un Partido");
                System.out.println("9.- Eliminar un Partido");
                System.out.println("10.- Incribir un Jugador  a un Equipo");
                System.out.println("11.- Listar toda la información de un Equipo buscándolo por id.");
                System.out.println("12.- Listar toda la información de todos los Equipos.");
                System.out.println("13.- Listar la información de un Jugador buscándolo por id.");
                System.out.println("14.- Listar la información de un Jugador buscándolo por nombre.");
                System.out.println("15.- Buscar partidos en los que un determinado equipo jugara como local.");
                System.out.println("16.- Buscar partidos en los que un determinado equipo jugara como visitante.");
                System.out.println("17.- Obtener toda la información de los jugadores que jueguen en una determinada posición.");
                System.out.println("18.- Obtener toda la información de los jugadores según su dorsal.");
                System.out.println("19.- Obtener todos los partidos según la fecha.");

                opt = pedirInt("Introduce una opción: ");

                switch (opt){
                    case 1:
                        insertarEquipo(conn);
                        break;
                    case 2:
                        modificarEquipo(conn);
                        break;
                    case 3:
                        break;
                    case 4:
                        insertarJugador(conn);
                        break;
                    case 5:
                        modificarJugador(conn);
                        break;
                    case 6:

                        break;
                    case 7:
                        insertarPartido(conn);
                        break;
                    case 8:
                        modificarPartido(conn);
                        break;
                    case 9:

                        break;
                    case 10:

                        break;
                    case 11:

                        break;
                    case 12:

                        break;
                    case 13:
                        mostrarInfoJugadorID(conn);
                        break;
                    case 14:
                        mostrarInfoJugadorNombre(conn);
                        break;
                    case 15:
                        mostrarPartidosLocal(conn);
                        break;
                    case 16:

                        break;
                    case 17:
                        break;
                    case 18:

                        break;
                    case 19:

                        break;

                }
            }while (opt != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void mostrarPartidosLocal(Connection conn) {
        try {
            int idE = pedirInt("Introduce la id del equipo, para ver la información de partidos locales");
            PreparedStatement ps = conn.prepareStatement(SQL_PARTIDOS_EQUIPO_LOCAL_ID);
            ps.setInt(1,idE);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("ID del partido: " + rs.getString("partido_id"));
                System.out.println("Fecha: " + rs.getString("fecha"));
                System.out.println("Equipo local: " + rs.getString("equipo_local"));
                System.out.println("Equipo visitante: " + rs.getInt("equipo_visitante"));
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void mostrarInfoJugadorNombre(Connection conn) {
        try {
            String nombreJug = pedirString("Introduce el nombre del jugador");
            PreparedStatement ps = conn.prepareStatement(SQL_MOSTRAR_INFO_JUGADOR_NOMBRE);
            ps.setString(1,nombreJug);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("ID del jugador: " + rs.getInt("jugador_id"));
                System.out.println("Nombre del jugador: " + rs.getString("Nombre"));
                System.out.println("Edad: " + rs.getInt("Edad"));
                System.out.println("Dorsal: " + rs.getInt("Dorsal"));
                System.out.println("Posicion: " + rs.getString("Posicion"));
                System.out.println("Altura: " + rs.getString("Altura"));
                System.out.println("ID del equipo: " + rs.getString("Equipo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void mostrarInfoJugadorID(Connection conn) {
        try {
            int idJug = pedirInt("Introduce la id del jugador");
            PreparedStatement ps = conn.prepareStatement(SQL_MOSTRAR_INFO_JUGADOR_ID);
            ps.setInt(1,idJug);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("ID del jugador: " + rs.getInt("jugador_id"));
                System.out.println("Nombre del jugador: " + rs.getString("Nombre"));
                System.out.println("Edad: " + rs.getInt("Edad"));
                System.out.println("Dorsal: " + rs.getInt("Dorsal"));
                System.out.println("Posicion: " + rs.getString("Posicion"));
                System.out.println("Altura: " + rs.getString("Altura"));
                System.out.println("ID del equipo: " + rs.getString("Equipo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void modificarPartido(Connection conn) {
        try {
            int idP = pedirInt("Introduce la id del partido a modificar");
            String fecha = pedirString("Introduce la fecha del partido");
            int idL = pedirInt("Introduce la id del equipo local");
            int idV = pedirInt("Introduce la id del equipo visitante");

            //Formateador de fechas
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(fecha,formatter);

            PreparedStatement ps = conn.prepareStatement(SQL_MODIFICAR_PARTIDO);
            ps.setDate(1, Date.valueOf(date));
            ps.setInt(2,idL);
            ps.setInt(3,idV);
            ps.setInt(4,idP);

            int insert = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("Dato introducido es incorrecto");
        }

    }

    private static void modificarJugador(Connection conn) {
        try {
            int idJug = pedirInt("Introduce la id del jugador");
            String nombre = pedirString("Introduce el nombre del jugador");
            int edad = pedirInt("Introduce la edad del jugador");
            int dorsal = pedirInt("Introduce el dorsal del jugador");
            String posicion = pedirString("Introduce la posicion del jugador");
            BigDecimal altura = pedirDecimal("Introduce la altura del jugador");
            int idEqui = pedirInt("Introduce la id del equipo");

            PreparedStatement ps = conn.prepareStatement(SQL_MODIFICAR_JUGADOR);
            ps.setString(1,nombre);
            ps.setInt(2,edad);
            ps.setInt(3,dorsal);
            ps.setString(4,posicion);
            ps.setBigDecimal(5,altura);
            ps.setInt(6,idEqui);
            ps.setInt(7,idJug);

            int insert = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void modificarEquipo(Connection conn) {
        try {
            int idEquipo = pedirInt("Introduce la id del equipo");
            String nombre = pedirString("Introduce el nombre del equipo");
            String ciudad = pedirString("Introduce la ciudad del equipo");
            String nombreE = pedirString("Introduce el nombre del entrenador");
            int edad = pedirInt("Introduce la edad del entrenador");

            PreparedStatement ps = conn.prepareStatement(SQL_MODIFICAR_EQUIPO);
            ps.setString(1,nombre);
            ps.setString(2,ciudad);
            ps.setString(3,nombreE);
            ps.setInt(4,edad);
            ps.setInt(5,idEquipo);

            int insert = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertarPartido(Connection conn) {
        try {
            String fecha = pedirString("Introduce la fecha del partido");
            int idL = pedirInt("Introduce la id del equipo local");
            int idV = pedirInt("Introduce la id del equipo visitante");

            //Formateador de fechas
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(fecha,formatter);

            PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_PARTIDO);
            ps.setDate(1, Date.valueOf(date));
            ps.setInt(2,idL);
            ps.setInt(3,idV);

            int insert = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("Dato introducido es incorrecto");
        }

    }

    private static void insertarEquipo(Connection conn) {
        try {
            String nombre = pedirString("Introduce el nombre del equipo");
            String ciudad = pedirString("Introduce la ciudad del equipo");
            String nombreE = pedirString("Introduce el nombre del entrenador");
            int edad = pedirInt("Introduce la edad del entrenador");
            PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_EQUIPO);
            ps.setString(1,nombre);
            ps.setString(2,ciudad);
            ps.setString(3,nombreE);
            ps.setInt(4,edad);

            int insert = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void insertarJugador(Connection conn) {
        try {
            String nombre = pedirString("Introduce el nombre del jugador");
            int edad = pedirInt("Introduce la edad del jugador");
            int dorsal = pedirInt("Introduce el dorsal del jugador");
            String posicion = pedirString("Introduce la posicion del jugador");
            BigDecimal altura = pedirDecimal("Introduce la altura del jugador");
            int idEqui = pedirInt("Introduce la id del equipo");

            PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR_JUGADOR);
            ps.setString(1,nombre);
            ps.setInt(2,edad);
            ps.setInt(3,dorsal);
            ps.setString(4,posicion);
            ps.setBigDecimal(5,altura);
            ps.setInt(6,idEqui);

            int insert = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private static String pedirString(String mensaje) {
        Scanner sc = new Scanner(System.in);
        System.out.println(mensaje);
        return sc.nextLine();
    }

    private static int pedirInt(String mensaje) {
        Scanner sc = new Scanner(System.in);
        System.out.println(mensaje);
        return sc.nextInt();
    }

    private static BigDecimal pedirDecimal(String mensaje) {
        Scanner sc = new Scanner(System.in);
        System.out.println(mensaje);
        return sc.nextBigDecimal();
    }
    }

