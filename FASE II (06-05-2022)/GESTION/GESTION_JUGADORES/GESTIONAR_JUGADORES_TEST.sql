-- GESTIONAR_JUGADORES_TEST
/* 
 Fecha: 03/05/2022
 Descripcion:  Testeo paquete gestion de jugadores
 */
 
SET SERVEROUTPUT ON;

DECLARE
    P_NICKNAME VARCHAR2(15) := 'The_Core';
    P_ROL VARCHAR2(15)  := 'TOP';
    P_NOMBRE VARCHAR2(15) := 'DARCY';
    P_APELLIDO VARCHAR2(15) := 'Wuenz';
    P_FECHA_NACIMIENTO DATE := TO_DATE('14/5/2001');
    P_NACIONALIDAD VARCHAR2(20) := 'Taiwanesa';
    P_SUELDO  NUMBER(6) := 2000;
BEGIN
    GEST_JUGADOR.INSERT_PERSONA(P_NICKNAME, P_ROL, P_NOMBRE, P_APELLIDO, 
            P_FECHA_NACIMIENTO, P_NACIONALIDAD, P_SUELDO); 
END;

 -- genera error de nickname duplicado
DECLARE
    P_NICKNAME VARCHAR2(15) := 'The_Core';
    P_ROL VARCHAR2(15) := 'TOP';
    P_NOMBRE VARCHAR2(15) := 'DARCY';
    P_APELLIDO VARCHAR2(15) := 'Wuenz';
    P_FECHA_NACIMIENTO DATE := TO_DATE('14/5/2001');
    P_NACIONALIDAD VARCHAR2(20) := 'Taiwanesa';
    P_SUELDO  NUMBER(6) := 2000;
BEGIN
    GEST_JUGADOR.INSERT_PERSONA(P_NICKNAME, P_ROL, P_NOMBRE, P_APELLIDO, 
            P_FECHA_NACIMIENTO, P_NACIONALIDAD, P_SUELDO);
END;

SELECT * FROM PERSONA;
SELECT * FROM JUGADORES;


--ROLLBACK;