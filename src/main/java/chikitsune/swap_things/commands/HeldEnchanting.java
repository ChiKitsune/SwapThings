package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.*;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class HeldEnchanting {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext cmdBuildContext) {
  return Commands.literal("heldenchanting").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return heldEnchantingLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone",null,null,cmdBuildContext);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return heldEnchantingLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone",null,null,cmdBuildContext);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return heldEnchantingLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"),null,null,cmdBuildContext);
      }).then(Commands.argument("enchantment", ResourceArgument.resource(cmdBuildContext, Registries.ENCHANTMENT)).executes((cmd_3arg) -> {
       return heldEnchantingLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"),ResourceArgument.getEnchantment(cmd_3arg, "enchantment"),null,cmdBuildContext);
      }).then(Commands.argument("enchantment_level",IntegerArgumentType.integer()).executes((cmd_4arg) -> {
       return heldEnchantingLogic(cmd_4arg.getSource(),EntityArgument.getPlayers(cmd_4arg, "targetedPlayer"),StringArgumentType.getString(cmd_4arg, "fromName"),ResourceArgument.getEnchantment(cmd_4arg, "enchantment"),IntegerArgumentType.getInteger(cmd_4arg, "enchantment_level"),cmdBuildContext);
      })
     ))));
 }
 
 private static int heldEnchantingLogic(CommandSourceStack source, Collection<ServerPlayer> targetPlayers, String fromName, Holder<Enchantment> enchInput, Integer enchLevel, CommandBuildContext cmdBuildContext) {
  ArchCommand.ReloadConfig();
  ItemStack tempStack=ItemStack.EMPTY;
  Enchantment tempEnch;
  Integer iCnt=0,tempEnchLevel=0;
  
  for(ServerPlayer targetedPlayer : targetPlayers) {
   tempStack=targetedPlayer.getMainHandItem();
   
   if (tempStack.isEmpty()) continue;
   
   if (enchInput!=null) {
    tempEnch=enchInput.value();
   } else {
    iCnt=0;
    do {
     tempEnch=(Enchantment)ForgeRegistries.ENCHANTMENTS.getValues().toArray()[rand.nextInt(ForgeRegistries.ENCHANTMENTS.getValues().size())];
    iCnt++;
    } while (!tempEnch.canEnchant(tempStack) && iCnt<50);
   }
   
   if (enchLevel==null) {
    tempEnchLevel=rand.nextInt(tempEnch.getMaxLevel()+1);
   } else {
    tempEnchLevel=enchLevel;
   }
   if (tempEnchLevel<0) tempEnchLevel=0;
   
   Map<Enchantment, Integer> itemEnchants=EnchantmentHelper.getEnchantments(tempStack);
   Map<Enchantment, Integer> itemEnchantsNew=Maps.<Enchantment, Integer>newLinkedHashMap();
   
   for (Map.Entry<Enchantment, Integer> curMapEnch : itemEnchants.entrySet()) {
    if (ForgeRegistries.ENCHANTMENTS.getKey(curMapEnch.getKey())!=ForgeRegistries.ENCHANTMENTS.getKey(tempEnch)) {
     itemEnchantsNew.put(curMapEnch.getKey(), curMapEnch.getValue());
    }
   }
   
   if (itemEnchants.get(tempEnch)==null) {
    if (tempEnchLevel>0) {
     tempStack.enchant(tempEnch, tempEnchLevel);
     ArchCommand.playerMsger(source, targetPlayers, 
       Component.literal(targetedPlayer.getName().getString() + "'s ").withStyle(ChatFormatting.RED)
       .append(Component.literal(tempStack.getHoverName().getString()).withStyle(ChatFormatting.AQUA))
       .append(Component.literal(" has gained a new power: ").withStyle(ChatFormatting.GOLD))
       .append(Component.literal(tempEnch.getFullname(tempEnchLevel).getString()).withStyle(ChatFormatting.DARK_GREEN))
       .append(Component.literal(". Blame " + fromName).withStyle(ChatFormatting.GOLD)));
//     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + "'s " + ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + " has gained a new power: " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(tempEnchLevel).getString() + ChatFormatting.GOLD + ". Blame " + fromName));
    } else {
     ArchCommand.playerMsger(source, targetPlayers, 
       Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED)
       .append(Component.literal(" was convinced by " + fromName + " that they didn't need ").withStyle(ChatFormatting.GOLD))
       .append(Component.literal(tempEnch.getFullname(1).getString()).withStyle(ChatFormatting.DARK_GREEN))
       .append(Component.literal(" on their ").withStyle(ChatFormatting.GOLD))
       .append(Component.literal(tempStack.getHoverName().getString()).withStyle(ChatFormatting.AQUA))
       .append(Component.literal(" anymore but then remembered they didn't have it anyways.").withStyle(ChatFormatting.GOLD)));
//     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " was convinced by " + fromName + " that they didn't need " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(1).getString() + ChatFormatting.GOLD + " on their " +  ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + " anymore but then remembered they didn't have it anyways."));
    }
   } else {
    if (tempEnchLevel==0) {
     //remove enchantment
     EnchantmentHelper.setEnchantments(itemEnchantsNew,tempStack);
     ArchCommand.playerMsger(source, targetPlayers, 
       Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED)
       .append(Component.literal(" was convinced by " + fromName + " that they didn't need ").withStyle(ChatFormatting.GOLD))
       .append(Component.literal(tempEnch.getFullname(itemEnchants.get(tempEnch)).getString()).withStyle(ChatFormatting.DARK_GREEN))
       .append(Component.literal(" on their ").withStyle(ChatFormatting.GOLD))
       .append(Component.literal(tempStack.getHoverName().getString()).withStyle(ChatFormatting.AQUA))
       .append(Component.literal(" anymore.").withStyle(ChatFormatting.GOLD)));
//     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " was convinced by " + fromName + " that they didn't need " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(itemEnchants.get(tempEnch)).getString() + ChatFormatting.GOLD + " on their " +  ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + " anymore."));
    } else if (tempEnchLevel==itemEnchants.get(tempEnch)) {
     //same level so nothing is done
     ArchCommand.playerMsger(source, targetPlayers, 
       Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED)
       .append(Component.literal(" wasn't convinced by " + fromName + " to get rid of ").withStyle(ChatFormatting.GOLD))
       .append(Component.literal(tempEnch.getFullname(itemEnchants.get(tempEnch)).getString()).withStyle(ChatFormatting.DARK_GREEN))
       .append(Component.literal(" on their ").withStyle(ChatFormatting.GOLD))
       .append(Component.literal(tempStack.getHoverName().getString()).withStyle(ChatFormatting.AQUA))
       .append(Component.literal(" .").withStyle(ChatFormatting.GOLD)));
//     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " wasn't convinced by " + fromName + " to get rid of " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(itemEnchants.get(tempEnch)).getString() + ChatFormatting.GOLD + " on their " +  ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + "."));
     
    } else {
     itemEnchantsNew.put(tempEnch, tempEnchLevel);
     EnchantmentHelper.setEnchantments(itemEnchantsNew,tempStack);
     if (tempEnchLevel>itemEnchants.get(tempEnch)) {
      ArchCommand.playerMsger(source, targetPlayers, 
        Component.literal(targetedPlayer.getName().getString() + "'s ").withStyle(ChatFormatting.RED)
        .append(Component.literal(tempStack.getHoverName().getString()).withStyle(ChatFormatting.AQUA))
        .append(Component.literal(" has increased to ").withStyle(ChatFormatting.GOLD))
        .append(Component.literal(tempEnch.getFullname(tempEnchLevel).getString()).withStyle(ChatFormatting.DARK_GREEN))
        .append(Component.literal(". Jeez thanks " + fromName).withStyle(ChatFormatting.GOLD)));
//      ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + "'s " + ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + " has increased to " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(tempEnchLevel).getString() + ChatFormatting.GOLD + ". Jeez thanks " + fromName));
      } else {
       ArchCommand.playerMsger(source, targetPlayers, 
         Component.literal(targetedPlayer.getName().getString() + "'s ").withStyle(ChatFormatting.RED)
         .append(Component.literal(tempStack.getHoverName().getString()).withStyle(ChatFormatting.AQUA))
         .append(Component.literal(" has decreased to ").withStyle(ChatFormatting.GOLD))
         .append(Component.literal(tempEnch.getFullname(tempEnchLevel).getString()).withStyle(ChatFormatting.DARK_GREEN))
         .append(Component.literal(". Woot! Thanks " + fromName + "!").withStyle(ChatFormatting.GOLD)));
//       ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + "'s " + ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + " has decreased to " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(tempEnchLevel).getString() + ChatFormatting.GOLD + ". Woot! Thanks " + fromName + "!")); 
      }
    }
   }
  }
  
  return 0;
 }

}
