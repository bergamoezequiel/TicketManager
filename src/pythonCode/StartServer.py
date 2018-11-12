import sqlite3

from flask import Flask, request
from flask_restful import Api, Resource, reqparse

app = Flask(__name__)
app.config['SERVER_NAME'] = '192.168.43.147:5000'
api = Api(app)


	
class Cliente(Resource):
	def get(self,name):
		conn = sqlite3.connect('TicketManager.db')
		c = conn.cursor()
		usuario={}
		for row in c.execute('SELECT * FROM CLIENTES where NOMBRE_USU=?',(name,)):
			 usuario={
				"IdCliente": row[0],
				"Nombre": row[1],
				"Apellido": row[2],
				"DNI":row[3],
				"NombreDeUsuario": row[4]}
		conn.close()
		return usuario, 200

class Clientes(Resource):
	def get(self):  
		Clientes=[]
		conn = sqlite3.connect('TicketManager.db')
		c = conn.cursor()
		for row in c.execute('SELECT * FROM CLIENTES'):
			user = {
			"IdCliente": row[0],
			"Nombre": row[1],
			"Apellido": row[2],
			"DNI":row[3],
			"NombreDeUsuario": row[4]
			}
			Clientes.append(user);
		conn.close()
		return Clientes, 200

class EmpresasEmisoras(Resource):
	def get(self):  
		Empresas=[]
		conn = sqlite3.connect('TicketManager.db')
		c = conn.cursor()
		for row in c.execute('SELECT * FROM EMPRESAS_EMISORAS'):
			user = {
			"RazonSocial": row[0],
			"CUIT": row[1]
			}
			Empresas.append(user);
		conn.close()
		EmpresasFinal={
		"Empresas":Empresas
		}
		return EmpresasFinal, 200


class Funciones(Resource):
	def get(self):
		Funciones=[]
		conn = sqlite3.connect('TicketManager.db')
		c = conn.cursor()
		for row in c.execute('SELECT * FROM FUNCIONES where CUIT_EMPRESA=? and ID_ESPECTACULO=?',(request.args.get('cuitEmp'),request.args.get('IdEspectaculo'), )):
			funcion = {
			"IdFuncion": row[0],
			"IdEspectaculo": row[1],
			"Hora":row[3],
			"Dia":row[4]
			}
			Funciones.append(funcion);
		conn.close()
		FuncionesFinal={
		"Funciones":Funciones
		}
		return FuncionesFinal,200
		

class Espectaculos(Resource):
	def get(self):
		Espectaculos=[]
		conn = sqlite3.connect('TicketManager.db')
		c = conn.cursor()
		c2 = conn.cursor()
		for row in c.execute('SELECT distinct(ID_ESPECTACULO) FROM FUNCIONES where CUIT_EMPRESA=?',(request.args.get('cuitEmp'), )):
			for rows2 in c2.execute('SELECT * FROM ESPECTACULOS where ID_ESPECTACULO=?',(row[0], )):
				Espectaculo = {
				"IdEspectaculo": row[0],
				"Nombre": rows2[1],
				"Descripcion":rows2[2],
				}
				Espectaculos.append(Espectaculo);
		conn.close()
		EspectaculosFinal={
		"Espectaculos":Espectaculos
		}
		return EspectaculosFinal,200

		 
class Entradas(Resource):
	def get(self):
		Entradas=[]
		conn = sqlite3.connect('TicketManager.db')
		c = conn.cursor()
		for row in c.execute('SELECT * FROM Entradas where ID_FUNCION=?',(request.args.get('IdFuncion'), )):
			funcion = {
			"IdFuncion": row[0],
			"Ubicacion": row[1],
			"IdCliente":row[2],
			
			}
			Entradas.append(funcion);
		conn.close()
		EntradasFinal={
		"Entradas":Entradas
		}
		return EntradasFinal,200
		

class Intereses(Resource):
	def post(self):
			Entradas=[]
			conn = sqlite3.connect('TicketManager.db')
			c = conn.cursor()
			print(request)
			#print(request.json["DNI"])
			c.execute('INSERT INTO INTERESES VALUES (?,?)',(request.json["IdCliente"],request.json["IdFuncion"]))
			conn.commit()
			return Entradas,200
api.add_resource(Clientes, "/Clientes")
api.add_resource(Cliente, "/Cliente/<string:name>")
api.add_resource(EmpresasEmisoras, "/Empresas")
api.add_resource(Funciones, "/Funciones") #http://192.168.0.110:5000/Funciones?cuitEmp=30-69726350-7&IdEspectaculo=1-SPIDER ejemplo de invocacion
api.add_resource(Espectaculos,"/Espectaculos") #Espectaculos por empresa	http://192.168.0.110:5000/Espectaculos?cuitEmp=30-69726350-7
api.add_resource(Entradas,"/Entradas")
api.add_resource(Intereses,"/Intereses")



app.run(debug=True)