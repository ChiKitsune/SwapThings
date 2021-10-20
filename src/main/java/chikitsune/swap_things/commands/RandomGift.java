package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class RandomGift {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("randomgift").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return randomGiftLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return randomGiftLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return randomGiftLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int randomGiftLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   Configs.bakeConfig();
   ItemStack tempStack = ItemStack.EMPTY;
   String curMsg="quick use these to hide!";
   ItemArgument iaStack=new ItemArgument();
   Integer weightedChance=0, weightedMax=1 ,randTemp,curWeight,curLoop,stackAmount=64;
   
   weightedMax=Configs.randomGiftList.stream().map(str->Integer.parseInt(str.split(",")[2])).reduce(0, Integer::sum);

   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    randTemp=rand.nextInt(weightedMax)+1;
    curWeight=0;
    curLoop=0;
    
    for(String str:Configs.randomGiftList) {
     curWeight+=Integer.parseInt(str.split(",")[2]);
     if(curWeight>=randTemp) {
      try {
       stackAmount=Integer.parseInt(str.split(",")[1]);
      tempStack=iaStack.parse(new StringReader(str.split(",")[0])).createStack(stackAmount, false);
      } catch (CommandSyntaxException e) {
       e.printStackTrace();
       stackAmount=64;
       tempStack=new ItemStack(Items.DEAD_BUSH,stackAmount);       
      }
      break;
     }
     curLoop+=1;
    }
    curMsg=TextFormatting.GOLD + "Oh! " + fromName + " was nice and gave " + TextFormatting.RED + targetedPlayer.getName().getString() + " " + TextFormatting.GOLD + tempStack.getCount() + " " + TextFormatting.AQUA + tempStack.getDisplayName().getString();
    
    targetedPlayer.addItemStackToInventory(tempStack);
    
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(curMsg));
   }
   
   return 0;
  }

}
