import sqlite3
conn = sqlite3.connect('TicketManager.db')
c = conn.cursor()

# Create table
c.execute('''CREATE TABLE CLIENTES
             (ID_CLIENTE text, NOMBRE text,APELLIDO text,DNI text,NOMBRE_USU text)''')

# Insert a row of data
c.execute("INSERT INTO CLIENTES VALUES ('1','Ezequiel','Bergamo','37246527','ebergamo')")
c.execute("INSERT INTO CLIENTES VALUES ('2','Alejandro','Alazraqui','37456655','aleAlaz')")

c.execute('''CREATE TABLE EMPRESAS_EMISORAS
             (RAZON_SOCIAL text,CUIT text)''')

# Insert a row of data
c.execute("INSERT INTO EMPRESAS_EMISORAS VALUES ('CINEMARK SA','30-69726350-7')")
c.execute("INSERT INTO EMPRESAS_EMISORAS VALUES ('METROPOLITAN SURA S.A','30-59692812-5')")



c.execute('''CREATE TABLE ESPECTACULOS
             (ID_ESPECTACULO text, NOMBRE text,DESCRIPCION text)''')

# Insert a row of data
c.execute("INSERT INTO ESPECTACULOS VALUES ('1-SPIDER','SPIDERMAN','Un hombre es picado por una araña, por lo cual...')")
c.execute("INSERT INTO ESPECTACULOS VALUES ('2-2MAS2','DOS MAS DOS','Dos parejas de amigos deciden ser swingers...')")
c.execute("INSERT INTO ESPECTACULOS VALUES ('3-PERFDES','PERFECTOS DESCONOCIDOS','Amigos se juntan y deciden dejar sus telefonos a la vista...')")


c.execute('''CREATE TABLE FUNCIONES
             (ID_FUNCION text, ID_ESPECTACULO text, CUIT_EMPRESA text, HORA text, DIA text)''')

# Insert a row of data
c.execute("INSERT INTO FUNCIONES VALUES ('1-FUNC','1-SPIDER','30-69726350-7','10:00','20/12/2018')")
c.execute("INSERT INTO FUNCIONES VALUES ('2_FUNC','1-SPIDER','30-69726350-7','14:00','20/12/2018')")
c.execute("INSERT INTO FUNCIONES VALUES ('3-FUNC','1-SPIDER','30-69726350-7','18:00','20/12/2018')")
c.execute("INSERT INTO FUNCIONES VALUES ('3-FUNC','1-SPIDER','30-69726350-7','22:00','20/12/2018')")
c.execute("INSERT INTO FUNCIONES VALUES ('4-FUNC','2-2MAS2','30-69726350-7','12:00','11/12/2018')")


c.execute('''CREATE TABLE ENTRADAS
             (ID_FUNCION text, UBICACION text, ID_CLIENTE text)''')

# Insert a row of data
c.execute("INSERT INTO ENTRADAS VALUES ('1-FUNC','FIL1COL1','NO_ASIGNADA')")
c.execute("INSERT INTO ENTRADAS VALUES ('1-FUNC','FIL1COL12','NO_ASIGNADA')")
c.execute("INSERT INTO ENTRADAS VALUES ('1-FUNC','FIL1COL2','1')")
c.execute("INSERT INTO ENTRADAS VALUES ('1-FUNC','FIL1COL23','1')")
c.execute("INSERT INTO ENTRADAS VALUES ('4-FUNC','FIL1COL23','NO_ASIGNADA')")


c.execute('''CREATE TABLE INTERESES
             (ID_CLIENTE text, ID_FUNCION text, FUE_NOTIFICADO INTEGER)''')
#c.execute("INSERT INTO INTERESES VALUES ('1','1-FUNC')")
#c.execute("INSERT INTO INTERESES VALUES ('1','4-FUNC')")

c.execute('''CREATE TABLE CODIGOS_PROMOCIONALES
             (ID_FUNCION text, CODIGO_PROMOCIONAL text, DESCRIPCION text, ID_CLIENTE text)''')

c.execute("INSERT INTO CODIGOS_PROMOCIONALES VALUES ('1-FUNC','Hjfs-46fD-SFh5','Entrada gratis','NO_ASIGNADA')")
c.execute("INSERT INTO CODIGOS_PROMOCIONALES VALUES ('1-FUNC','Hjfs-46fD-SFh6','balde de pochoclos gratis','NO_ASIGNADA')")
c.execute("INSERT INTO CODIGOS_PROMOCIONALES VALUES ('1-FUNC','Hjfs-46fD-SFh7','35% de descuento en mcDonalds','NO_ASIGNADA')")
c.execute("INSERT INTO CODIGOS_PROMOCIONALES VALUES ('4-FUNC','Hjfs-46fD-SFh8','Entrada gratis','NO_ASIGNADA')")
c.execute("INSERT INTO CODIGOS_PROMOCIONALES VALUES ('4-FUNC','Hjfs-46fD-SFh9','Algo gratis','NO_ASIGNADA')")
c.execute("INSERT INTO CODIGOS_PROMOCIONALES VALUES ('4-FUNC','Hjfs-46fD-SFh0','35% de descuento en mcDonalds','NO_ASIGNADA')")

# Save (commit) the changes
conn.commit()

# We can also close the connection if we are done with it.
# Just be sure any changes have been committed or they will be lost.
conn.close()