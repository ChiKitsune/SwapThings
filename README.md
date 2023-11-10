# SwapThings a Minecraft Forge Mod (Mainly 1.20 being updated)

Mod mainly created to work with CCI/TwitchSpawn for some Twitch Integration. 
Adds commands for viewers to interact and make things more entertaining for the streamer.

#### Commands:
      - Note: arguments in <> mean they are required while [] are optional.
      - Note: All playername arguments are optional. First playername will attempt to use who did the command else will use @r.
      - Note: Any commands with two players will attempt to get a different second playername than the first unless it isn't randomly choosen. However it will only attempt to get a different one 10 * currently only players number of times. If the player name is still the same will use as is.
      - Note: All commands can be used regardless of if there is only one player on or several. Although, most swap commands will not show a visible difference other than a message in chat. 

#####  ```/swapthings disconnectplayer [playername] [courtesy of]```
    Disconnects the given player from the world or server
        - Note: Message on disconnect is controlled by the config value

#####  ```/swapthings displaydeathboard```
    Gets death count from the player Stats and list them in chat
        - Note: Message next to each name is controlled by the config value
        - Note: If one value is given will use it for all places. If there is at least two values separated by a comma will put messages in order
      
#####  ```/swapthings heldenchanting [playername] [courtesy of] [enchantment] [enchantment_level]```
    Adds/removes/updates held item with a given enchantment or random if none is given
        - Note: If an enchantment is not given will choose a random valid enchantment and level
        - Note: If an enchantment level is not given will choose a random valid one for the given enchantment (0 is possible which will remove the enchantment
        - Note: When specifying the enchantment level can do higher than usual (i.e. minecraft:efficiency 10 will give Efficiency X)

#####  ```/swapthings inventorybomb [playername] [courtesy of]```
    Drops and replaces all inventory items with the item specified in the config
      - Note: Default is minecraft:dead_bush or if config value is invalid

#####  ```/swapthings inventoryequalizer [playername] [item] [stack amount] [courtesy of]```
    Drops and replaces all inventory items with the item specified with each stack having the same amount
      - Note: Default item is minecraft:dead_bush and a stack size of 1/li>
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings inventoryslotclearer [playername] [amount] [courtesy of]```
    Clears a specific inventory slot based on the decimal place of the amount or random if not given one.
      - Note: Amount ignores the whole number amount and only looks for decimal places. https://i.imgur.com/5qokaCR.jpg
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
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings inventoryslotenchanting [playername] [amount] [courtesy of] [enchantment] [enchantment_level]```
    Enchants a specific inventory slot based on the decimal place of the amount or random if not given one.
      - Note: Amount ignores the whole number amount and only looks for decimal places. https://i.imgur.com/5qokaCR.jpg
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

#####  ```/swapthings inventoryslotrenamer [playername] [courtesy of] [amount]```
    Renames an inventory item to have the prefix of "[courtesy of]'s "
      - Note: Amount ignores the whole number amount and only looks for decimal places. https://i.imgur.com/5qokaCR.jpg
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
      - Note: If an amount is not given it will try to find a non empty inventory slot and use that
      - Note: If a courtesy of name is not given it will default to "Someone"

#####  ```/swapthings inventoryslotreplacer [player] [item] [slot num] [courtesy of]```
    drops and then replaces a specific inventory slot based on the decimal place of the amount or random if not given one.
      - Note: If no item is given it defaults to Air/empty slot
      - Note: slot num ignores the whole number amount and only looks for decimal places. https://i.imgur.com/5qokaCR.jpg
            -1 will try to choose a random non empty slot
             0 is held item
            01 - 09 is hotbar
            10 - 18 is top line in inventory
            19 - 27 is second line in inventory
            28 - 36 is third line in inventory
            37 is boots
            38 is leggings
            39 is chestplate
            40 is helmet
            41 is offhand
            42 and above is outside of the default player's inventory size and will loop back around to force it within the inventory size
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings inventoryslotunnamer [playername] [courtesy of] [amount]```
    Unnames an inventory item back to the default name
      - Note: Amount ignores the whole number amount and only looks for decimal places. https://i.imgur.com/5qokaCR.jpg
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
      - Note: If an amount is not given it will try to find a non empty inventory slot and use that

#####  ```/swapthings playernudger [playername] [courtesy of]```
    Nudges the player in a random direction
      - Note: Direction chances are based on the config chance values
      - Note: Strength is also in the config and default to .7 which will push the player one block in that direction
      - Note: Multiplier config values will times the normal direction chance by the multiplier based on what direction the player is facing. A 0 for either the Chance or Multiplier will never nudge that direction and a 1 is default.
      
#####  ```/swapthings playerrotate [playername] [courtesy of]```
    Forces the player to look at a random direction.
      
#####  ```/swapthings quickhide [playername] [<item> <message>]```
    Throws the four basic armor slots, offhand, and held item onto the ground. Then replaces all of those slots with the item given or if none given a random one in the below list and displays a message to all players on server.
      - Currently these are the choices it will choose from at random (will change into a config in future)
         - minecraft:deadbush, quick hide in these bushes!
         - minecraft:wheat, quick hide in the wheat field!
         - minecraft:feather, quick act like a chicken!
         - minecraft:painting, quick blend into the wall!
      - Note: If want do your own item and message playername is required
      
#####  ```/swapthings randomenchanting [playername] [courtesy of] [effecttype] [enchantment] [enchantment level]```
    Chooses a random item in inventory and gives, changes, or removes an enchantment at random
      - Note: effecttype can be a value of ANY, POSSITIVE, NEGATIVE, RANDOM.
         - ANY: will do from 0 - max normal level of the enchantment
         - POSSITIVE: will always increase the enchantment's level unless already at max
         - NEGATIVE: will always decrease level by at least one or if already at level one will remove enchantment.
         - RANDOM: will determine a random range and choose from that for what it will do.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.
      - Note: If an enchantment is not given will choose a random valid enchantment and level
      - Note: If an enchantment level is not given will choose a random valid one for the given enchantment (0 is possible which will remove the enchantment
      - Note: When specifying the enchantment level can do higher than usual (i.e. minecraft:efficiency 10 will give Efficiency X)

#####  ```/swapthings randomgift [playername] [courtesy of]```
    Gifts a random item based on the list in the configs
      - Note: Each list item contains three parts.
         - Item name/ Namespaced ID - i.e. minecraft:stone_button
         - Stack amount - Any number 1-64
         - Weighted chance number - if put 5 it means it will have five chances of choosing this item
      - Note: If want an equal chance of choosing can put the weighted chance number as 1 for all

#####  ```/swapthings randomteleport [playername] [courtesy of]```
    Teleports the player to a random location based on config min/max values for each axis.
#####  ```/swapthings randomteleport [playername] [courtesy of] [X_Min] [X_Max] [Y_Min] [Y_Max] [Z_Min] [Z_Max]```
    Teleports the player to a random location based on given numbers instead of the config min/max values for each axis.
      - Note: Min/Max values should all be positive as it is block distance amounts

#####  ```/swapthings randomteleportdirection [playername] [courtesy of] [distance]```
    Teleports user in a random direction with the given amount as distance from their original spot.
      - Note: If no distance is given will use the config value
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings replacearmorpiece [playername] [head|chest|legs|feet|mainhand|offhand|random] [item] [courtesy of]```
    Drops and replaces armor in the given slot with the given item
      - Note: DRANDOM will choose a random single option (helm, chest, legs, boots, mainhand, or offhand) from the list.
      - Note: DIf no item is given it defaults to a dead bush

#####  ```/swapthings shufflehotbar [playername] [courtesy of]```
    Goes through all of the hotbar slots and randomly swaps it with another hotbar slot.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings shuffleinventory [playername] [courtesy of]```
    Goes through all of the hotbar, equipment, and inventory slots and randomly swaps it with another slot.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings shuffleinventorynames [playername] [courtesy of]```
    Goes through all of the hotbar, equipment, and inventory slots and randomly swaps the item names with another slot.
      - Note: Courtesy of is an optional text string. If nothing is given will just shuffle item names around.
      - Note: If Courtesy of is given it will prefix all shuffled item names with what is given plus 's

#####  ```/swapthings summonmount [playername] [courtesy of] [mount]```
    Summons a random Entity and makes the player ride it.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.
      - Note: If a mount is given in the command it will summon that mount instead of a random one.

#####  ```/swapthings summonrider  [playername] [courtesy of] [mount]```
    Summons a random Entity and makes it ride the player.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.
      - Note: If a mount is given in the command it will summon that mount instead of a random one.
      - Note: Some cases may need to use the UnMounter command or a death to remove it.
      
#####  ```/swapthings swaparmor <head|chest|legs|feet|mainhand|offhand|all|set|random> [playername] [playername]```
    Based on the option given will swap armor with another player. 
    - ALL will swap all six of the options before it (four basic armor, mainhand, and offhand) at once. 
    - SET will swap all of the four basic armor slots. 
    - RANDOM will choose a random single option (helm, chest, legs, boots, mainhand, or offhand) from the list.
      - Note: RANDOM is likely to choose different slots for the two players. It prefers non empty slots but it is possible to switch boots with a helm this way.
      
#####  ```/swapthings swaphands [playername] [courtesy of]```
    Swaps the held and offhand items
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings swapidentity [playername] [playername]```
    Swaps the location, inventory, and experience of the two players with each other and drops any helmets equipped, and equips the skull of the other player. 
      - Note: Players will also be set to look in the direction the other person was looking

#####  ```/swapthings swapinventorynames  [playername] [courtesy of]```
    Goes through all of the hotbar, equipment, and inventory slots and randomly replaces the name with another player's item name or a random item name. 
      - Note: Courtesy of is an optional text string. If nothing is given will just shuffle item names around.
      - Note: If "Courtesy of" is given it will prefix all shuffled item names with what is given plus 's

#####  ```/swapthings swaplocation [playername] [playername]```
    Swaps the location of the two players with each other.
      - Note: Players will also be set to look in the direction the other person was looking

#####  ```/swapthings togglecrouch [playername] [courtesy of]```
    Toggles if the player is crouching until command is run again, player presses their crouch key, or opens chat.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.
      
#####  ```/swapthings togglerun [playername] [courtesy of]```
    Toggles if the player is running until command is run again, player presses their sprint key, or stops.
      - Note: Courtesy of is an optional text string if want the message displayed in chat to have a username in it. If none is given will just use "someone" instead.

#####  ```/swapthings unmounter  [playername] [courtesy of]```
    Unmounts whatever the player is riding on or that is riding on the player
    
#####  ```/swapthings unshuffleinventorynames [playername] [courtesy of]```
    Goes through all of the inventory slots and removes all custom names