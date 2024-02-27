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
    static final String user = "postgres";
    static final String password = "root";

    //SENTENCIAS SQL
    static private final String SQL_INSERTAR_EQUIPO = "INSERT INTO objetos.equipos (equipo_info) VALUES (ROW(?,?,ROW(?,?)));";
    static private final String SQL_MODIFICAR_EQUIPO = "UPDATE objetos.equipos SET equipo_info = (ROW(?,?,ROW(?,?))) WHERE equipo_id = ?;";
    static private final String SQL_ELIMINAR_EQUIPO = "BEGIN; UPDATE objetos.jugadores SET equipo_id = null WHERE equipo_id = ?; DELETE FROM objetos.partidos WHERE equipo_local = ? OR equipo_visitante = ?;DELETE FROM objetos.equipos WHERE equipo_id = ?;COMMIT;";
    static private final String SQL_INSERTAR_JUGADOR = "INSERT INTO objetos.jugadores (datos_personales,jugador_info,equipo_id) VALUES (ROW(?,?),(ROW(?,?,?)),?);";
    static private final String SQL_MODIFICAR_JUGADOR = "UPDATE objetos.jugadores SET datos_personales = (ROW(?,?)), jugador_info = (ROW(?,?,?)), equipo_id = ? WHERE jugador_id = ?;";
    static private final String SQL_ELIMINAR_JUGADOR = "DELETE FROM objetos.jugadores WHERE jugador_id = ?";
    static private final String SQL_INSERTAR_PARTIDO = "INSERT INTO objetos.partidos (fecha,equipo_local,equipo_visitante) VALUES (?,?,?);";
    static private final String SQL_MODIFICAR_PARTIDO = "UPDATE objetos.partidos SET fecha = ?, equipo_local = ?, equipo_visitante = ? WHERE partido_id = ?";
    static private final String SQL_ELIMINAR_PARTIDO = "DELETE FROM objetos.partidos WHERE partido_id = ?";
    static private final String SQL_INSCRIBIR_JUGADOR_EQUIPO = "UPDATE objetos.jugadores SET equipo_id = ? WHERE jugador_id = ?";
    static private final String SQL_MOSTRAR_INFO_EQUIPO_ID= "SELECT equipo_id, (equipo_info).nombre as Nombre, (equipo_info).ciudad as Ciudad, (equipo_info).entrenador.nombre as NombreE,(equipo_info).entrenador.edad as Edad FROM objetos.equipos WHERE equipo_id = ?;";
    static private final String SQL_LISTAR_EQUIPOS = "SELECT equipo_id, (equipo_info).nombre as Nombre, (equipo_info).ciudad as Ciudad, (equipo_info).entrenador.nombre as NombreE,(equipo_info).entrenador.edad as Edad FROM objetos.equipos";
    static private final String SQL_MOSTRAR_INFO_JUGADOR_ID = "SELECT jugador_id, (datos_personales).nombre as Nombre, (datos_personales).edad as Edad,(jugador_info).dorsal as Dorsal,(jugador_info).posicion as Posicion, (jugador_info).altura as Altura, equipo_id as Equipo FROM objetos.Jugadores WHERE jugador_id = ?";
    static private final String SQL_MOSTRAR_INFO_JUGADOR_NOMBRE = "SELECT jugador_id, (datos_personales).nombre as Nombre, (datos_personales).edad as Edad,(jugador_info).dorsal as Dorsal,(jugador_info).posicion as Posicion, (jugador_info).altura as Altura, equipo_id as Equipo FROM objetos.Jugadores WHERE (datos_personales).nombre = ?";
    static private final String SQL_PARTIDOS_EQUIPO_LOCAL_ID = "SELECT partido_id,fecha, equipo_local,equipo_visitante FROM objetos.partidos WHERE equipo_local = ?";
    static private final String SQL_PARTIDOS_EQUIPO_VISITANTE_ID = "SELECT partido_id,fecha, equipo_local,equipo_visitante FROM objetos.partidos WHERE equipo_visitante = ?";
    static private final String SQL_MOSTRAR_INFO_SEGUN_POSICION = "SELECT jugador_id,(datos_personales).nombre as Nombre,(datos_personales).edad as Edad,(jugador_info).dorsal as Dorsal, (jugador_info).posicion as Posicion,(jugador_info).altura as Altura, equipo_id as Equipo FROM objetos.jugadores WHERE (jugador_info).posicion= ?";
    static private final String SQL_MOSTRAR_INFO_SEGUN_DORSAL = "SELECT jugador_id,(datos_personales).nombre as Nombre,(datos_personales).edad as Edad,(jugador_info).dorsal as Dorsal, (jugador_info).posicion as Posicion,(jugador_info).altura as Altura, equipo_id as Equipo FROM objetos.jugadores WHERE (jugador_info).dorsal= ?";;
    static private final String SQL_MOSTRAR_PARTIDOS_SEGUN_FECHA = "SELECT partido_id,fecha, equipo_local,equipo_visitante FROM objetos.partidos ORDER BY fecha ASC";



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
                        eliminarEquipo(conn);
                        break;
                    case 4:
                        insertarJugador(conn);
                        break;
                    case 5:
                        modificarJugador(conn);
                        break;
                    case 6:
                        eliminarJugador(conn);
                        break;
                    case 7:
                        insertarPartido(conn);
                        break;
                    case 8:
                        modificarPartido(conn);
                        break;
                    case 9:
                        eliminarPartido(conn);
                        break;
                    case 10:
                        inscribirJugadorEquipo(conn);
                        break;
                    case 11:
                        mostrarInfoEquipoId(conn);
                        break;
                    case 12:
                        mostrarInfoEquipos(conn);
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
                        mostrarPartidosVisitante(conn);
                        break;
                    case 17:
                        mostrarJugadoresPosicion(conn);
                        break;
                    case 18:
                        mostrarJugadoresDorsal(conn);
                        break;
                    case 19:
                        mostrarPartidosSegunFecha(conn);
                        break;
                    default:
                        System.out.println("No es una opcion válida!!!!");

                }
            }while (opt != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }

    }

    private static void inscribirJugadorEquipo(Connection conn) {
        try {
            int idJugador = pedirInt("Introduce la id del jugador a inscribir");
            int idEquipo = pedirInt("Introduce la id del equipo al que lo quieres inscribir");
            PreparedStatement ps = conn.prepareStatement(SQL_INSCRIBIR_JUGADOR_EQUIPO);
            ps.setInt(1,idEquipo);
            ps.setInt(2,idJugador);
            int rs = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }

    }

    private static void eliminarPartido(Connection conn) {
        try {
            int idPartido = pedirInt("Introduce la id del partido a eliminar");
            PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR_PARTIDO);
            ps.setInt(1,idPartido);
            int rs = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }

    }


    private static void eliminarJugador(Connection conn) {
        try {
            int idJugador = pedirInt("Introduce la id del jugador a eliminar");
            PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR_JUGADOR);
            ps.setInt(1,idJugador);
            int rs = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }
    }

    private static void eliminarEquipo(Connection conn) {
        try {
            int idEquipo = pedirInt("Introduce la id del equipo a eliminar");
            PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR_EQUIPO);
            ps.setInt(1,idEquipo);
            ps.setInt(2,idEquipo);
            ps.setInt(3,idEquipo);
            ps.setInt(4,idEquipo);
            int rs = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }
    }

    private static void mostrarPartidosSegunFecha(Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement(SQL_MOSTRAR_PARTIDOS_SEGUN_FECHA);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("****************************************************");
                System.out.println("ID del partido: " + rs.getString("partido_id"));
                System.out.println("Fecha: " + rs.getString("fecha"));
                System.out.println("Equipo local: " + rs.getString("equipo_local"));
                System.out.println("Equipo visitante: " + rs.getInt("equipo_visitante"));
                System.out.println("****************************************************");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }


    }

    private static void mostrarJugadoresDorsal(Connection conn) {
        try {
            int dorsal = pedirInt("Introduce el dorsal");
            PreparedStatement ps = conn.prepareStatement(SQL_MOSTRAR_INFO_SEGUN_DORSAL);
            ps.setInt(1,dorsal);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("****************************************************");
                System.out.println("ID del jugador: " + rs.getInt("jugador_id"));
                System.out.println("Nombre del jugador: " + rs.getString("Nombre"));
                System.out.println("Edad: " + rs.getInt("Edad"));
                System.out.println("Dorsal: " + rs.getInt("Dorsal"));
                System.out.println("Posicion: " + rs.getString("Posicion"));
                System.out.println("Altura: " + rs.getString("Altura"));
                System.out.println("ID del equipo: " + rs.getString("Equipo"));
                System.out.println("****************************************************");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }
    }

    private static void mostrarJugadoresPosicion(Connection conn) {
        try {
            String posicion = pedirString("Introduce la posicion");
            PreparedStatement ps = conn.prepareStatement(SQL_MOSTRAR_INFO_SEGUN_POSICION);
            ps.setString(1,posicion);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("****************************************************");
                System.out.println("ID del jugador: " + rs.getInt("jugador_id"));
                System.out.println("Nombre del jugador: " + rs.getString("Nombre"));
                System.out.println("Edad: " + rs.getInt("Edad"));
                System.out.println("Dorsal: " + rs.getInt("Dorsal"));
                System.out.println("Posicion: " + rs.getString("Posicion"));
                System.out.println("Altura: " + rs.getString("Altura"));
                System.out.println("ID del equipo: " + rs.getString("Equipo"));
                System.out.println("****************************************************");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }

    }

    private static void mostrarInfoEquipos(Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_EQUIPOS);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("****************************************************");
                System.out.println("ID del equipo: " + rs.getInt("equipo_id"));
                System.out.println("Nombre del equipo: " + rs.getString("Nombre"));
                System.out.println("Ciudad: " + rs.getString("Ciudad"));
                System.out.println("Nombre del entrenador: " + rs.getString("NombreE"));
                System.out.println("Edad: " + rs.getInt("Edad"));
                System.out.println("****************************************************");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }
    }

    private static void mostrarInfoEquipoId(Connection conn) {
        try {
            int idEquipo = pedirInt("Introduce la id del equipo");
            PreparedStatement ps = conn.prepareStatement(SQL_MOSTRAR_INFO_EQUIPO_ID);
            ps.setInt(1,idEquipo);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("ID del equipo: " + rs.getInt("equipo_id"));
                System.out.println("Nombre del equipo: " + rs.getString("Nombre"));
                System.out.println("Ciudad: " + rs.getString("Ciudad"));
                System.out.println("Nombre del entrenador: " + rs.getString("NombreE"));
                System.out.println("Edad: " + rs.getInt("Edad"));
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }
    }

    private static void mostrarPartidosVisitante(Connection conn) {
        try {
            int idE = pedirInt("Introduce la id del equipo, para ver la información de partidos visitantes");
            PreparedStatement ps = conn.prepareStatement(SQL_PARTIDOS_EQUIPO_VISITANTE_ID);
            ps.setInt(1,idE);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("****************************************************");
                System.out.println("ID del partido: " + rs.getString("partido_id"));
                System.out.println("Fecha: " + rs.getString("fecha"));
                System.out.println("Equipo local: " + rs.getString("equipo_local"));
                System.out.println("Equipo visitante: " + rs.getInt("equipo_visitante"));
                System.out.println("****************************************************");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }

    }


    private static void mostrarPartidosLocal(Connection conn) {
        try {
            int idE = pedirInt("Introduce la id del equipo, para ver la información de partidos locales");
            PreparedStatement ps = conn.prepareStatement(SQL_PARTIDOS_EQUIPO_LOCAL_ID);
            ps.setInt(1,idE);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("****************************************************");

                System.out.println("ID del partido: " + rs.getString("partido_id"));
                System.out.println("Fecha: " + rs.getString("fecha"));
                System.out.println("Equipo local: " + rs.getString("equipo_local"));
                System.out.println("Equipo visitante: " + rs.getInt("equipo_visitante"));
                System.out.println("****************************************************");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }

    }

    private static void mostrarInfoJugadorNombre(Connection conn) {
        try {
            String nombreJug = pedirString("Introduce el nombre del jugador");
            PreparedStatement ps = conn.prepareStatement(SQL_MOSTRAR_INFO_JUGADOR_NOMBRE);
            ps.setString(1,nombreJug);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("****************************************************");
                System.out.println("ID del jugador: " + rs.getInt("jugador_id"));
                System.out.println("Nombre del jugador: " + rs.getString("Nombre"));
                System.out.println("Edad: " + rs.getInt("Edad"));
                System.out.println("Dorsal: " + rs.getInt("Dorsal"));
                System.out.println("Posicion: " + rs.getString("Posicion"));
                System.out.println("Altura: " + rs.getString("Altura"));
                System.out.println("ID del equipo: " + rs.getString("Equipo"));
                System.out.println("****************************************************");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
        }
    }

    private static void mostrarInfoJugadorID(Connection conn) {
        try {
            int idJug = pedirInt("Introduce la id del jugador");
            PreparedStatement ps = conn.prepareStatement(SQL_MOSTRAR_INFO_JUGADOR_ID);
            ps.setInt(1,idJug);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("****************************************************");
                System.out.println("ID del jugador: " + rs.getInt("jugador_id"));
                System.out.println("Nombre del jugador: " + rs.getString("Nombre"));
                System.out.println("Edad: " + rs.getInt("Edad"));
                System.out.println("Dorsal: " + rs.getInt("Dorsal"));
                System.out.println("Posicion: " + rs.getString("Posicion"));
                System.out.println("Altura: " + rs.getString("Altura"));
                System.out.println("ID del equipo: " + rs.getString("Equipo"));
                System.out.println("****************************************************");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
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
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
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
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
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
        }catch (InputMismatchException e){
            System.out.println("ERROR!!!!! El dato introducido no es correcto");
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
        }catch (InputMismatchException e){
            System.out.println("No es una opción valida");
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

