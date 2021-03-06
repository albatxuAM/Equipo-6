-- GESTIONAR_PARTIDOS_CUERPO
/* 
 Fecha: 04/05/2022
  Descripcion:  Paquete gestion de partidos
 */
 
CREATE OR REPLACE PACKAGE BODY GEST_PARTIDOS AS

FUNCTION  BUSCAR_EQUIPO_POR_NOMBRE
    (P_NOMBRE VARCHAR2)
    RETURN NUMBER;
          
-- **************************************************************
-- Programacion de la funcion
FUNCTION  BUSCAR_EQUIPO_POR_NOMBRE
    (P_NOMBRE VARCHAR2)
    RETURN NUMBER
    AS
        P_COD_EQUIPO EQUIPOS.COD_EQUIPO%TYPE;
    BEGIN
        -- Comprobar que existe el equipo
        SELECT COD_EQUIPO INTO P_COD_EQUIPO
        FROM EQUIPOS
        WHERE UPPER(NOMBRE) = UPPER(P_NOMBRE);
        -- devolver el codigo del equipo
        RETURN P_COD_EQUIPO;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
    -- lleno P_COD_EQUIPO con null para que mi funcion indique que no existe el
    -- nombre de equipo en la base de datos
        P_COD_EQUIPO:=NULL;
        RETURN P_COD_EQUIPO;
        
    WHEN TOO_MANY_ROWS THEN
    -- lleno P_COD_EQUIPO con -1 para que mi funcion indique que existe mas de 
    -- una equipo con el mismo nombre
        P_COD_EQUIPO:=-1;
        RETURN P_COD_EQUIPO;
END BUSCAR_EQUIPO_POR_NOMBRE;


-- **************************************************************
/*
Procedimiento INSERT_JORNADA: insertar una jornada
*/
PROCEDURE INSERT_PARTIDO(
        P_HORA_PARTIDO PARTIDOS.HORA_PARTIDO%TYPE,
        P_COD_JORNADA PARTIDOS.COD_JORNADA%TYPE,
        P_EQUIPO1 EQUIPOS.NOMBRE%TYPE,
        P_EQUIPO2 EQUIPOS.NOMBRE%TYPE
        )
AS
    V_COD_EQUIPO1 NUMBER;
    V_COD_EQUIPO2 NUMBER;
    V_COD_PARTIDO NUMBER;
    EQUIPO1_NO_ENCONTRADO EXCEPTION;
    EQUIPO2_NO_ENCONTRADO EXCEPTION;
BEGIN
	-- comprobando que no existe otra equipo con ese nombre
    V_COD_EQUIPO1 := BUSCAR_EQUIPO_POR_NOMBRE(P_EQUIPO1);
    V_COD_EQUIPO2 := BUSCAR_EQUIPO_POR_NOMBRE(P_EQUIPO2);
    
    IF (V_COD_EQUIPO1 IS NULL OR V_COD_EQUIPO2 IS NULL)
    THEN      
        RAISE EQUIPO1_NO_ENCONTRADO;
    ELSE 
        IF (V_COD_EQUIPO2 IS NULL)
        THEN      
            RAISE EQUIPO2_NO_ENCONTRADO;
        ELSE 
        NULL;
             --insertar el partido
            INSERT INTO PARTIDOS (HORA_PARTIDO, COD_JORNADA) 
            VALUES (P_HORA_PARTIDO, P_COD_JORNADA);
           
            --obtener codigo partido
            SELECT COD_PARTIDO INTO V_COD_PARTIDO
            FROM PARTIDOS
            WHERE COD_JORNADA = P_COD_JORNADA
              AND HORA_PARTIDO = P_HORA_PARTIDO;
            
            --insertar participa
            INSERT INTO PARTICIPA (COD_EQUIPO1, COD_EQUIPO2, COD_PARTIDO) 
            VALUES (V_COD_EQUIPO1, V_COD_EQUIPO2, V_COD_PARTIDO);
        END IF;   
    END IF; 
EXCEPTION
    WHEN EQUIPO1_NO_ENCONTRADO THEN
        RAISE_APPLICATION_ERROR (-20020 ,'Err. el ' || P_EQUIPO1 ||'no existe');
	 WHEN EQUIPO2_NO_ENCONTRADO THEN
        RAISE_APPLICATION_ERROR (-20021 ,'Err. el ' || P_EQUIPO2 ||'no existe');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR('-20999','Error desconocido INSERT_PARTIDO');
