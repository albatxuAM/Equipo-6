-- GESTIONAR_PARTIDOS_TEST
/* 
 Fecha: 03/05/2022
 Descripcion:  Testeo paquete gestion de jornadas
 */
 
SET SERVEROUTPUT ON;

DECLARE
    P_COD_PARTIDO NUMBER(2) := 01;
    P_RESULTADO VARCHAR2(3) := '3-2';
BEGIN
    GEST_PARTIDOS.INSERT_RESULTADO(P_COD_PARTIDO, P_RESULTADO); 
END;


 -- genera error
DECLARE
    P_COD_PARTIDO NUMBER(2) := 05;
    P_RESULTADO VARCHAR2(3) := '3-2';
BEGIN
    GEST_PARTIDOS.INSERT_RESULTADO(P_COD_PARTIDO, P_RESULTADO); 
END;


--ROLLBACK;