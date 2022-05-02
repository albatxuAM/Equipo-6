DROP TABLE ENTRENA CASCADE CONSTRAINT;
DROP TABLE JUGAR_PARA CASCADE CONSTRAINT;
DROP TABLE ASISTE CASCADE CONSTRAINT;
DROP TABLE TEMPORADAS CASCADE CONSTRAINT;

DROP TABLE PERSONA CASCADE CONSTRAINT;
DROP TABLE ASISTENTES CASCADE CONSTRAINT;
DROP TABLE ENTRENADORES CASCADE CONSTRAINT;
DROP TABLE JUGADORES CASCADE CONSTRAINT;
DROP TABLE EQUIPOS CASCADE CONSTRAINT;

DROP TABLE PARTIDOS CASCADE CONSTRAINT;
DROP TABLE JORNADAS CASCADE CONSTRAINT;

DROP TABLE ALMACEN_XML CASCADE CONSTRAINT;


CREATE TABLE JORNADAS (
    COD_JORNADA NUMBER(2),
    FECHA_JORNADA DATE,
    CONSTRAINT JOR_COD_PK PRIMARY KEY(COD_JORNADA)
);

CREATE TABLE PARTIDOS (
    COD_PARTIDO NUMBER(2),
    HORA_PARTIDO VARCHAR2(30),
    RESULTADO VARCHAR2(5),
    COD_JORNADA NUMBER(2),
    CONSTRAINT PART_JORD_FK FOREIGN KEY(COD_JORNADA) 
        REFERENCES JORNADAS(COD_JORNADA),
    CONSTRAINT PART_COD_PK PRIMARY KEY(COD_PARTIDO)
);

CREATE TABLE EQUIPOS (
    COD_EQUIPO NUMBER (2),
    COD_PARTIDO NUMBER (4),
    NOMBRE VARCHAR2 (30),
    FECHA_FUNDACION DATE,
    CIUDAD VARCHAR2 (30),
    ESPONSOR VARCHAR2 (20) NOT NULL,
    NOMBRE_DUEÑO VARCHAR2 (20),  
    CONSTRAINT EQUI_CODE_PK PRIMARY KEY (COD_EQUIPO),
    CONSTRAINT EQUI_CODP_FK FOREIGN KEY (COD_PARTIDO)
        REFERENCES PARTIDOS(COD_PARTIDO)
); 

CREATE TABLE PERSONA(
	NOMBRE VARCHAR2(15),
	APELLIDO VARCHAR2(15),
	SUELDO NUMBER(6),
	FECHA_NACIMIENTO DATE,
	NACIONALIDAD VARCHAR2(20),
    NICKNAME VARCHAR2(15),
    COD_PERSONA NUMBER(2),
    CONSTRAINT PER_COD_PK PRIMARY KEY(COD_PERSONA)
);

CREATE TABLE JUGADORES(
    COD_JUGADOR NUMBER(2),
	ROL VARCHAR2(15),
    CONSTRAINT COD_JUG_PK PRIMARY KEY(COD_JUGADOR),
    CONSTRAINT COD_JUG_FK FOREIGN KEY(COD_JUGADOR) REFERENCES 
        PERSONA (COD_PERSONA),
	CONSTRAINT PJ_ROL_CK CHECK(UPPER(ROL) IN ('TOP, JGL, MID, ADC, SUPP')),

);

CREATE TABLE ENTRENADORES(
    COD_ENTRENADOR NUMBER(2),
    CONSTRAINT COD_ENTRE_PK PRIMARY KEY(COD_ENTRENADOR),
    CONSTRAINT COD_ENTRE_FK FOREIGN KEY(COD_ENTRENADOR) REFERENCES 
        PERSONA(COD_PERSONA)
);

CREATE TABLE ASISTENTES (
    COD_ASISTENTE NUMBER(2),
    COD_ENTRE_ASOCIADO NUMBER(2),
    CONSTRAINT COD_ASIS_PK PRIMARY KEY(COD_ASISTENTE)
    CONSTRAINT COD_ASIS_FK FOREIGN KEY(COD_ASISTENTE) REFERENCES 
        PERSONA(COD_PERSONA),
    CONSTRAINT ENTRE_ASOCIADO_FK FOREIGN KEY(COD_ENTRE_ASOCIADO) REFERENCES
        ENTRENADORES(COD_ENTRENADOR)
);

CREATE TABLE TEMPORADAS(
    COD_TEMPORADA NUMBER(4),
    ABIERTA CHAR(1),
    CONSTRAINT TEMP_COD_PK PRIMARY KEY(COD_TEMPORADA),
    CONSTRAINT TEMP_OPEN_CK CHECK(UPPER(ABIERTA) IN ('S,N'))
);
    
CREATE TABLE JUGAR_PARA( 
    COD_CONTRATO NUMBER(2),
    COD_JUGADOR NUMBER(2),
    COD_EQUIPO NUMBER(2),
    FECHA_FIN DATE,
    FECHA_INICIO DATE, 
    CONSTRAINT JP_COD_PK PRIMARY KEY(COD_CONTRATO),
    CONSTRAINT JP_JUG_FK FOREIGN KEY(COD_JUGADOR) REFERENCES JUGADORES(COD_JUGADOR),
    CONSTRAINT JP_EQUIPO_FK FOREIGN KEY(COD_EQUIPO) REFERENCES EQUIPOS(COD_EQUIPO)
); 
    
CREATE TABLE ENTRENA(
    COD_CONTRATO NUMBER(2),
    COD_ENTRENADOR NUMBER(2),
    COD_EQUIPO NUMBER(2),
    FECHA_FIN DATE,
    FECHA_INICIO DATE, 
    CONSTRAINT ENTRE_COD_PK PRIMARY KEY(COD_CONTRATO),
    CONSTRAINT JP_ENTRE_FK FOREIGN KEY(COD_ENTRENADOR) REFERENCES 
        ENTRENADORES (COD_ENTRENADOR),
    CONSTRAINT JP_EQUIPO_FK FOREIGN KEY(COD_EQUIPO) 
        REFERENCES EQUIPOS(COD_EQUIPO)
);

CREATE TABLE ASISTE(
    COD_CONSTRATO NUMBER(2),
    COD_ASISTENTE NUMBER(2),
    COD_EQUIPO NUMBER(2),
    FECHA_FIN DATE,
    FECHA_INICIO DATE,
    CONSTRAINT A_COD_PK PRIMARY KEY(COD_CONSTRATO),
    CONSTRAINT A_ASIS_FK FOREIGN KEY(COD_ASISTENTE) REFERENCES
        ASISTENTES(COD_ASISTENTE),
    CONSTRAINT A_EQUIPO_FK FOREIGN KEY(COD_EQUIPO) REFERENCES   
        EQUIPOS(COD_EQUIPO)
);

CREATE TABLE ALMACEN_XML(
    result_xml clob
);