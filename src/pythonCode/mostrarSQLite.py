import sqlite3
conn = sqlite3.connect('TicketManager.db')
c = conn.cursor()
print("-----------clientes-----------")
for row in c.execute('SELECT * FROM CLIENTES'):
        print (row)
print("-----------empresasEmisoras-----------")
for row in c.execute('SELECT * FROM EMPRESAS_EMISORAS'):
        print (row)
print("-----------espectaculos-----------")
for row in c.execute('SELECT * FROM ESPECTACULOS'):
        print (row)
print("-----------funciones-----------")
for row in c.execute('SELECT * FROM FUNCIONES'):
        print (row)
print("-----------Entradas-----------")
for row in c.execute('SELECT * FROM ENTRADAS'):
        print (row)
print("-----------INTERESES-----------")
for row in c.execute('SELECT * FROM INTERESES'):
        print (row)