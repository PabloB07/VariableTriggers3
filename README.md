# VariableTriggers3 (VTV3)

VariableTriggers3 is a Paper server plugin for creating configurable player, world, and event triggers that run
scripts. It includes click, walk, command, area, and event triggers, a script parser, variables, placeholders, and
scheduled autosaving.

This repository contains the VTV3 source code, updated from the legacy plugin. It targets Java 21 bytecode and Paper API
1.21.4, with a runtime compatibility target of Java 21–25 and Paper 1.21.x–26.3.

## Requirements

- Paper 1.21.x–26.3 and Java 21–25 at runtime.
- Java 21 or newer and Maven to build. The artifact is compiled for Java 21.
- Vault is an optional soft dependency. Install Vault if you use economy, chat, or permission integrations.
- PlaceholderAPI is declared as a soft dependency in `plugin.yml`.

## Install or update on a server

1. Stop the server.
2. Back up the existing `plugins/VariableTriggers3/` folder, including scripts, settings, and data files. Keep a copy of
   the currently installed plugin JAR so you can roll back.
3. Build or obtain the VariableTriggers3 VTV3 plugin JAR.
4. Replace the old VariableTriggers3 JAR in the server's `plugins/` directory. Do not delete the existing
   `plugins/VariableTriggers3/` data folder when updating.
5. Start the server and check the console for the `VTV3 is ready.` message or any startup errors.
6. Test the existing triggers and scripts in-game. If startup or data migration fails, stop the server and restore the
   backups before trying again.

The plugin saves scripts and data during shutdown and periodically while running. Always stop the server cleanly before
replacing the JAR or restoring data.

## Build from source

The project uses Maven. With a compatible JDK and Maven installed, run this from the repository root:

```sh
mvn clean package
```

The packaged plugin JAR is written to `target/VariableTrigger3-1.0.jar`. It includes FastBoard and SpiGUI; Paper supplies
the server API, and Vault remains optional for economy, chat, or permission integrations.

## First use and commands

After starting the server, use `/vt ?` for the in-game help. Script directives include:

- `/vt reload` reloads scripts and trigger files without restarting the server. Plugin data lives under
  `plugins/VariableTriggers3/`; existing data in `plugins/VariableTriggers/` is migrated or merged on startup.
- `@SCOREBOARD TITLE <text>`, `@SCOREBOARD LINE <1-15> <text>`, and `@SCOREBOARD REMOVE`. Add a player name after
  `@SCOREBOARD` to target an online player instead of the player running the script.
- `@MODIFYINV SET <slot> <material> [amount] [name]`, `@MODIFYINV REMOVE <slot>`, and `@MODIFYINV CLEAR`. These edit
  the top inventory currently open by the script's player; a player name after `@MODIFYINV` targets another player.
- Legacy `&` colors and hex colors such as `&#12ABEF` are accepted in messages, scoreboard titles/lines, and item names.
  Inventory materials accept the names available on the running server, including namespaced forms such as
  `minecraft:cherry_planks`.

The plugin also provides command families for scripts,
events, areas, click triggers, walk triggers, and player/server commands. Use each command's help output for its
arguments and permissions.

The permission defaults are defined in `resources/plugin.yml`. In particular, `vtriggers.admin` defaults to operators,
while `vtriggers.player` and trigger-use permissions default to all players. Grant creation permissions only to trusted
users.

## Data and configuration

VariableTriggers3 creates its data under `plugins/VariableTriggers3/`. The source initializes settings and script/data
files on startup. Back up this entire directory before updating the plugin or editing scripts.

Bundled resource files in `resources/` include `plugin.yml` and `placeholders.yml`. The legacy development notes and
placeholder/script examples are in `resources/reminder`; they are not a complete user manual.

## Contributing

Keep changes compatible with the Paper API baseline and supported Java versions. Test changes on the server versions
the project aims to support, and document behavior or migration changes here.

---

# VariableTriggers3 (VTV3)

VariableTriggers3 es un plugin para servidores Paper que permite crear disparadores configurables de jugadores,
del mundo y de eventos, y ejecutar scripts. Incluye disparadores de clic, movimiento, comandos, áreas y eventos, además
de un intérprete de scripts, variables, placeholders y guardado automático programado.

Este repositorio contiene el código fuente de VTV3, actualizado desde el plugin heredado. El proyecto genera bytecode
Java 21 y apunta a Paper API 1.21.4, con compatibilidad objetivo de ejecución para Java 21–25 y Paper 1.21.x–26.3.

