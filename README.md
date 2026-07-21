# Calculadora de Arbitraje de divisas 
 
Aplicación de escritorio en Java (Swing) que detecta oportunidades de **arbitraje de divisas** entre monedas fiat (USD, EUR, GBP, MXN, JPY, CHF, CAD, AUD) y Bitcoin (BTC), usando tasas de cambio reales consultadas en tiempo de ejecución.
 
El problema se modela como un **grafo dirigido y ponderado** (cada moneda es un nodo, cada tasa es una arista con peso `-log(tasa)`), y se resuelve aplicando el algoritmo de **Bellman-Ford** para detectar ciclos de peso negativo, que equivalen matemáticamente a ciclos de arbitraje rentables.
 
> Proyecto desarrollado para la asignatura **Técnicas de Programación 3**, Universidad Nacional Experimental de Guayana (UNEG), Período 2026-1, Sección 2.
 
---
 
## Requisitos previos
 
| Herramienta | Versión mínima |
|---|---|
| JDK (Java Development Kit) | 11 o superior |
| Maven | 3.8 o superior |
| Conexión a internet | La aplicación consulta tasas de cambio reales al iniciar |
 
## Instalación
 
### Opción rápida: usar el ejecutable (.exe)
 
Si solo quieres usar la aplicación sin compilar el código, descarga el archivo `ArbitrajeDivisas.exe` incluido en este repositorio y ejecútalo directamente con doble clic. No requiere tener Java instalado.
 
> Requiere conexión a internet para cargar las tasas de cambio al iniciar.
 
### Opción para desarrolladores: compilar desde el código fuente
 
### 1. Clonar el repositorio
 
```bash
git clone https://github.com/<tu-usuario>/<nombre-del-repositorio>.git
cd <nombre-del-repositorio>
```
 
### 2. Verificar Java
 
```bash
java -version
javac -version
```
 
Ambos comandos deben mostrar la versión 11 o superior. Si no tienes Java instalado, descárgalo desde [Eclipse Temurin](https://adoptium.net/).
 
### 3. Verificar Maven
 
```bash
mvn -version
```
 
Si no está instalado, descárgalo desde [maven.apache.org/download.cgi](https://maven.apache.org/download.cgi), descomprímelo y agrega su carpeta `bin` a la variable de entorno `PATH`.
 
### 4. Compilar el proyecto
 
Desde la raíz del proyecto (donde está el archivo `pom.xml`):
 
```bash
mvn clean package
```
 
Esto genera el ejecutable `ArbitrajeDivisas.jar` dentro de la carpeta `target/`.
 
### 5. Ejecutar la aplicación
 
```bash
cd target
java -jar ArbitrajeDivisas.jar
```
