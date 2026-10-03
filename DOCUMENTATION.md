# VariableTriggers3 (VTV3) — English documentation

A practical guide to installing, configuring, and using VariableTriggers3 on Paper. The plugin runs scripts in response
to events, commands, block interactions, and regions.

## Requirements and installation

- Paper 1.21.x–26.3 and Java 21–25.
- Copy `VariableTrigger3-1.0.jar` into the server's `plugins/` folder and restart the server.
- The plugin name shown by Paper is `VariableTriggers3`; its message prefix is `VTV3`.
- Plugin files and data are created under `plugins/VariableTriggers3/`. If the legacy `plugins/VariableTriggers/` folder
  exists, startup migrates it or merges files that are missing from the new folder. Keep the legacy folder as a backup.

Common paths:

| Path | Contents |
|---|---|
| `plugins/VariableTriggers3/scripts/` | Reusable scripts (`.script.yml`) |
| `plugins/VariableTriggers3/events/player/` | Player event scripts, such as `PlayerJoin.yml` |
| `plugins/VariableTriggers3/events/entity/` | Entity event scripts |
| `plugins/VariableTriggers3/events/system/` | Server event scripts |
| `plugins/VariableTriggers3/events/triggers/ClickTriggers.yml` | Block click triggers |
| `plugins/VariableTriggers3/events/triggers/WalkTriggers.yml` | Walk-location triggers |
| `plugins/VariableTriggers3/inventories/` | Inventories defined in YAML |
| `plugins/VariableTriggers3/system/` | Settings and saved variables |

## Reload and help

```text
/vt ?
/vt core
/vt reload
```

`/vt reload` reloads scripts and trigger files without restarting the server. Run it after editing files manually.
Additional subcommands include `/vt rt` to reload triggers, `/vt rs` to reload scripts, `/vt st` and `/vt ss` to save
triggers and scripts, and `/vt sv` to save variables.

## Create a reusable script

Create `plugins/VariableTriggers3/scripts/Welcome.script.yml`:

```yaml
Scripts:
  welcome:
    Script:
      - '@PLAYER &aHello, <playername>!'
      - '@PLAYER &eYou are in world &b<worldname>&e.'
      - '@SCOREBOARD TITLE &6&lMy server'
      - '@SCOREBOARD LINE 1 &fPlayer: &a<playername>'
      - '@SCOREBOARD LINE 2 &bWelcome!'
```

Keys under `Scripts:` are script identifiers. Run the script using the filename (without `.script.yml`) and identifier:

```text
/vt run Welcome:welcome
```

You can also manage scripts in-game:

```text
/vts add Welcome welcome @PLAYER &aHello, <playername>!
/vts add Welcome welcome @BROADCAST &e<playername> joined!
/vts view Welcome welcome
/vts edit Welcome welcome 0 @PLAYER &bUpdated message
/vts remove Welcome welcome
/vt ss
```

Run `/vts add` once per script line. `/vt ss` saves scripts managed in-game. After creating or editing scripts directly
in files, run `/vt reload`.

## Run a script when a player joins

Edit `plugins/VariableTriggers3/events/player/PlayerJoin.yml`:

```yaml
Worlds:
  - world
main:
  - '@CALL Welcome:welcome'
```

Replace `world` with the exact world name, then run `/vt reload`. `Worlds` limits the event to the listed worlds; `main`
contains the script instructions to execute. For other events, see the files generated under `events/player/`,
`events/entity/`, and `events/system/`.

You can also add event instructions in-game:

```text
/vte add PlayerJoin main @PLAYER &aWelcome, <playername>!
/vte view PlayerJoin main
/vt reload
```

`/vte` is an alias for `/vtevent`. Run `/vte ?` for event script editing, viewing, and removal commands.

## Create a block click trigger

Click triggers are tied to a **specific block location**, not every block of the same material. To configure one:

1. Hold a bone (`BONE`).
2. Run `/vtc add` followed by **one script line**.
3. When prompted, use the bone to click the block that should activate the trigger.
4. Repeat for each line, clicking the same block each time.
5. Close every `@IF` condition with `@ENDIF`.

This example shows a message only on right-click:

```text
/vtc add @IF s <clicktype> = RIGHT_CLICK_BLOCK
/vtc add @PLAYER &aRight-click detected!
/vtc add @ENDIF
```

Test the block with an empty hand or any item; the bone is only needed to **configure** the trigger. Normal click events
are processed from the main hand so the off-hand event does not run the action twice.

Useful trigger management commands:

```text
/vtc add <script line>
/vtc view
/vtc remove
/vtc edit <index> <new line>
/vt reload
```

After `/vtc view`, `/vtc remove`, or `/vtc edit`, click the target block with the bone to select its trigger. `/vtc` and
`/vtclick` are click aliases; `/vtw` and `/vtwalk` manage walk triggers.

A click trigger can also check the block material at that location:

```text
/vtc add @IF s <blockmaterial> = DIAMOND_BLOCK
/vtc add @PLAYER &bThis block is a diamond block!
/vtc add @ENDIF
```

Possible block `<clicktype>` values are `RIGHT_CLICK_BLOCK` and `LEFT_CLICK_BLOCK`. `@IF s ... = ...` comparisons are
case-insensitive; `==` compares exact case. See `/vtc ?` and `/vtw ?` for more help.

## Other trigger types

