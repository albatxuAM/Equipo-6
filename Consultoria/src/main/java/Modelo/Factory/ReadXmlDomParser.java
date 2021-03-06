package Modelo.Factory;

import Controlador.Main;
import Excepciones.Validaciones;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * PARSER XML
 * @author Equipo-6
 */
public class ReadXmlDomParser {

    private static final String JORNADAS = "resources\\resultados_jornadas.xml";
    private static final String CLASIFICACION = "resources\\clasificacion.xml";

    //datos almacenados
    private DatosClasificacionXML DatosClasificacion;
    private DatosJornadasXML DatosJornadas;

    protected DocumentBuilderFactory dbf;

    /**
     *
     * CONTRUCTOR PARSER XML
     *
     **/
    public ReadXmlDomParser() {
        // Instantiate the Factory
        dbf = DocumentBuilderFactory.newInstance();
        DatosClasificacion = new DatosClasificacionXML();
        DatosJornadas = new DatosJornadasXML();
    }

    /**
     *
     * COMPROBAR SI EXISTEN LOS XML O NECESITAN ACTUALIZACION
     *
     **/
    public void checkXML() {
        //ver si los xml existen en el systema
        File jornadas_xml = new File(JORNADAS);
        if (!jornadas_xml.exists()) {
            System.out.println("No existe el archivo " + JORNADAS);
            //generaJornadasXMLs();
        }

        File clasificacion_xml = new File(CLASIFICACION);
        if (!jornadas_xml.exists()) {
            System.out.println("No existe el archivo " + CLASIFICACION);
            //generaClasificacionXMLs();
        }

        //comprobar las fechas de los archivos xml
        ultimaJornadaXML();
        clasificacionXML();
    }

