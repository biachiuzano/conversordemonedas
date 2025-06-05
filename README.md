<p align="center">
  <img src="assets/banner.png" width="100%" alt="Conversor de Monedas">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-Finalizado-brightgreen" />
  <img src="https://img.shields.io/badge/Java-17-blue" />
  <img src="https://img.shields.io/badge/API-ExchangeRate--API-yellow" />
</p>

# 📘 Descripción del proyecto

Este es un proyecto de consola en Java que permite realizar conversiones entre monedas latinoamericanas utilizando datos reales de una API. El programa guarda un historial con fecha y hora, muestra nombres completos de monedas y gestiona errores de entrada de forma amigable.

# 🛠️ Tecnologías utilizadas

- ☕ Java (JDK 11+)
- 📦 Gson (para parsear JSON)
- 🌐 ExchangeRate-API
- 💻 Visual Studio Code

# ✨ Funcionalidades

- Menú interactivo por consola
- Conversión con tasa real obtenida desde la API
- Registro automático del historial en `historial.txt`
- Visualización del historial desde el programa
- Manejo de errores: opciones inválidas o entradas no numéricas
- Monedas mostradas con nombres completos (no solo siglas)

# ▶️ Cómo usar

1. Cloná este repositorio:  
   `git clone https://github.com/biachiuzano/conversordemonedas.git`

2. Abrí el proyecto en tu editor (recomendado: Visual Studio Code)

3. Ejecutá el archivo desde tu terminal:  
   `src/Conversor.java`

4. Seguí las instrucciones que aparecen en consola para seleccionar las monedas y convertir montos.

5. Las conversiones se guardarán automáticamente en el archivo `historial.txt`.

¡Listo! Ya podés convertir monedas de forma rápida y sencilla 💸


# 📁 Estructura del proyecto

 ConversorDeMonedas/

 ├── src/
 
 │ └── Conversor.java
 
 ├── lib/
 
 │ └── gson-2.13.1.jar
 
 ├── historial.txt
 
 ├── assets/
 
 │ ├── banner.png
 
 │ └── captura.png
 
 └── README.md
 


📌 El archivo `historial.txt` se genera automáticamente tras cada conversión válida.

# 👩🏻‍💻 Desarrolladora

<p align="center">
  <a href="https://github.com/biachiuzano" target="_blank">
    <img src="https://github.com/user-attachments/assets/9f0e476a-1eb0-4884-aa5a-68e2768bb232" width="120" alt="Bianka Chiuzano" style="border-radius: 50%;" />
  </a>
  <br>
  <b>Bianka Chiuzano</b><br>
  <sub>Desarrolladora de software | Apasionada por la tecnología</sub>
</p>

# 📄 Licencia

Este proyecto fue desarrollado con fines educativos y es de libre uso.  
¡Podés compartirlo, modificarlo y seguir aprendiendo! ✨
