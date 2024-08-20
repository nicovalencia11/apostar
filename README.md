# Proyecto con Docker Compose

## NOTA:
EL API PARA OBTENER LOS RESULTADOS GENERA CONFLICTOS CON JAVA POR EL CERTIFICADO SSL
POR ESTE MOTIVO SE CREO UN SERVICIO PYTHON QUE CONSUME EL SERVICIO Y LO EXPONE EN UN
ENDPOINT SIN CERTIFICADO

Este proyecto utiliza Docker Compose para gestionar varios servicios, incluyendo una aplicación Spring Boot, una base de datos SQLite, una aplicación Angular y una aplicación Flask. Este archivo `README.md` te guiará a través de los pasos para construir y ejecutar los contenedores.

## Estructura del Proyecto

- **Spring Boot Application**: Servidor backend en Java.
- **SQLite Database**: Base de datos ligera utilizada por la aplicación Spring Boot.
- **Angular Application**: Interfaz de usuario frontend.
- **Flask Application**: Aplicación web escrita en Python con Flask.

## Requisitos

- [Docker](https://www.docker.com/products/docker-desktop) (para construir y ejecutar los contenedores)
- [Docker Compose](https://docs.docker.com/compose/install/) (para gestionar los contenedores)

## Instrucciones

### 1. Clona el Repositorio

Primero, clona el repositorio del proyecto desde GitHub (o el origen que uses):

git clone https://github.com/nicovalencia11/apostar.git
cd apostar

## 2. Ejecuta el docker compose

docker-compose up --build



## POSIBLES MEJORAS

## 1. 
El codigo se puede mejorar solucionando el conflicto con el certificado ssl para evitar que tener codigo python

## 2.
Usando una herramienta como AIRFLOW la cual nos permitira tener ya una interfaz para la administracion
y con codigo sencillo para el consumo y generacion de la imagen

## 3.
Implementando un servicio de cliente feign que es una libreria dedicada para consumir apis externas
