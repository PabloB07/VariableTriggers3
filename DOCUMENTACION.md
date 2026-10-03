# VariableTriggers3 (VTV3) — documentación en español

Guía práctica para instalar, configurar y usar VariableTriggers3 en Paper. El plugin ejecuta scripts asociados a
eventos, comandos, bloques y zonas.

## Requisitos e instalación

- Paper 1.21.x–26.3 y Java 21–25.
- Copia `VariableTrigger3-1.0.jar` en la carpeta `plugins/` y reinicia el servidor.
- El nombre del plugin que aparece en Paper es `VariableTriggers3`; el prefijo de sus mensajes es `VTV3`.
- Los archivos y carpetas de datos se generan en `plugins/VariableTriggers3/`. Si existe la antigua carpeta
  `plugins/VariableTriggers/`, al iniciar el plugin migra sus datos o combina los archivos que falten; conserva la
  carpeta antigua como respaldo.

Rutas habituales:

| Ruta | Contenido |
|---|---|
| `plugins/VariableTriggers3/scripts/` | Scripts reutilizables (`.script.yml`) |
| `plugins/VariableTriggers3/events/player/` | Scripts de eventos de jugador, por ejemplo `PlayerJoin.yml` |
| `plugins/VariableTriggers3/events/entity/` | Scripts de eventos de entidades |
| `plugins/VariableTriggers3/events/system/` | Scripts de eventos del servidor |
| `plugins/VariableTriggers3/events/triggers/ClickTriggers.yml` | Disparadores asociados a bloques |
| `plugins/VariableTriggers3/events/triggers/WalkTriggers.yml` | Disparadores al caminar sobre ubicaciones |
| `plugins/VariableTriggers3/inventories/` | Inventarios definidos en YAML |
| `plugins/VariableTriggers3/system/` | Ajustes y variables guardadas |

## Recargar y ayuda

```text
/vt ?
/vt core
/vt reload
```

`/vt reload` vuelve a cargar los scripts y archivos de triggers sin reiniciar el servidor. Después de editar archivos
manualmente, úsalo para aplicar los cambios. Los subcomandos específicos incluyen `/vt rt` para triggers,
`/vt rs` para scripts, `/vt st` y `/vt ss` para guardarlos, y `/vt sv` para guardar variables.

## Crear un script reutilizable

Crea `plugins/VariableTriggers3/scripts/Bienvenida.script.yml`:

```yaml
Scripts:
  bienvenida:
    Script:
      - '@PLAYER &a¡Hola, <playername>!'
      - '@PLAYER &eEstás en el mundo &b<worldname>&e.'
      - '@SCOREBOARD TITLE &6&lMi servidor'
      - '@SCOREBOARD LINE 1 &fJugador: &a<playername>'
      - '@SCOREBOARD LINE 2 &b¡Bienvenido!'
```

Las claves debajo de `Scripts:` son nombres de scripts. Llámalo desde `/vt` o desde otro script usando el nombre del
archivo (sin `.script.yml`) y el identificador:

```text
/vt run Bienvenida:bienvenida
```

También puedes gestionar scripts en el juego:

```text
/vts add Bienvenida bienvenida @PLAYER &a¡Hola, <playername>!
/vts add Bienvenida bienvenida @BROADCAST &e¡<playername> se unió!
/vts view Bienvenida bienvenida
/vts edit Bienvenida bienvenida 0 @PLAYER &bMensaje actualizado
/vts remove Bienvenida bienvenida
/vt ss
```

Al ejecutar `/vts add`, escribe **una línea del script por comando**. `/vt ss` guarda los scripts gestionados en el juego;
para scripts creados o editados directamente en archivos, ejecuta `/vt reload`.

## Ejecutar un script al entrar un jugador

Edita `plugins/VariableTriggers3/events/player/PlayerJoin.yml`:

```yaml
Worlds:
  - world
main:
  - '@CALL Bienvenida:bienvenida'
```

Sustituye `world` por el nombre exacto del mundo y ejecuta `/vt reload`. `Worlds` limita el evento a esos mundos; la
lista `main` contiene las instrucciones que se ejecutan. Para más eventos, revisa los archivos que genera el plugin en
`events/player/`, `events/entity/` y `events/system/`.

También puedes agregar líneas de evento con comandos:

```text
/vte add PlayerJoin main @PLAYER &a¡Bienvenido, <playername>!
/vte view PlayerJoin main
/vt reload
```

`/vte` es el alias de `/vtevent`. Consulta `/vte ?` para editar, ver o quitar scripts de eventos.