END INSERT_PARTIDO;

PROCEDURE INSERT_RESULTADO(
    P_COD_PARTIDO PARTIDOS.COD_PARTIDO%TYPE,
    P_RESULTADO PARTIDOS.RESULTADO%TYPE
    )
AS
    PARTIDO_NO_EXISTE EXCEPTION;
BEGIN
    UPDATE PARTIDOS 
    SET RESULTADO = P_RESULTADO
    WHERE COD_PARTIDO = P_COD_PARTIDO;
    
    IF SQL%NOTFOUND
    THEN
        RAISE PARTIDO_NO_EXISTE; 
    END IF;
    
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        RAISE_APPLICATION_ERROR('-20023' ,'Err. partida no existe');
	WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR('-20999','Error desconocido');
END INSERT_RESULTADO;




/******************************************************************************/

PROCEDURE INSERT_CLASIFICACION(
	P_COD_EQUIPO1 PARTICIPA.COD_EQUIPO1%TYPE,
    P_COD_EQUIPO2 PARTICIPA.COD_EQUIPO2%TYPE,
    P_RESULTADO   PARTIDOS.RESULTADO%TYPE
)

AS

PUNTOSEQUIPO1 NUMBER(1);
PUNTOSEQUIPO2 NUMBER(1);
CLASIFICACION_NO_EXISTE EXCEPTION;

BEGIN
    SELECT TO_NUMBER(SUBSTR(RESULTADO,1,1)) AS EQUIPO1,
           TO_NUMBER(SUBSTR(RESULTADO,3,3)) AS EQUIPO2            
    
    INTO   PUNTOSEQUIPO1,PUNTOSEQUIPO2

    FROM PARTIDOS;

IF SQL%NOTFOUND
    THEN
        RAISE CLASIFICACION_NO_EXISTE;
END IF;

EXCEPTION 

WHEN CLASIFICACION_NO_EXISTE THEN 
          RAISE_APPLICATION_ERROR('-20024' ,'Err. clasificaion no existe');
	WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR('-20999','Error desconocido');
        
END INSERT_CLASIFICACION;

-- **************************************************************

-- Programacion de la funcion
FUNCTION  PARTIDOS_GANADOS
    (P_COD_EQUIPO NUMBER)
    RETURN NUMBER
    AS
        V_GANADOS NUMBER := 0;
  
        CURSOR C_PARTIDOS_EQUIPO IS (
            SELECT COD_PARTIDO, COD_EQUIPO1
            FROM PARTICIPA
            WHERE (COD_EQUIPO1 = P_COD_EQUIPO OR COD_EQUIPO2 = P_COD_EQUIPO));
            
        R_EQUIPO1 NUMBER;
        R_EQUIPO2 NUMBER;
    BEGIN            
        FOR REG_COD_PARTIDO IN C_PARTIDOS_EQUIPO 
        LOOP
            --CONSEGUIR EL RESULTADO DEL PARTIDO EN CUESTION
            SELECT to_number(SUBSTR(RESULTADO, 1, 1)), to_number(SUBSTR(RESULTADO, 3, 3))
                INTO R_EQUIPO1, R_EQUIPO1
            FROM PARTIDOS
            WHERE COD_PARTIDO = REG_COD_PARTIDO.COD_PARTIDO;
                
            --COMRPOBAR QUE EXISTE UN RESULADO PARA EL PARTIDO
            --IF(R_EQUIPO1 IS NOT NULL)
            --THEN
                --MIRAR SI ES EL EQUIPO1
                IF(P_COD_EQUIPO = REG_COD_PARTIDO.COD_EQUIPO1)
                THEN
                    --COMPROBAR SI HA GANADO
                    IF (R_EQUIPO1 > R_EQUIPO2)
                    THEN
                        V_GANADOS := V_GANADOS + 1;
                    END IF;
                ELSE 
                    -- ES EL EQUIPO2
                    IF (R_EQUIPO2 > R_EQUIPO1)
                    THEN
                        V_GANADOS := V_GANADOS + 1;
                    END IF;
                END IF;
            --END IF;    
        END LOOP;
        
    RETURN V_GANADOS;   
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR('-20999','Error desconocido');
END PARTIDOS_GANADOS;

END GEST_PARTIDOS;			   
				   




 




	
	
	
	



















