# 1.14.4 Minecraft Forge Mod Created for Darkphan

Mod created for Darkphan to work with CCI for some Twitch Integration.
 Mainly adds commands to swap inventory items or location with a random other player.

#### Server Commands:
      - Note: arguments in <> mean they are required while [] are optional.
      - Note: All playername arguments are optional. First playername will attempt to use who did the command else will use @r.
      - Note: Any commands with two players will attempt to get a different second playername than the first unless it isn't randomly choosen. However it will only attempt to get a different one 10 * currently only players number of times. If the player name is still the same will use as is.
      - Note: All commands can be used regardless of if there is only one player on or several. Although, most swap commands will not show a visible difference other than a message in chat. 
      
#####  ```/swapthings quickhide [playername] [<item> <message>]```
    Throws the four basic armor slots, offhand, and held item onto the ground. Then replaces all of those slots with the item given or if none given a random one in the below list and displays a message to all players on server.
      - Currently these are the choices it will choose from at random (will change into a config in future)
         - minecraft:deadbush, quick hide in these bushes!
         - minecraft:wheat, quick hide in the wheat field!
         - minecraft:feather, quick act like a chicken!
         - minecraft:painting, quick blend into the wall!
      - Note: If want do your own item and message playername is required
      
#####  ```/swapthings swaparmor <head|chest|legs|feet|mainhand|offhand|all|set|random> [playername] [playername]```
    Based on the option given will swap armor with another player. 
    - ALL will swap all six of the options before it (four basic armor, mainhand, and offhand) at once. 
    - SET will swap all of the four basic armor slots. 
    - RANDOM will choose a random single option (helm, chest, legs, boots, mainhand, or offhand) from the list.
      - Note: RANDOM is likely to choose different slots for the two players. It prefers non empty slots but it is possible to switch boots with a helm this way.

#####  ```/swapthings swaplocations [playername] [playername]```
    Swaps the location of the two players with each other.
      - Note: Players will also be set to look in the direction the other person was looking

#####  ```/swapthings shufflehotbar [playername] [courtesy of]```
    Goes through all of the hotbar slots and randomly swaps it with another hotbar slot.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings shuffleinventory [playername] [courtesy of]```
    Goes through all of the hotbar, equipment, and inventory slots and randomly swaps it with another slot.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.