## Trigger de clic en un bloque

Los triggers de clic se guardan en una **ubicación concreta**, no en todos los bloques de un mismo material. Para
configurar uno:

1. Ten un hueso (`BONE`) en la mano.
2. Ejecuta `/vtc add` seguido de **una línea** de script.
3. Cuando indique que hagas clic, usa el hueso para hacer clic en el bloque que activará el trigger.
4. Repite para cada línea, haciendo clic en el mismo bloque cada vez.
5. Termina una condición `@IF` con `@ENDIF`.

Ejemplo que muestra el mensaje solo al hacer clic derecho:

```text
/vtc add @IF s <clicktype> = RIGHT_CLICK_BLOCK
/vtc add @PLAYER &a¡Clic derecho detectado!
/vtc add @ENDIF
```

Prueba el bloque con la mano vacía o con cualquier objeto: el hueso se utiliza para **configurar** el trigger. El evento
de clic normal se procesa desde la mano principal para no ejecutar la acción dos veces por la mano secundaria.

Comandos útiles para administrar triggers:

```text
/vtc add <línea del script>
/vtc view
/vtc remove
/vtc edit <índice> <línea nueva>
/vt reload
```

Después de `/vtc view`, `/vtc remove` o `/vtc edit`, haz clic con el hueso en el bloque elegido para seleccionar el
trigger. `/vtc` y `/vtclick` son alias de clic; `/vtw` y `/vtwalk` gestionan triggers de caminar.

Un trigger de clic puede revisar también el material de la ubicación. Por ejemplo:

```text
/vtc add @IF s <blockmaterial> = DIAMOND_BLOCK
/vtc add @PLAYER &b¡Este bloque es de diamante!
/vtc add @ENDIF
```

Los valores posibles de `<clicktype>` para un bloque son `RIGHT_CLICK_BLOCK` y `LEFT_CLICK_BLOCK`. Las comparaciones
`@IF s ... = ...` no distinguen mayúsculas; `==` es una comparación exacta. Otras ayudas del comando son `/vtc ?` y
`/vtw ?`.

## Otros tipos de triggers

| Tipo | Comandos | Uso |
|---|---|---|
| Evento | `/vte ?` | Consultar, agregar, editar, ver o quitar instrucciones de eventos como `PlayerJoin` |
| Comando | `/vtcmd ?` | Crear y administrar triggers ligados a comandos de jugador |
| Área | `/vta ?` | Activar selección de posiciones, definir áreas y consultar el área actual |
| Caminar | `/vtw ?` | Administrar triggers activados al caminar sobre una ubicación |

Para definir un área, activa la selección con `/vta set`, ponte en modo supervivencia, marca sus esquinas con clic
izquierdo y derecho usando un hueso, y guárdala con `/vta define <nombre>`. Consulta `/vta ?` para más detalles.

## Directivas comunes de scripts

Cada línea ejecuta una directiva. Los colores clásicos con `&` y los colores hexadecimales `&#RRGGBB` están disponibles
en mensajes y textos compatibles.

| Directiva | Ejemplo | Descripción |
|---|---|---|
| `@PLAYER <texto>` | `@PLAYER &aHola, <playername>` | Envía un mensaje al jugador que ejecuta el script |
| `@BROADCAST <texto>` | `@BROADCAST &eAnuncio del servidor` | Envía un mensaje a todos |
| `@TELL <jugador> <texto>` | `@TELL Alex &bHola` | Envía un mensaje a un jugador conectado |
| `@CALL <archivo>:<script>` | `@CALL Bienvenida:bienvenida` | Ejecuta un script definido en `scripts/` |
| `@SET <variable> <valor>` | `@SET $jugador.visitas 1` | Guarda una variable |
| `@ADDINT <variable> <número>` | `@ADDINT $jugador.visitas 1` | Incrementa una variable numérica |
| `@SUBINT <variable> <número>` | `@SUBINT $jugador.visitas 1` | Decrementa una variable numérica |
| `@IF s <valor> = <valor>` | `@IF s <clicktype> = RIGHT_CLICK_BLOCK` | Ejecuta condicionalmente hasta `@ENDIF` |
| `@SCOREBOARD TITLE <texto>` | `@SCOREBOARD TITLE &6&lServidor` | Cambia el título del scoreboard del jugador |
| `@SCOREBOARD LINE <1-15> <texto>` | `@SCOREBOARD LINE 1 &aEn línea` | Establece una línea del scoreboard |
| `@SCOREBOARD REMOVE` | `@SCOREBOARD REMOVE` | Quita el scoreboard administrado por el plugin |
| `@OPENINV <nombre> [jugador]` | `@OPENINV tienda` | Abre un inventario definido en `inventories/` |
| `@CLOSEINV <jugador>` | `@CLOSEINV Alex` | Cierra el inventario abierto del jugador indicado |
| `@MODIFYINV SET <ranura> <material> [cantidad] [nombre]` | `@MODIFYINV SET 13 DIAMOND 1 &bPremio` | Cambia una ranura del inventario superior abierto |
| `@MODIFYINV REMOVE <ranura>` | `@MODIFYINV REMOVE 13` | Vacía una ranura del inventario superior abierto |
| `@MODIFYINV CLEAR` | `@MODIFYINV CLEAR` | Vacía el inventario superior abierto |
| `@CMD <comando>` | `@CMD me saluda` | Ejecuta el comando como el jugador del script |
| `@CMDOP <comando>` | `@CMDOP gamemode creative` | Ejecuta el comando como el jugador del script, temporalmente como operador |
| `@CMDCON <comando>` | `@CMDCON say Hola` | Ejecuta el comando como consola |
| `@EXIT` | `@EXIT` | Termina el script actual |

