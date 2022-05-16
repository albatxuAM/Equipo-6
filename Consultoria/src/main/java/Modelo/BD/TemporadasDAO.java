package Modelo.BD;

import javax.persistence.*;

public class TemporadasDAO extends BaseDatos{

    public TemporadasDAO() {  }

    public void crearTemporada() {
        //iniciar transaccion
        transaction.begin();

        // Ejecutar package
        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("GEST_TEMPORADA.INSERT_TEMPORADA");
        // execute SP
        storedProcedure.execute();

        // ejecutar las transaciones en la base de datos
        transaction.commit();
    }
}