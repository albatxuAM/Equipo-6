-- GESTIONAR_ASISTENTES_TEST
/* 
 Fecha: 03/05/2022
 Descripcion:  Testeo paquete gestion de asistentes
 */
 
SET SERVEROUTPUT ON;

/*
-- INSERTAR EL ENTRENADOR PRIMERO
DECLARE
    P_NICKNAME VARCHAR2(15) := 'Slayo_15';
    P_NOMBRE VARCHAR2(15) := 'I�aki';
    P_APELLIDO VARCHAR2(15) := 'Birel';
    P_FECHA_NACIMIENTO DATE := TO_DATE('09/07/1997','DD/MM/YYYY');
    P_NACIONALIDAD VARCHAR2(20) := 'Espa�ola';
    P_SUELDO  NUMBER(6) := 1500;
BEGIN
    GEST_ENTRENADORES.INSERT_PERSONA(P_NICKNAME, P_NOMBRE, P_APELLIDO, 
            P_FECHA_NACIMIENTO, P_NACIONALIDAD, P_SUELDO); 
END;
*/

DECLARE
    P_NICKNAME VARCHAR2(15) := 'Destepo';
    P_NOMBRE VARCHAR2(15) := 'I�aki';
    P_APELLIDO VARCHAR2(15) := 'Birel';
    P_FECHA_NACIMIENTO DATE := TO_DATE('09/07/1997','DD/MM/YYYY');
    P_NACIONALIDAD VARCHAR2(20) := 'Espa�ola';
    P_SUELDO  NUMBER(6) := 1500;
    P_NICKNAME_ENTRENADOR VARCHAR2(15) := 'Slayo_15';
BEGIN
    GEST_ASISTENTES.INSERT_PERSONA(P_NICKNAME, P_NOMBRE, P_APELLIDO, 
            P_FECHA_NACIMIENTO, P_NACIONALIDAD, P_SUELDO, P_NICKNAME_ENTRENADOR); 
END;

 -- genera error de nickname duplicado
DECLARE
    P_NICKNAME VARCHAR2(15) := 'Destepo';
    P_NOMBRE VARCHAR2(15) := 'I�aki';
    P_APELLIDO VARCHAR2(15) := 'Birel';
    P_FECHA_NACIMIENTO DATE := TO_DATE('09/07/1997','DD/MM/YYYY');
    P_NACIONALIDAD VARCHAR2(20) := 'Espa�ola';
    P_SUELDO  NUMBER(6) := 1500;
    P_NICKNAME_ENTRENADOR VARCHAR2(15) := 'Slayo_15';
BEGIN
    GEST_ASISTENTES.INSERT_PERSONA(P_NICKNAME, P_NOMBRE, P_APELLIDO, 
            P_FECHA_NACIMIENTO, P_NACIONALIDAD, P_SUELDO, P_NICKNAME_ENTRENADOR); 
END;

SELECT * FROM PERSONA;
SELECT * FROM ENTRENADORES;
SELECT * FROM ASISTENTES;



--ROLLBACK;