| Type | Commands | Use |
|---|---|---|
| Event | `/vte ?` | View, add, edit, or remove event instructions such as `PlayerJoin` |
| Command | `/vtcmd ?` | Create and manage player-command triggers |
| Area | `/vta ?` | Select positions, define regions, and check the current region |
| Walk | `/vtw ?` | Manage triggers activated by walking at a location |

To define an area, enable selection with `/vta set`, switch to survival mode, mark the corners with left- and right-click
using a bone, then save with `/vta define <name>`. See `/vta ?` for details.

## Common script directives

Each script line runs one directive. Legacy `&` colors and hex colors in `&#RRGGBB` format are supported in messages and
other compatible text.

| Directive | Example | Description |
|---|---|---|
| `@PLAYER <text>` | `@PLAYER &aHello, <playername>` | Sends a message to the player running the script |
| `@BROADCAST <text>` | `@BROADCAST &eServer announcement` | Sends a message to everyone |
| `@TELL <player> <text>` | `@TELL Alex &bHello` | Sends a message to an online player |
| `@CALL <file>:<script>` | `@CALL Welcome:welcome` | Runs a script from `scripts/` |
| `@SET <variable> <value>` | `@SET $player.visits 1` | Stores a variable |
| `@ADDINT <variable> <number>` | `@ADDINT $player.visits 1` | Increments a numeric variable |
| `@SUBINT <variable> <number>` | `@SUBINT $player.visits 1` | Decrements a numeric variable |
| `@IF s <value> = <value>` | `@IF s <clicktype> = RIGHT_CLICK_BLOCK` | Conditional block ending with `@ENDIF` |
| `@SCOREBOARD TITLE <text>` | `@SCOREBOARD TITLE &6&lServer` | Sets the player's scoreboard title |
| `@SCOREBOARD LINE <1-15> <text>` | `@SCOREBOARD LINE 1 &aOnline` | Sets a scoreboard line |
| `@SCOREBOARD REMOVE` | `@SCOREBOARD REMOVE` | Removes the scoreboard managed by the plugin |
| `@OPENINV <name> [player]` | `@OPENINV shop` | Opens an inventory defined under `inventories/` |
| `@CLOSEINV <player>` | `@CLOSEINV Alex` | Closes the specified player's open inventory |
| `@MODIFYINV SET <slot> <material> [amount] [name]` | `@MODIFYINV SET 13 DIAMOND 1 &bPrize` | Changes a slot in the open top inventory |
| `@MODIFYINV REMOVE <slot>` | `@MODIFYINV REMOVE 13` | Clears a slot in the open top inventory |
| `@MODIFYINV CLEAR` | `@MODIFYINV CLEAR` | Clears the open top inventory |
| `@CMD <command>` | `@CMD me waves` | Runs a command as the player running the script |
| `@CMDOP <command>` | `@CMDOP gamemode creative` | Temporarily runs a command as that player with operator permissions |
| `@CMDCON <command>` | `@CMDCON say Hello` | Runs a command as the console |
| `@EXIT` | `@EXIT` | Stops the current script |

Inventory directives target the player running the script by default. To target another online player, place their name
after the directive; for example, `@SCOREBOARD Alex TITLE &6Server` or `@MODIFYINV Alex CLEAR`. Scoreboards support up to
15 lines.

## Variables and placeholders

Variables start with `$`; placeholders are written inside angle brackets (`< >`). Common built-in placeholders:

| Placeholder | Value |
|---|---|
| `<playername>` | Player name |
| `<playerdisplayname>` | Player display name |
| `<worldname>` | World associated with the trigger |
| `<playerloc>` | Player world and coordinates |
| `<triggerloc>` | Trigger world and coordinates |
| `<health>` | Current health |
| `<gamemode>` | Game mode |
| `<helditemname>` | Material held by the player |
| `<onlineplayeramount>` | Number of online players |
| `<clicktype>` | Action for block or air click triggers |
| `<blockmaterial>` | Block material for a click trigger |

Numeric variable example:

```text
@SET $stats.visits 0
@ADDINT $stats.visits 1
@PLAYER &eRecorded visits: $stats.visits
```

Other placeholders are listed in `resources/placeholders.yml`. Event-specific placeholders are available in the script
for their associated event.

## Custom inventories

Create `plugins/VariableTriggers3/inventories/shop.yml`:

```yaml
title: '&6Shop'
slots: 27
items:
  '13':
    type: DIAMOND
    amount: 1
    meta: 0
    name: '&bPrize'
    lore:
      - '&7An example diamond'
```

Open the inventory from a script:

```text
@OPENINV shop
```

`slots` must be a multiple of 9 between 9 and 54. Use material names available in Paper, such as `CHERRY_PLANKS` or
`minecraft:cherry_planks`. To modify the inventory, use `@MODIFYINV` while it is open.

## Troubleshooting and permissions

- Confirm the plugin loaded in the console and check `plugins/VariableTriggers3/`.
- Run `/vt reload` after editing scripts, events, or trigger files.
- If a block trigger does not run, check that it is attached to the block you clicked, that the event's `Worlds` list
  contains the correct world, and that the `<clicktype>` condition uses the right value.
- Run `/vt debug` to toggle diagnostic messages.
- Relevant permissions include `vtriggers.admin`, `vtriggers.create.click`, `vtriggers.create.walk`,
  `vtriggers.create.event`, `vtriggers.create.area`, `vtriggers.create.command`, and `vtriggers.use.*`.
- Permission defaults are in `resources/plugin.yml`; creation permissions generally require operator status or an
  explicit grant from an administrator.

File edits take effect after `/vt reload`; code changes require building and reinstalling the JAR.
