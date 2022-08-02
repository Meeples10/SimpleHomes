# SimpleHomes

A small plugin that allows players to set homes.

## Commands

|Command|Description|Permission|
|-------|-----------|----------|
|`/home [name]`|Teleports you to a home. If no name is provided, the name *home* will be used.|`simplehomes.home`|
|`/sethome [name]`|Sets a home. If no name is provided, the home will be named *home*.|`simplehomes.sethome`|
|`/delhome <name>`|Deletes a home.|`simplehomes.delhome`|
|`/homes`|Lists your homes.|`simplehomes.homes`|

The permission `simplehomes.all` can be used to give players access to all SimpleHomes commands.

## Configuration

The default configuration file can be found [here](https://github.com/Meeples10/SimpleHomes/blob/master/src/main/resources/config.yml).

|Key|Description|
|---|-----------|
|`play-sound`|If true, a sound will be played when the player is teleported to their home.|
|`teleport-sound`|If play-sound is true, this sound will be played when a player uses `/home`. See [the Minecraft Wiki](https://minecraft.fandom.com/wiki/Sounds.json#Sound_events) for a list of available sounds.|
|`reset-velocity`|If true, the player's velocity will be reset when teleporting. This prevents fall damage if the player uses `/home` while falling.|
|`messages`|Messages displayed by the plugin.|