    /**
     *
     * PARSEAR XML DE JORNADAS
     *
     **/
    public void ultimaJornadaXML() {

        try {
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(JORNADAS));

            doc.getDocumentElement().normalize();

            // System.out.println("Root Element : " + doc.getDocumentElement().getNodeName());
            // System.out.println("------");

            // get <fecha_expiracion>
            String expirationDate = doc.getElementsByTagName("fecha_expiracion").item(0).getTextContent();

            LocalDate hoy = LocalDate.now();

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = LocalDate.parse(expirationDate, formatoFecha);

            if(fecha.isAfter(hoy)) {

                // get <jornada>
                NodeList jornadas = doc.getElementsByTagName("jornada");

                for (int j = 0; j < jornadas.getLength(); j++) {

                    Node jornada = jornadas.item(j);

                    if (jornada.getNodeType() == Node.ELEMENT_NODE) {

                        Element j_element = (Element)jornada;

                        // get staff's attribute
                        String num_jornada = j_element.getAttribute("num_jornada");
                        String fecha_jornada = j_element.getAttribute("fecha_jornada");

                        // System.out.println("Element :" + jornada.getNodeName());
                        // System.out.println("num_jornada : " + num_jornada);
                        // System.out.println("fecha_jornada : " + fecha_jornada);

                        DatosJornadasXML.Jornada jornadaDOM = DatosJornadas.addJornada(num_jornada, fecha_jornada);

                        // get <partido>
                        NodeList partidos = j_element.getElementsByTagName("partido");

                        for (int p = 0; p < partidos.getLength(); p++) {

                            Node partido = partidos.item(p);

                            if (partido.getNodeType() == Node.ELEMENT_NODE) {

                                Element p_element = (Element) partido;

                                // get staff's attribute
                                String cod_partido = p_element.getAttribute("cod_partido");
                                String hora_partido = p_element.getAttribute("hora_partido");

                                // get text
                                String resultado = p_element.getElementsByTagName("resultado").item(0).getTextContent();

                                // System.out.println("Current Element :" + partido.getNodeName());
                                // System.out.println("cod_partido : " + cod_partido);
                                // System.out.println("hora_partido : " + hora_partido);
                                // System.out.println("resultado : " + resultado);

                                DatosJornadasXML.Partido partidoDOM =
                                        jornadaDOM.addPartido(Integer.parseInt(cod_partido), hora_partido, resultado);

                                // get <nombre_equipo>
                                NodeList equipos = p_element.getElementsByTagName("nombre_equipo");

                                for (int e = 0; e < equipos.getLength(); e++) {

                                    Node equipo = equipos.item(e);

                                    if (partido.getNodeType() == Node.ELEMENT_NODE) {

                                        Element e_element = (Element) equipo;

                                        String nombre_equipo = e_element.getTextContent();

                                        // System.out.println("nombre_equipo : " + nombre_equipo);

                                        partidoDOM.addEquipo(nombre_equipo);
                                    }
                                }
                            }
                        }
                    }
                }
            }

           // generaJornadasXMLs();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * PARSEAR XML CLASIFICACION
     *
     **/
    public void clasificacionXML() {

        try {
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(CLASIFICACION));

            doc.getDocumentElement().normalize();

            // System.out.println("Root Element : " + doc.getDocumentElement().getNodeName());
            // System.out.println("------");

            // get <fecha_expiracion>
            String expirationDate = doc.getElementsByTagName("fecha_expiracion").item(0).getTextContent();

            LocalDate hoy = LocalDate.now();

            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = LocalDate.parse(expirationDate, formatoFecha);

            if(fecha.isAfter(hoy)) {

                // get <temporada>
                NodeList temporadas = doc.getElementsByTagName("temporada");

                for (int t = 0; t < temporadas.getLength(); t++) {

                    Node temporada = temporadas.item(t);

                    if (temporada.getNodeType() == Node.ELEMENT_NODE) {

                        Element t_element = (Element)temporada;

                        // get staff's attribute
                        String cod_temporada = t_element.getAttribute("cod_temporada");

                        // System.out.println("Element :" + temporada.getNodeName());
                        // System.out.println("cod_temporada : " + cod_temporada);

                        DatosClasificacionXML.Temporada temporadaDOM = DatosClasificacion.addTemporada(cod_temporada);

                        // get <equipo>
                        NodeList equipos = t_element.getElementsByTagName("equipo");

                        for (int e = 0; e < equipos.getLength(); e++) {

                            Node equipo = equipos.item(e);

                            if (equipo.getNodeType() == Node.ELEMENT_NODE) {

                                Element e_element = (Element) equipo;

                                // get staff's attribute
                                String cod_equipo = e_element.getAttribute("cod_equipo");
                                String partidos_ganados = e_element.getAttribute("partidos_ganados");

                                // get text
                                String e_nombre = e_element.getElementsByTagName("nombre").item(0).getTextContent();

                                // System.out.println("Current Element :" + equipo.getNodeName());
                                // System.out.println("nombre : " + e_nombre);

                                DatosClasificacionXML.Equipo equipoDOM = temporadaDOM.addEquipo(e_nombre,
                                            Integer.parseInt(cod_equipo), Integer.parseInt(partidos_ganados));

                                // get <lista_jugadores>
                                NodeList lista_jugadores = e_element.getElementsByTagName("lista_jugadores");

                                Node lista_jugador = lista_jugadores.item(0);

                                if (lista_jugador.getNodeType() == Node.ELEMENT_NODE) {

                                    Element jl_element = (Element) equipo;

                                    NodeList jugadores = jl_element.getElementsByTagName("jugador");

                                    for (int j = 0; j < jugadores.getLength(); j++) {

                                        Node jugador = jugadores.item(j);

                                        if (jugador.getNodeType() == Node.ELEMENT_NODE) {

                                            Element j_element = (Element) jugador;

                                            String nickname = j_element.getElementsByTagName("nickname").item(0).getTextContent();
                                            String rol = j_element.getElementsByTagName("rol").item(0).getTextContent();

                                            // System.out.println("Current Element :" + jugador.getNodeName());
                                            // System.out.println("nickname : " + nickname);
                                            // System.out.println("rol : " + rol);

                                            equipoDOM.addJugador(nickname, rol);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // generaClasificacionXML();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }


    /**
     *
     * GETTER DATOS JORNDAS
     *
     * @return datos Jornadas
     *
     **/
    public String getDatosJornadas() {

        StringBuilder datos = new StringBuilder();

        for (DatosJornadasXML.Jornada jornada : DatosJornadas.getJornadas()) {
            datos.append("JORNADA ").append(jornada.getNum_jornada()).append("\n");

            for (DatosJornadasXML.Partido partido : jornada.getPartidos()) {
                datos.append("        PARTIDO ").append(partido.getCodPartido()).append("\t")
                        .append(partido.getHoraPartido()).append("\n");

                ArrayList<String> equipos = partido.getEquipos();

                datos.append("\t").append(equipos.get(0)).append(" \t ").append(partido.getResultado()).append(" \t ").append(equipos.get(1)).append(" \n ");
            }

        }

        return datos.toString();
    }

    /**
     *
     * GETTER DATOS CLASIFICACION
     *
     * @return datos clasificacion
     *
     **/
    public String getClasificacion() {
        StringBuilder datos = new StringBuilder();

        for (DatosClasificacionXML.Temporada temporada : DatosClasificacion.getTemporadas()) {

            datos.append("TEMPORADA ").append(temporada.getCod_temporada()).append("\n");

            for (DatosClasificacionXML.Equipo equipo : temporada.getEquipos()) {
                //EQUIPOS
                datos.append("        ").append(equipo.getNombre()).append("\t")
                        .append(equipo.getPartidos_ganados()).append("\n");

                for (DatosClasificacionXML.Jugador jugador : equipo.getLista_jugadores()) {

                    datos.append("\t").append(jugador.getNickname()).append(" \t ").append(jugador.getRol()).append(" \n ");
                }
            }

        }

        return datos.toString();
    }


    /**
     *
     * GETTER DATOS DE JORNADAS
     *
     * @return DatosClasificacion
     *
     **/
    public DatosJornadasXML getDatosJornadasXML() {

       return DatosJornadas;
    }

    /**
     *
     * GETTER DATOS DE LA CLASIFICACION
     *
     * @return DatosClasificacion
     *
     **/
    public DatosClasificacionXML getDatosClasificacionXML() {

        return DatosClasificacion;
    }

    /**
     *
     * GENERAR CLASIFICACION XML DESDE EL PROCEDIMIENTO DE BBDD Y GUARDAR EN EL ARCHIVO
     *
     **/
    public void generaClasificacionXMLs() {

        try {
            File tempfile = new File("resources", "clasificacion.xml");

            FileWriter fw = new FileWriter( tempfile.getAbsoluteFile( ) );
            BufferedWriter bw = new BufferedWriter( fw );
            bw.write( Main.getDatosClasificacionXML() );

            bw.close();
            fw.close();

        } catch (Exception e) {
            Validaciones.mostrarError(e.getMessage());
        }
    }

    /**
     *
     * GENERAR JORNADAS XML DESDE EL PROCEDIMIENTO DE BBDD Y GUARDAR EN EL ARCHIVO
     *
     **/
    public void generaJornadasXMLs() {

        try {
            File tempfile = new File("resources", "resultados_jornadas.xml");

            FileWriter fw = new FileWriter( tempfile.getAbsoluteFile( ) );
            BufferedWriter bw = new BufferedWriter( fw );
            bw.write( Main.getDatosJornadasXML() );

            bw.close();
            fw.close();

        } catch (Exception e) {
            Validaciones.mostrarError(e.getMessage());
        }
    }

}
