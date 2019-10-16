package chikitsune.swap_things.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ArchCommand {
 public static Random rand= new Random();
 
 public static void register(final CommandDispatcher<CommandSource> dispatcher) {
  dispatcher.register(
    Commands.literal("swapthings")
     .then(QuickHide.register())
     .then(SwapLocation.register())
     .then(SwapArmor.register())
     .then(ShuffleHotbar.register())
     .then(ShuffleInventory.register())
    );
 }
 
 public static ServerPlayerEntity getNewRandomSecondTarget(ServerPlayerEntity curTargetOne, ServerPlayerEntity curTargetTwo, MinecraftServer server) {
  ServerPlayerEntity newSecondTarget=curTargetTwo;
  
  List<String> curPlayers=Arrays.asList(server.getOnlinePlayerNames());
  Integer ranNumPlayers=0;
  Integer iCnt=0;
  
  if (server.getOnlinePlayerNames().length > 1) {
   do {
    ranNumPlayers=rand.nextInt(curPlayers.size());
    newSecondTarget=server.getPlayerList().getPlayerByUsername(curPlayers.get(ranNumPlayers));
    iCnt++;
   } while (curTargetOne.getName().getUnformattedComponentText()==newSecondTarget.getName().getUnformattedComponentText() && iCnt<=(curPlayers.size()*20));
  }
  
  return newSecondTarget;
 }
 
 public static void swapArmorItems(ServerPlayerEntity curTargetOne, ServerPlayerEntity curTargetTwo, String  targetedArmorSlotOne) {
  swapArmorItems(curTargetOne, curTargetTwo, targetedArmorSlotOne,targetedArmorSlotOne);
 }
 
 public static void swapArmorItems(ServerPlayerEntity curTargetOne, ServerPlayerEntity curTargetTwo, String  targetedArmorSlotOne, String targetedArmorSlotTwo) {
  ItemStack tempStackOne, tempStackTwo;
  
  tempStackOne=curTargetOne.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlotOne.toLowerCase()));
  tempStackTwo=curTargetTwo.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlotTwo.toLowerCase()));
  
  curTargetOne.setItemStackToSlot(EquipmentSlotType.fromString(targetedArmorSlotOne.toLowerCase()), tempStackTwo);
  curTargetTwo.setItemStackToSlot(EquipmentSlotType.fromString(targetedArmorSlotTwo.toLowerCase()), tempStackOne);
 }
 
 public static StringTextComponent getRainbowizedStr(String strMsg) {
  StringTextComponent newStrMsg = new StringTextComponent("");
  Integer colorStrLen=strMsg.length()/7;
  Integer iCnt=0;
  strMsg=TextFormatting.getTextWithoutFormattingCodes(strMsg);
  
  newStrMsg.appendSibling(new StringTextComponent(TextFormatting.RED + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.appendSibling(new StringTextComponent(TextFormatting.GOLD + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.appendSibling(new StringTextComponent(TextFormatting.YELLOW + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.appendSibling(new StringTextComponent(TextFormatting.GREEN + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.appendSibling(new StringTextComponent(TextFormatting.BLUE + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.appendSibling(new StringTextComponent(TextFormatting.DARK_PURPLE + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.appendSibling(new StringTextComponent(TextFormatting.LIGHT_PURPLE + strMsg.substring(iCnt)));
  
  return newStrMsg;
 }
}
