DROP TABLE ALMACEN_XML CASCADE CONSTRAINTS;

DROP TABLE ROL CASCADE CONSTRAINTS;
DROP TABLE USUARIOS CASCADE CONSTRAINTS;

DROP TABLE ASISTE CASCADE CONSTRAINTS;
DROP TABLE ENTRENA CASCADE CONSTRAINTS;
DROP TABLE JUGAR_PARA CASCADE CONSTRAINTS;
DROP TABLE TEMPORADAS CASCADE CONSTRAINTS;

DROP TABLE ASISTENTES CASCADE CONSTRAINTS;
DROP TABLE ENTRENADORES CASCADE CONSTRAINTS;
DROP TABLE JUGADORES CASCADE CONSTRAINTS;
DROP TABLE PERSONAS CASCADE CONSTRAINTS;

DROP TABLE PARTICIPA CASCADE CONSTRAINTS;
DROP TABLE EQUIPOS CASCADE CONSTRAINTS;
DROP TABLE PARTIDOS CASCADE CONSTRAINTS;
DROP TABLE JORNADAS CASCADE CONSTRAINTS;

/******CREACION DE TABLAS*****/

CREATE TABLE TEMPORADAS(
    COD_TEMPORADA NUMBER(4) GENERATED ALWAYS AS IDENTITY
        (START WITH 1 INCREMENT BY 1),
    ABIERTA CHAR(1),
    CONSTRAINT TEMP_COD_PK PRIMARY KEY(COD_TEMPORADA),
    CONSTRAINT TEMP_OPEN_CK CHECK(UPPER(ABIERTA) IN ('S','N'))
);

CREATE TABLE JORNADAS (
    COD_JORNADA NUMBER(2) GENERATED ALWAYS AS IDENTITY
        (START WITH 1 INCREMENT BY 1),
    COD_TEMPORADA NUMBER(2),
    FECHA_JORNADA DATE,
    CONSTRAINT JOR_COD_PK PRIMARY KEY(COD_JORNADA),
     CONSTRAINT JOR_TEMP_PK FOREIGN KEY(COD_TEMPORADA) 
        REFERENCES TEMPORADAS(COD_TEMPORADA)
);

CREATE TABLE PARTIDOS (
    COD_PARTIDO NUMBER(2) GENERATED ALWAYS AS IDENTITY
        (START WITH 1 INCREMENT BY 1),
    HORA_PARTIDO VARCHAR2(30),
    RESULTADO VARCHAR2(3) NULL,
    COD_JORNADA NUMBER(2),
    CONSTRAINT PART_JORD_FK FOREIGN KEY(COD_JORNADA) 
        REFERENCES JORNADAS(COD_JORNADA),
    CONSTRAINT PART_COD_PK PRIMARY KEY(COD_PARTIDO)
);

CREATE TABLE EQUIPOS (
    COD_EQUIPO NUMBER (2) GENERATED ALWAYS AS IDENTITY
        (START WITH 1 INCREMENT BY 1),
    COD_PARTIDO NUMBER (4),
    NOMBRE VARCHAR2 (30),
    FECHA_NACIMIENTO DATE,
    CIUDAD VARCHAR2 (30),
    ESPONSOR VARCHAR2 (25) NOT NULL,
    NOMBRE_DUENIO VARCHAR2 (30),  
    CONSTRAINT EQUI_CODE_PK PRIMARY KEY (COD_EQUIPO),
    CONSTRAINT EQUI_CODP_FK FOREIGN KEY (COD_PARTIDO)
        REFERENCES PARTIDOS(COD_PARTIDO)
); 

CREATE TABLE PARTICIPA (
    COD_EQUIPO1 NUMBER (2),
    COD_EQUIPO2 NUMBER (2),
    COD_PARTIDO NUMBER(2),
    CONSTRAINT PARTC_COD_PK PRIMARY KEY (COD_EQUIPO1, COD_EQUIPO2, COD_PARTIDO),
    CONSTRAINT PARTC_EQUI1_FK FOREIGN KEY (COD_EQUIPO1)
        REFERENCES EQUIPOS(COD_EQUIPO),
    CONSTRAINT PARTC_EQUI2_FK FOREIGN KEY (COD_EQUIPO2)
        REFERENCES EQUIPOS(COD_EQUIPO),
    CONSTRAINT PARTC_PART_FK FOREIGN KEY (COD_PARTIDO)
        REFERENCES PARTIDOS(COD_PARTIDO)
); 

CREATE TABLE PERSONAS(
    COD_PERSONA NUMBER(2) GENERATED ALWAYS AS IDENTITY
        (START WITH 1 INCREMENT BY 1),
	NOMBRE VARCHAR2(15),
	APELLIDO VARCHAR2(15),
	SUELDO NUMBER(6),
	FECHA_NACIMIENTO DATE,
	NACIONALIDAD VARCHAR2(20),
    NICKNAME VARCHAR2(15),
    CONSTRAINT PERS_COD_PK PRIMARY KEY(COD_PERSONA),
    CONSTRAINT P_SUELDO_CK CHECK (SUELDO >= 950)
);

