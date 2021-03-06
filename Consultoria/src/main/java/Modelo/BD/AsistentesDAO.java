package Modelo.BD;
import Modelo.UML.AsistentesEntity;
import Modelo.UML.EntrenadoresEntity;
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

    public class AsistentesDAO extends BaseDatos {

    public AsistentesDAO() {    }
    /**
     * CREACION DEL ASISTENTE
     * @param nickname  parámetro de registro.
     * @param nombre    parámetro de registro.
     * @param apellido   parámetro de registro.
     * @param fechaNacimiento parámetro de registro.
     * @param nacionalidad parámetro de registro.
     * @param sueldo parámetro de registro.
     **/

    public void crearAsistente(String nickname, String nombre, String apellido, LocalDate fechaNacimiento,
                               String nacionalidad, Double sueldo, String nicknameEntenador) throws Exception {

        //iniciar transaccion
        transaction.begin();

        // Ejecutar
        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("GEST_ASISTENTES.INSERT_PERSONA");
        // set parameters
        storedProcedure.registerStoredProcedureParameter("P_NICKNAME", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_NOMBRE", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_APELLIDO", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_FECHA_NACIMIENTO", Date.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_NACIONALIDAD", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_SUELDO", Double.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("P_NICKNAME_ENTRENADOR", String.class, ParameterMode.IN);

        storedProcedure.setParameter("P_NICKNAME", nickname);
        storedProcedure.setParameter("P_NOMBRE", nombre);
        storedProcedure.setParameter("P_APELLIDO", apellido);
        storedProcedure.setParameter("P_FECHA_NACIMIENTO", conversionDate(fechaNacimiento));
        storedProcedure.setParameter("P_NACIONALIDAD", nacionalidad);
        storedProcedure.setParameter("P_SUELDO", sueldo);
        storedProcedure.setParameter("P_NICKNAME_ENTRENADOR", nicknameEntenador);
        // execute SP
        storedProcedure.execute();

        // ejecutar las transaciones en la base de datos
        transaction.commit();
    }

        /**
         *
         *CONSULTA DE ASISTENTE
         * @return listaAsistentes
         *
         */
    public List<AsistentesEntity> consultarAsistentes() throws Exception {
        //iniciar transaccion
        transaction.begin();
        List<AsistentesEntity> listaAsistentes;

        // 3. Construir comandos SQL
        TypedQuery<AsistentesEntity > qEventos =
                em.createNamedQuery("AsistentesEntity.todas", AsistentesEntity.class);
        listaAsistentes = qEventos.getResultList();

        // ejecutar las transaciones en la base de datos
        transaction.commit();

        return listaAsistentes;
    }

        /**
         *
         *BORRAR ASISTENTE
         * @param nomAsis
         *
         */

    public void borrarAsistente(String nomAsis) throws Exception{
        //iniciar transaccion
        transaction.begin();

        // 3. Construir comandos SQL
        TypedQuery<AsistentesEntity> qEventos =
                em.createNamedQuery("AsistentesEntity.borrar", AsistentesEntity.class);
        qEventos.setParameter(1, nomAsis.toUpperCase());
        AsistentesEntity consulta = qEventos.getSingleResult();
        if (consulta != null){
            em.remove(consulta);
        }

        // ejecutar las transaciones en la base de datos
        transaction.commit();
    }
}
