package Modelo.BD;
import Modelo.UML.JugadoresEntity;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

        /**
         *
         * @author EQUIPO-6
         */


public class JugadoresDAO extends BaseDatos {

    public JugadoresDAO() {    }

            /**
             * CREACION DEL JUGADOR
             * @param nickname  parámetro de registro.
             * @param rol    parámetro de registro.
             * @param nombre   parámetro de registro.
             * @param apellido parámetro de registro.
             * @param fechaNacimiento parámetro de registro.
             *
             **/

    public void crearJugador(String nickname, String rol, String nombre, String apellido, LocalDate fechaNacimiento,
                                String nacionalidad, Double sueldo) throws Exception {

        //iniciar transaccion
        transaction.begin();

        // Ejecutar
        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("GEST_JUGADOR.INSERT_PERSONA");
        // set parameters
        storedProcedure.registerStoredProcedureParameter("P_NICKNAME", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_ROL", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_NOMBRE", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_APELLIDO", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_FECHA_NACIMIENTO", Date.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_NACIONALIDAD", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_SUELDO", Double.class, ParameterMode.IN);


        storedProcedure.setParameter("P_NICKNAME", nickname);
        storedProcedure.setParameter("P_ROL", rol);
        storedProcedure.setParameter("P_NOMBRE", nombre);
        storedProcedure.setParameter("P_APELLIDO", apellido);
        storedProcedure.setParameter("P_FECHA_NACIMIENTO", conversionDate(fechaNacimiento));
        storedProcedure.setParameter("P_NACIONALIDAD", nacionalidad);
        storedProcedure.setParameter("P_SUELDO", sueldo);
        // execute SP
        storedProcedure.execute();

        // ejecutar las transaciones en la base de datos
        transaction.commit();
    }

            /**
             *
             *CONSULTA DE JUGADORES
             * @return listaJugadores
             *
             */


    public List<JugadoresEntity> consultarJugadores() throws Exception {
        //iniciar transaccion
        transaction.begin();
        List<JugadoresEntity> listaJugadores;

        // 3. Construir comandos SQL
        TypedQuery<JugadoresEntity > qEventos =
                em.createNamedQuery("JugadoresEntity.todas", JugadoresEntity.class);
        listaJugadores = qEventos.getResultList();

        // ejecutar las transaciones en la base de datos
        transaction.commit();

        return listaJugadores;
    }

            /**
             *
             *CONSULTA DE TOPLANERS
             * @return listaToplaners
             *
             */


            public List<JugadoresEntity> consultarToplaners() throws Exception {
        //iniciar transaccion
        transaction.begin();
        List<JugadoresEntity> listaToplaners;

        // 3. Construir comandos SQL
        TypedQuery<JugadoresEntity > qEventos =
                em.createNamedQuery("JugadoresEntity.TOP", JugadoresEntity.class);
        listaToplaners = qEventos.getResultList();

        // ejecutar las transaciones en la base de datos
        transaction.commit();

        return listaToplaners;
    }

            /**
             *
             *CONSULTA DE JUNGLERS
             * @return listaJunglers
             *
             */

    public List<JugadoresEntity> consultarJunglers() throws Exception {
        //iniciar transaccion
        transaction.begin();
        List<JugadoresEntity> listaJunglers;

        // 3. Construir comandos SQL
        TypedQuery<JugadoresEntity > qEventos =
                em.createNamedQuery("JugadoresEntity.JGL", JugadoresEntity.class);
        listaJunglers = qEventos.getResultList();

        // ejecutar las transaciones en la base de datos
        transaction.commit();

        return listaJunglers;
    }
            /**
             *
             *CONSULTA DE MIDLANERS
             * @return listaMidlaners
             *
             */


    public List<JugadoresEntity> consultarMidlaners() throws Exception {
        //iniciar transaccion
        transaction.begin();
        List<JugadoresEntity> listaMidlaners;

        // 3. Construir comandos SQL
        TypedQuery<JugadoresEntity > qEventos =
                em.createNamedQuery("JugadoresEntity.MID", JugadoresEntity.class);
        listaMidlaners = qEventos.getResultList();

        // ejecutar las transaciones en la base de datos
        transaction.commit();

        return listaMidlaners;
    }
            /**
             *
             *CONSULTA DE AD CARRYS
             * @return listaADCarrys
             *
             */


            public List<JugadoresEntity> consultarADCarrys() throws Exception {
        //iniciar transaccion
        transaction.begin();
        List<JugadoresEntity> listaADCarrys;

        // 3. Construir comandos SQL
        TypedQuery<JugadoresEntity > qEventos =
                em.createNamedQuery("JugadoresEntity.ADC", JugadoresEntity.class);
        listaADCarrys = qEventos.getResultList();

        // ejecutar las transaciones en la base de datos
        transaction.commit();

        return listaADCarrys;
    }

            /**
             *
             *CONSULTA DE SUPPORTS
             * @return listaSupports
             *
             */

    public List<JugadoresEntity> consultarSupports() throws Exception {
        //iniciar transaccion
        transaction.begin();
        List<JugadoresEntity> listaSupports;

        // 3. Construir comandos SQL
        TypedQuery<JugadoresEntity > qEventos =
                em.createNamedQuery("JugadoresEntity.SUPP", JugadoresEntity.class);
        listaSupports = qEventos.getResultList();

        // ejecutar las transaciones en la base de datos
        transaction.commit();

        return listaSupports;
    }


            /**
             *
             *BORRAR JUGADOR
             * @param jugadorBorrar
             *
             */

    public void borrarJugador(String jugadorBorrar) throws Exception {
        //iniciar transaccion
        transaction.begin();

        // 3. Construir comandos SQL
        TypedQuery<JugadoresEntity> qEventos =
                 em.createNamedQuery("JugadoresEntity.borrar", JugadoresEntity.class);
        qEventos.setParameter(1, jugadorBorrar.toUpperCase());
        JugadoresEntity consulta = qEventos.getSingleResult();
        if (consulta != null){
            em.remove(consulta);
        }

        // ejecutar las transaciones en la base de datos
        transaction.commit();
    }
}