## Requisitos

- Paper 1.21.x–26.3 y Java 21–25 para ejecutar el plugin.
- Java 21 o posterior y Maven para compilar. El artefacto se compila para Java 21.
- Vault es una dependencia opcional. Instala Vault si utilizas integraciones de economía, chat o permisos.
- `plugin.yml` declara PlaceholderAPI como dependencia opcional.

## Instalar o actualizar en un servidor

1. Detén el servidor.
2. Haz una copia de seguridad de la carpeta existente `plugins/VariableTriggers3/`, incluidos scripts, ajustes y datos.
   Guarda también el JAR instalado para poder volver a la versión anterior.
3. Compila u obtén el JAR del plugin VariableTriggers3 VTV3.
4. Reemplaza el JAR anterior de VariableTriggers3 en la carpeta `plugins/` del servidor. No borres la carpeta de datos
   existente `plugins/VariableTriggers3/` al actualizar.
5. Inicia el servidor y revisa la consola para confirmar el mensaje `VTV3 is ready.` y comprobar que no haya errores de
   inicio.
6. Prueba los disparadores y scripts existentes. Si falla el inicio o la migración de datos, detén el servidor y
   restaura las copias de seguridad antes de volver a intentarlo.

El plugin guarda scripts y datos al cerrarse y periódicamente mientras está activo. Detén el servidor correctamente
antes de reemplazar el JAR o restaurar datos.

## Compilar desde el código fuente

El proyecto usa Maven. Con un JDK y Maven compatibles instalados, ejecuta desde la raíz del repositorio:

```sh
mvn clean package
```

El JAR compilado se genera en `target/VariableTrigger3-1.0.jar`. Incluye FastBoard y SpiGUI; Paper proporciona la API del
servidor y Vault sigue siendo opcional para integraciones de economía, chat o permisos.

## Primer uso y comandos

Después de iniciar el servidor, usa `/vt ?` para consultar la ayuda dentro del juego. Las directivas de scripts incluyen:

- `/vt reload` vuelve a cargar scripts y disparadores sin reiniciar el servidor. Los datos quedan en
  `plugins/VariableTriggers3/`; los datos existentes en `plugins/VariableTriggers/` se migran o combinan al iniciar.
- `@SCOREBOARD TITLE <texto>`, `@SCOREBOARD LINE <1-15> <texto>` y `@SCOREBOARD REMOVE`. Agrega el nombre de un jugador
  después de `@SCOREBOARD` para actualizar a otro jugador conectado.
- `@MODIFYINV SET <ranura> <material> [cantidad] [nombre]`, `@MODIFYINV REMOVE <ranura>` y `@MODIFYINV CLEAR`. Modifican
  el inventario superior abierto por el jugador del script; agrega un nombre después de `@MODIFYINV` para elegir otro.
- Se aceptan colores clásicos `&` y colores hexadecimales como `&#12ABEF` en mensajes, títulos/líneas del scoreboard y
  nombres de ítems. Los inventarios admiten los materiales disponibles en el servidor, incluidos nombres como
  `minecraft:cherry_planks`.

El plugin también ofrece comandos para scripts, eventos, áreas, disparadores de clic, movimiento y comandos de
jugador/servidor. Consulta la ayuda de cada comando para ver sus argumentos y permisos.

Los permisos predeterminados están definidos en `resources/plugin.yml`. En particular, `vtriggers.admin` está asignado
por defecto a operadores, mientras que `vtriggers.player` y los permisos de uso de disparadores se asignan por defecto a
todos los jugadores. Concede permisos de creación solo a usuarios de confianza.

## Datos y configuración

VariableTriggers3 crea sus datos en `plugins/VariableTriggers3/`. Al iniciar, el plugin prepara los ajustes y los archivos
de scripts/datos. Haz una copia de seguridad de toda esta carpeta antes de actualizar el plugin o editar scripts.

Los recursos incluidos en `resources/` contienen, entre otros, `plugin.yml` y `placeholders.yml`. Las notas de desarrollo
y ejemplos heredados de placeholders y scripts están en `resources/reminder`; no constituyen un manual completo.

## Contribuir

Mantén los cambios compatibles con las APIs heredadas de Java y Bukkit del proyecto, salvo que se actualice
intencionalmente la plataforma soportada. Prueba los cambios en las versiones de servidor objetivo y documenta aquí los
cambios de comportamiento o migración.
