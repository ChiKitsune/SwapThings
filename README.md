# SwapThings a 1.14.4 Minecraft Forge Mod

Mod created for Darkphan to work with CCI/TwitchSpawn for some Twitch Integration.
 Mainly adds commands for viewers to interact and make things more entertaining for the streamer.

#### Commands:
      - Note: arguments in <> mean they are required while [] are optional.
      - Note: All playername arguments are optional. First playername will attempt to use who did the command else will use @r.
      - Note: Any commands with two players will attempt to get a different second playername than the first unless it isn't randomly choosen. However it will only attempt to get a different one 10 * currently only players number of times. If the player name is still the same will use as is.
      - Note: All commands can be used regardless of if there is only one player on or several. Although, most swap commands will not show a visible difference other than a message in chat. 
      
#####  ```/swapthings heldenchanting [playername] [courtesy of] [enchantment] [enchantment_level]```
    Adds/removes/updates held item with a given enchantment or random if none is given
        - Note: If an enchantment is not given will choose a random valid enchantment and level
        - Note: If an enchantment level is not given will choose a random valid one for the given enchantment (0 is possible which will remove the enchantment
        - Note: When specifying the enchantment level can do higher than usual (i.e. minecraft:efficiency 10 will give Efficiency X)

#####  ```/swapthings inventoryslotclearer [playername] [amount] [courtesy of]```
    Clears a specific inventory slot based on the decimal place of the amount or random if not given one.
      - Note: Amount ignores the whole number amount and only looks for decimal places.
                .00 - .08 is hotbar
                .09 - .17 is top line in inventory
                .18 - .26 is second line in inventory
                .27 - .35 is third line in inventory
                .36 is boots
                .37 is leggings
                .38 is chestplate
                .39 is helmet
                .40 is offhand
                .41 and above is outside of the default player's inventory size
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings inventoryslotenchanting [playername] [amount] [courtesy of] [enchantment] [enchantment_level]```
    Enchants a specific inventory slot based on the decimal place of the amount or random if not given one.
      - Note: Amount ignores the whole number amount and only looks for decimal places.
            .00 is held item
            .01 - .09 is hotbar
            .10 - .18 is top line in inventory
            .19 - .27 is second line in inventory
            .28 - .36 is third line in inventory
            .37 is boots
            .38 is leggings
            .39 is chestplate
            .40 is helmet
            .41 is offhand
            .42 and above is outside of the default player's inventory size
      - Note: If an enchantment is not given will choose a random valid enchantment and level
      - Note: If an enchantment level is not given will choose a random valid one for the given enchantment (0 is possible which will remove the enchantment
      - Note: When specifying the enchantment level can do higher than usual (i.e. minecraft:efficiency 10 will give Efficiency X)

#####  ```/swapthings playernudger [playername] [courtesy of]```
    Nudges the player in a random direction
      - Note: Direction chances are based on the config chance values
      - Note: Strength is also in the config and default to .7 which will push the player one block in that direction
      - Note: Multiplier config values will times the normal direction chance by the multiplier based on what direction the player is facing. A 0 for either the Chance or Multiplier will never nudge that direction and a 1 is default.
      
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
      
#####  ```/swapthings swaphands [playername] [courtesy of]```
    Swaps the held and offhand items
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings swaplocations [playername] [playername]```
    Swaps the location of the two players with each other.
      - Note: Players will also be set to look in the direction the other person was looking

#####  ```/swapthings shufflehotbar [playername] [courtesy of]```
    Goes through all of the hotbar slots and randomly swaps it with another hotbar slot.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings shuffleinventory [playername] [courtesy of]```
    Goes through all of the hotbar, equipment, and inventory slots and randomly swaps it with another slot.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings togglecrouch [playername] [courtesy of]```
    Toggles if the player is crouching until command is run again, player presses their crouch key, or opens chat.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.
      
#####  ```/swapthings togglerun [playername] [courtesy of]```
    Toggles if the player is running until command is run again, player presses their sprint key, or stops.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.
