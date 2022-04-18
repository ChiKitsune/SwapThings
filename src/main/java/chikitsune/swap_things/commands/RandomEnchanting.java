package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.commands.arguments.RandomEffectType;
import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ItemEnchantmentArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class RandomEnchanting {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("randomenchanting").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return randomEnchantingLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone","ANY",null,null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return randomEnchantingLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone","ANY",null,null);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return randomEnchantingLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"),"ANY",null,null);
     }).then(Commands.argument("randomEffectType", RandomEffectType.randomEffectType()).executes((cmd_3arg) -> {
      return randomEnchantingLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"),RandomEffectType.getRandomEffectType(cmd_3arg,"randomEffectType"),null,null); 
      }).then(Commands.argument("enchantment",ItemEnchantmentArgument.enchantment()).executes((cmd_4arg) -> {
       return randomEnchantingLogic(cmd_4arg.getSource(),EntityArgument.getPlayers(cmd_4arg, "targetedPlayer"),StringArgumentType.getString(cmd_4arg, "fromName"),RandomEffectType.getRandomEffectType(cmd_4arg,"randomEffectType"),ItemEnchantmentArgument.getEnchantment(cmd_4arg, "enchantment"),null);
      }).then(Commands.argument("enchantment_level",IntegerArgumentType.integer()).executes((cmd_5arg) -> {
       return randomEnchantingLogic(cmd_5arg.getSource(),EntityArgument.getPlayers(cmd_5arg, "targetedPlayer"),StringArgumentType.getString(cmd_5arg, "fromName"),RandomEffectType.getRandomEffectType(cmd_5arg,"randomEffectType"),ItemEnchantmentArgument.getEnchantment(cmd_5arg, "enchantment"),IntegerArgumentType.getInteger(cmd_5arg, "enchantment_level"));
      })
     )))));
 }
 
 private static int randomEnchantingLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName, String randomEffectType, Enchantment enchInput, Integer enchLevel) {
  ArchCommand.ReloadConfig();
  ItemStack tempStack=ItemStack.EMPTY;
  Enchantment tempEnch;
  Integer iCnt=0,tempEnchLevel=0,tempRandNum=0,tempRandEnchMin=0,tempRandEnchMax=1;
  Map<Enchantment, Integer> itemEnchants=null;
  Map<Enchantment, Integer> itemEnchantsNew=Maps.<Enchantment, Integer>newLinkedHashMap();
  List<Enchantment> lstPossEnch = ForgeRegistries.ENCHANTMENTS.getValues().stream()
//    .filter((Enchantment e) -> e.category
//    .filter((EntityType eT) -> !ArchCommand.GetSM_EXT_LIST().contains(eT.getRegistryName().toString()))
//    .filter((EntityType eT) -> ArchCommand.GetSM_INC_LIST().contains(eT.getCategory()))
    .collect(Collectors.toList());
  
  for(ServerPlayer targetedPlayer : targetPlayers) {
   rand= new Random();
   itemEnchants=null;
   itemEnchantsNew=Maps.<Enchantment, Integer>newLinkedHashMap();
      
   iCnt=0;
   do {
    tempRandNum=rand.nextInt(targetedPlayer.getInventory().getContainerSize());
    iCnt++;
   } while (targetedPlayer.getInventory().getItem(tempRandNum).isEmpty() || iCnt<50);
   
   tempStack=targetedPlayer.getInventory().getItem(tempRandNum);
   
   if (tempStack.isEmpty()) continue;
   itemEnchants=EnchantmentHelper.getEnchantments(tempStack);
   
   
   if (enchInput!=null) {
    tempEnch=enchInput;
   } else {
    iCnt=0;
    do {
     tempEnch=(Enchantment)ForgeRegistries.ENCHANTMENTS.getValues().toArray()[rand.nextInt(ForgeRegistries.ENCHANTMENTS.getValues().size())];
    iCnt++;
    } while (!tempEnch.canEnchant(tempStack) && iCnt<50);
   }
   
   if (enchLevel==null) {
//    System.out.println("tempEnch: "+tempEnch.getRegistryName()+" max level: "+tempEnch.getMaxLevel());
    Integer curLevel=itemEnchants.get(tempEnch)!=null ? itemEnchants.get(tempEnch) : 0;
    
    switch (randomEffectType.toUpperCase()) {
     case "ANY": tempRandEnchMin=0;tempRandEnchMax=tempEnch.getMaxLevel();
      break;
     case "POSSITIVE": 
      tempRandEnchMin=curLevel+1>tempEnch.getMaxLevel() ? tempEnch.getMaxLevel() : curLevel+1;
      tempRandEnchMax=tempEnch.getMaxLevel();
      break;
     case "NEGATIVE":tempRandEnchMin=0;tempRandEnchMax= curLevel-1<0 ? 0 : curLevel-1;
     break;
     case "RANDOM":
     default:tempRandEnchMax=rand.nextInt(tempEnch.getMaxLevel()+1);tempRandEnchMin=rand.nextInt(tempRandEnchMax+1);
//   Integer tempMinRandNum=rand.nextInt(tempRandEnchMax+1); tempRandEnchMin=tempMinRandNum<0 ? 0 : tempMinRandNum;
      break;
    }
//    System.out.println("tempRandEnchMin: "+tempRandEnchMin+" tempRandEnchMax: "+tempRandEnchMin);
    
    tempEnchLevel=rand.nextInt(tempRandEnchMax-tempRandEnchMin+1)+tempRandEnchMin;
   } else {
    tempEnchLevel=enchLevel;
   }
   if (tempEnchLevel<0) tempEnchLevel=0;
   

   
   for (Map.Entry<Enchantment, Integer> curMapEnch : itemEnchants.entrySet()) {
    if (curMapEnch.getKey().getRegistryName()!=tempEnch.getRegistryName()) {
     itemEnchantsNew.put(curMapEnch.getKey(), curMapEnch.getValue());
    }
   }
   
   if (itemEnchants.get(tempEnch)==null) {
    if (tempEnchLevel>0) {
     tempStack.enchant(tempEnch, tempEnchLevel);
     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + "'s " + ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + " has gained a new power: " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(tempEnchLevel).getString() + ChatFormatting.GOLD + ". Blame " + fromName));
    } else {
     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " was convinced by " + fromName + " that they didn't need " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(1).getString() + ChatFormatting.GOLD + " on their " +  ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + " anymore but then remembered they didn't have it anyways."));
    }
   } else {
    if (tempEnchLevel==0) {
     //remove enchantment
     EnchantmentHelper.setEnchantments(itemEnchantsNew,tempStack);
     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " was convinced by " + fromName + " that they didn't need " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(itemEnchants.get(tempEnch)).getString() + ChatFormatting.GOLD + " on their " +  ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + " anymore."));
    } else if (tempEnchLevel==itemEnchants.get(tempEnch)) {
     //same level so nothing is done
     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " wasn't convinced by " + fromName + " to get rid of " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(itemEnchants.get(tempEnch)).getString() + ChatFormatting.GOLD + " on their " +  ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + "."));
     
    } else {
     itemEnchantsNew.put(tempEnch, tempEnchLevel);
     EnchantmentHelper.setEnchantments(itemEnchantsNew,tempStack);
     if (tempEnchLevel>itemEnchants.get(tempEnch)) {
      ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + "'s " + ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + " has increased to " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(tempEnchLevel).getString() + ChatFormatting.GOLD + ". Jeez thanks " + fromName));
      } else {
       ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + "'s " + ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + " has decreased to " + ChatFormatting.DARK_GREEN + tempEnch.getFullname(tempEnchLevel).getString() + ChatFormatting.GOLD + ". Woot! Thanks " + fromName + "!")); 
      }
    }
   }
  }
  return 0;
 }
}