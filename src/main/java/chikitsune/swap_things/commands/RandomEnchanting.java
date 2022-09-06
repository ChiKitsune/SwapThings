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

import chikitsune.swap_things.commands.arguments.RandomEffectTypeArgument;
import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ItemEnchantmentArgument;
import net.minecraft.network.chat.Component;
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
     }).then(Commands.argument("randomEffectType", RandomEffectTypeArgument.randomEffectTypeArgument()).executes((cmd_3arg) -> {
      return randomEnchantingLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"),RandomEffectTypeArgument.getRandomEffectType(cmd_3arg,"randomEffectType"),null,null); 
      }).then(Commands.argument("enchantment",ItemEnchantmentArgument.enchantment()).executes((cmd_4arg) -> {
       return randomEnchantingLogic(cmd_4arg.getSource(),EntityArgument.getPlayers(cmd_4arg, "targetedPlayer"),StringArgumentType.getString(cmd_4arg, "fromName"),RandomEffectTypeArgument.getRandomEffectType(cmd_4arg,"randomEffectType"),ItemEnchantmentArgument.getEnchantment(cmd_4arg, "enchantment"),null);
      }).then(Commands.argument("enchantment_level",IntegerArgumentType.integer()).executes((cmd_5arg) -> {
       return randomEnchantingLogic(cmd_5arg.getSource(),EntityArgument.getPlayers(cmd_5arg, "targetedPlayer"),StringArgumentType.getString(cmd_5arg, "fromName"),RandomEffectTypeArgument.getRandomEffectType(cmd_5arg,"randomEffectType"),ItemEnchantmentArgument.getEnchantment(cmd_5arg, "enchantment"),IntegerArgumentType.getInteger(cmd_5arg, "enchantment_level"));
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
    .filter((Enchantment eT) -> !ArchCommand.GetRE_EXT_LIST().contains(ForgeRegistries.ENCHANTMENTS.getKey(eT).toString()))
//    .filter((Enchantment eT) -> ArchCommand.Enchantment().contains(eT.getCategory()))
    .collect(Collectors.toList());
  List<Enchantment> lstPossEnchLoop;
  
  for(ServerPlayer targetedPlayer : targetPlayers) {
   rand= new Random();
   itemEnchants=null;
   itemEnchantsNew=Maps.<Enchantment, Integer>newLinkedHashMap();
   lstPossEnchLoop=lstPossEnch;
      
   iCnt=0;
   do {
    tempRandNum=rand.nextInt(targetedPlayer.getInventory().getContainerSize());
    iCnt++;
   } while (targetedPlayer.getInventory().getItem(tempRandNum).isEmpty() || iCnt<50);
   
   tempStack=targetedPlayer.getInventory().getItem(tempRandNum);
   
   if (tempStack.isEmpty()) continue;
   itemEnchants=EnchantmentHelper.getEnchantments(tempStack);
   
   boolean allowedEnchant=false;
   Integer chkCurLevel=0;
   if (enchInput!=null) {
    tempEnch=enchInput;
   } else {
    iCnt=0;
    boolean getAnotherEnchant=false;
    
    do {
     int rndIntEnch=rand.nextInt(lstPossEnch.size());
     Collections.shuffle(lstPossEnch);
     tempEnch=(Enchantment)lstPossEnch.get(rndIntEnch);

     chkCurLevel=itemEnchants.get(tempEnch)!=null ? itemEnchants.get(tempEnch) : 0;
     boolean isCurMaxLevel=(chkCurLevel==tempEnch.getMaxLevel()) ? true : false;
          
     if (!Configs.RE_FORCE_ENCHANT.get() && !tempEnch.canEnchant(tempStack)) {
      allowedEnchant=false;
     } else {
      allowedEnchant=true;
     }
     if (chkCurLevel>0) allowedEnchant=true;

     switch (randomEffectType.toUpperCase()) {
      case "POSITIVE": getAnotherEnchant=isCurMaxLevel || tempEnch.isCurse() ? true : false;
       break;
      case "NEGATIVE": getAnotherEnchant=chkCurLevel==0 ? true : false;      
       break;
      default:
       break;
     }
     

     
//     System.out.println("iCnt: "+iCnt);
//     System.out.println("RE_FORCE_ENCHANT: "+Configs.RE_FORCE_ENCHANT.get());
//     System.out.println("tempEnch.canEnchant(): "+tempEnch.canEnchant(tempStack));
//     System.out.println("allowedEnchant: "+allowedEnchant);
//     
//     System.out.println("isCurMaxLevel: "+isCurMaxLevel);
//     System.out.println("tempEnch.isCurse(): "+tempEnch.isCurse());
//     System.out.println("getAnotherEnchant: "+getAnotherEnchant);
//     
//     System.out.println("while loop check: "+(!allowedEnchant && getAnotherEnchant && iCnt<200));
//     System.out.println("while loop check 2/3: "+(!allowedEnchant && getAnotherEnchant));
//     System.out.println("while loop check 2/3v2: "+(getAnotherEnchant && iCnt<200));     
     
     
    iCnt++;
    } while ( !(allowedEnchant && !getAnotherEnchant) && iCnt<(lstPossEnch.size()*20));
   }
//   System.out.println("Chosen tempEnch: "+tempEnch.getRegistryName());
   

   
   if (enchLevel==null) {
//    System.out.println("tempEnch: "+tempEnch.getRegistryName()+" max level: "+tempEnch.getMaxLevel());
    Integer curLevel=itemEnchants.get(tempEnch)!=null ? itemEnchants.get(tempEnch) : 0;
    
    switch (randomEffectType.toUpperCase()) {
     case "ANY": tempRandEnchMin=0;tempRandEnchMax=tempEnch.getMaxLevel();
      break;
     case "POSITIVE": 
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
   
   if (!allowedEnchant || chkCurLevel == tempEnchLevel) {
    ArchCommand.playerMsger(source, targetPlayers, 
      Component.literal(targetedPlayer.getName().getString() + "'s ").withStyle(ChatFormatting.RED)
      .append(Component.literal(tempStack.getHoverName().getString()).withStyle(ChatFormatting.AQUA))
      .append(Component.literal(" shimmered but seems nothing changed despite " + fromName + "'s effort").withStyle(ChatFormatting.GOLD)));
//    ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + "'s " + ChatFormatting.AQUA + tempStack.getHoverName().getString() +  ChatFormatting.GOLD + " shimmered but seems nothing changed despite " + fromName + "'s effort"));
    return 0;
   }
   
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