Las directivas de inventario afectan al jugador del script por defecto. Para apuntar a otro jugador conectado, pon su
nombre después de la directiva; por ejemplo, `@SCOREBOARD Alex TITLE &6Servidor` o
`@MODIFYINV Alex CLEAR`. El scoreboard permite hasta 15 líneas.

## Variables y placeholders

Las variables se escriben con `$` y los placeholders entre `< >`. Algunos placeholders integrados:

| Placeholder | Valor |
|---|---|
| `<playername>` | Nombre del jugador |
| `<playerdisplayname>` | Nombre visible del jugador |
| `<worldname>` | Mundo asociado al trigger |
| `<playerloc>` | Mundo y coordenadas del jugador |
| `<triggerloc>` | Mundo y coordenadas del trigger |
| `<health>` | Salud actual |
| `<gamemode>` | Modo de juego |
| `<helditemname>` | Material del objeto en la mano |
| `<onlineplayeramount>` | Cantidad de jugadores conectados |
| `<clicktype>` | Acción del clic en triggers de bloque o aire |
| `<blockmaterial>` | Material del bloque en un trigger de clic |

Ejemplo de variable numérica:

```text
@SET $stats.visitas 0
@ADDINT $stats.visitas 1
@PLAYER &eVisitas registradas: $stats.visitas
```

Otros placeholders están listados en `resources/placeholders.yml`. Los placeholders específicos de un evento están
disponibles en el script asociado a ese evento.

## Inventarios personalizados

Crea `plugins/VariableTriggers3/inventories/tienda.yml`:

```yaml
title: '&6Tienda'
slots: 27
items:
  '13':
    type: DIAMOND
    amount: 1
    meta: 0
    name: '&bPremio'
    lore:
      - '&7Un diamante de ejemplo'
```

Abre el inventario desde un script:

```text
@OPENINV tienda
```

`slots` debe ser múltiplo de 9, entre 9 y 54. Los materiales se nombran con los nombres de Paper, por ejemplo
`CHERRY_PLANKS` o `minecraft:cherry_planks`. Para modificarlo, usa `@MODIFYINV` mientras ese inventario esté abierto.

## Diagnóstico y permisos

- Confirma en la consola que el plugin se cargó correctamente y revisa `plugins/VariableTriggers3/`.
- Ejecuta `/vt reload` tras editar scripts, eventos o triggers.
- Si un trigger de bloque no reacciona, verifica que está en la ubicación seleccionada, que `Worlds` contiene el mundo
  correcto para el evento, y que la condición usa el valor correcto de `<clicktype>`.
- Ejecuta `/vt debug` para activar o desactivar mensajes de diagnóstico.
- Permisos relevantes: `vtriggers.admin` para administración, `vtriggers.create.click`, `vtriggers.create.walk`,
  `vtriggers.create.event`, `vtriggers.create.area`, `vtriggers.create.command` y `vtriggers.use.*`.
- La configuración de permisos predeterminada está en `resources/plugin.yml`; los permisos de creación normalmente
  requieren ser operador o que un administrador los conceda.

Los cambios en archivos se aplican al ejecutar `/vt reload`; los cambios en el código requieren compilar y reinstalar el
JAR.
