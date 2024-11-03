# Guía de contribución

&nbsp;
Todas las contribuciones que utilicen las siguientes convenciones serán bienvenidas.

## Nomenclatura

- Variables locales y atributos: `camelCase`
- Clases y archivos de clases: `PascalCase`
- Nombre de la base de datos: `snake_case`
- Tablas y atributos de la base de datos: `PascalCase`

## Estilo

### Lenguaje

- Los archivos y estructura del proyecto debe estar en inglés.

- El código debe estar en inglés.

- Las tablas y atributos de la base de datos deben estar en inglés.

- Los comentarios deben estar en español.

### Comentarios

- Los comentarios que solo hagan referencia a una línea de código deben ser de tipo inline a la derecha de la linea en cuestión.

- Los comentarios que hagan referencia a un bloque de código deben estar al arriba del bloque, separados del código por una línea en blanco antes y después del comentario.

### Líneas en blanco, indentación y espacios

- Hacer indentaciones de 4 espacios, salvo que se trate de archivos `.html` o `.jsp` que deben ser indentados con 2 espacios.

- Dejar un espacio después de una coma o antes de un paréntesis, salvo que se trate del paréntesis de una función que debe estar inmediatamente pegado al nombre de la misma.

- Siempre dejar una línea en blanco entre una declaración y una estructura de control de flujo (if, for, while, etc).

- Dejar una línea en blanco después de una llave de cierre, salvo entre llaves de cierre sucesivas o antes de bloques else.

- No dejar líneas en blanco ni espacios después de una llave de apertura ni antes de una llave de cierre.

### Llaves

- Alinear todas las llaves con su respectiva llave de bloque.

- Las líneas que contienen llaves solo tienen que contener llaves.

- Usar llaves en bloques if de una sola línea.

## Base de datos

- Debido a limitaciones en los servidores gratuitos para desplegar el proyecto, no se permite usar Triggers.

## Extensiones

### Prettier SQL VSCode

&nbsp;
Instalar la extensión [Prettier SQL VSCode](https://marketplace.visualstudio.com/items?itemName=inferrinizzard.prettier-sql-vscode) para Visual Studio Code. Antes de cada commit que involucre archivos `.sql`, abrir el archivo en cuestión con VS Code, hacer click derecho sobre el código y luego hacer click en `Format Document`. (Se puede usar cualquier editor de código SQL de cualquier gestor de bases de datos, solo es necesario formatear el código con Prettier SQL VSCode luego.)