CREATE TABLE JUGADORES(
    COD_JUGADOR NUMBER(2),
	ROL VARCHAR2(15),
    CONSTRAINT JUG_COD_PK PRIMARY KEY(COD_JUGADOR),
    CONSTRAINT JUG_PERS_FK FOREIGN KEY(COD_JUGADOR) REFERENCES 
        PERSONAS (COD_PERSONA),
	CONSTRAINT JUG_ROL_CK CHECK(UPPER(ROL) IN ('TOP', 'JGL', 'MID', 'ADC', 'SUPP'))
);

CREATE TABLE ENTRENADORES(
    COD_ENTRENADOR NUMBER(2),
    CONSTRAINT ENTRE_COD_PK PRIMARY KEY(COD_ENTRENADOR),
    CONSTRAINT ENTRE_PERS_FK FOREIGN KEY(COD_ENTRENADOR) REFERENCES 
        PERSONAS(COD_PERSONA)
);

CREATE TABLE ASISTENTES (
    COD_ASISTENTE NUMBER(2),
    COD_ENTRE_ASOCIADO NUMBER(2),
    CONSTRAINT ASIS_COD_PK PRIMARY KEY(COD_ASISTENTE),
    CONSTRAINT ASIS_PERS_FK FOREIGN KEY(COD_ASISTENTE) REFERENCES 
        PERSONAS(COD_PERSONA),
    CONSTRAINT ASIS_ENTRE_FK FOREIGN KEY(COD_ENTRE_ASOCIADO) REFERENCES
        ENTRENADORES(COD_ENTRENADOR)
);
    
CREATE TABLE JUGAR_PARA( 
    COD_CONTRATO NUMBER(2) GENERATED ALWAYS AS IDENTITY
        (START WITH 1 INCREMENT BY 1),
    COD_JUGADOR NUMBER(2),
    COD_EQUIPO NUMBER(2),
    FECHA_FIN DATE,
    FECHA_INICIO DATE, 
    CONSTRAINT JP_COD_PK PRIMARY KEY(COD_CONTRATO),
    CONSTRAINT JP_JUG_FK FOREIGN KEY(COD_JUGADOR) REFERENCES JUGADORES(COD_JUGADOR),
    CONSTRAINT JP_EQUIPO_FK FOREIGN KEY(COD_EQUIPO) REFERENCES EQUIPOS(COD_EQUIPO)
); 
    
CREATE TABLE ENTRENA(
    COD_CONTRATO NUMBER(2) GENERATED ALWAYS AS IDENTITY
        (START WITH 1 INCREMENT BY 1),
    COD_ENTRENADOR NUMBER(2),
    COD_EQUIPO NUMBER(2),
    FECHA_FIN DATE,
    FECHA_INICIO DATE, 
    CONSTRAINT ENTRENA_COD_PK PRIMARY KEY(COD_CONTRATO),
    CONSTRAINT ENTRENA_ENTRE_FK FOREIGN KEY(COD_ENTRENADOR) REFERENCES 
        ENTRENADORES(COD_ENTRENADOR),
    CONSTRAINT ENTRENA_EQUI_FK FOREIGN KEY(COD_EQUIPO) 
        REFERENCES EQUIPOS(COD_EQUIPO)
);

CREATE TABLE ASISTE(
    COD_CONTRATO NUMBER(2) GENERATED ALWAYS AS IDENTITY
        (START WITH 1 INCREMENT BY 1),
    COD_ASISTENTE NUMBER(2),
    COD_EQUIPO NUMBER(2),
    FECHA_FIN DATE,
    FECHA_INICIO DATE,
    CONSTRAINT A_COD_PK PRIMARY KEY(COD_CONTRATO),
    CONSTRAINT A_ASIS_FK FOREIGN KEY(COD_ASISTENTE) REFERENCES
        ASISTENTES(COD_ASISTENTE),
    CONSTRAINT A_EQUIPO_FK FOREIGN KEY(COD_EQUIPO) REFERENCES   
        EQUIPOS(COD_EQUIPO)
);

CREATE TABLE ROL(
    COD_ROL NUMBER(2),
    NOM_ROL VARCHAR2(15),
    CONSTRAINT ROL_COD_PK PRIMARY KEY(COD_ROL),
    CONSTRAINT ROL_NOMBRE_CK CHECK(UPPER(NOM_ROL) IN ('ADMINISTRADOR', 'USUARIO'))
);

CREATE TABLE USUARIOS(
    COD_USUARIO NUMBER(2) GENERATED ALWAYS AS IDENTITY
        (START WITH 1 INCREMENT BY 1),
    NOMBRE VARCHAR2(15),
	FECHA_NACIMIENTO DATE,
	PASSWORD_ VARCHAR2(20), --PASSWORD
    EMAIL VARCHAR2(30) NULL,
    COD_ROL NUMBER(2),
    CONSTRAINT U_COD_PK PRIMARY KEY(COD_USUARIO),
    CONSTRAINT U_ROL_FK FOREIGN KEY(COD_ROL) REFERENCES
        ROL(COD_ROL)
);
    
CREATE TABLE ALMACEN_XML(
    result_xml clob
);