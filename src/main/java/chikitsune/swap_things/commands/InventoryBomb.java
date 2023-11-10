package chikitsune.swap_things.commands;

import chikitsune.swap_things.config.Configs;
import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class InventoryBomb {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("inventorybomb").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return inventoryBombLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return inventoryBombLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return inventoryBombLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
 
 private static int inventoryBombLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
  ArchCommand.ReloadConfig();
  ItemStack defItemStack=ItemStack.EMPTY;
  ItemStack tempItemStack=ItemStack.EMPTY;

  defItemStack=new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Configs.IB_ITEM.get())));
  if (defItemStack.isEmpty()) defItemStack=new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:dead_bush")));
  defItemStack.setCount(defItemStack.getMaxStackSize());
  
  List<NonNullList<ItemStack>> allInventories;
  for(ServerPlayer targetedPlayer : targetPlayers) {
   allInventories = ImmutableList.of(targetedPlayer.getInventory().items, targetedPlayer.getInventory().armor, targetedPlayer.getInventory().offhand);
   for(List<ItemStack> list : allInventories) {
    for(int i = 0; i < list.size(); ++i) {
     tempItemStack = list.get(i);
       if (!tempItemStack.isEmpty()) {
//        targetedPlayer.dropItem(tempItemStack, true, false);
        ArchCommand.ibDropItem(tempItemStack,targetedPlayer);
       }
       list.set(i, defItemStack.copy());
    }
 }
   ArchCommand.playerMsger(source, targetPlayers, 
     Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED)
   .append(Component.literal(" just found out what their inventory would look like if it only had ").withStyle(ChatFormatting.GOLD))
   .append(Component.literal(defItemStack.getHoverName().getString()).withStyle(ChatFormatting.DARK_GREEN))
   .append(Component.literal(" in it.").withStyle(ChatFormatting.GOLD)));
//   ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " just found out what their inventory would look like if it only had " + ChatFormatting.DARK_GREEN + defItemStack.getHoverName().getString() + ChatFormatting.GOLD + " in it."));
  }
  
  return 0;
 }
 
}
