package chikitsune.swap_things.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.mojang.brigadier.CommandDispatcher;

import chikitsune.swap_things.SwappingThings;
import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ArchCommand {
 public static Random rand= new Random();
 public static List<String> realArmorList = Arrays.asList("MAINHAND", "OFFHAND","FEET","LEGS","CHEST","HEAD");
 
 public static void register(final CommandDispatcher<CommandSource> dispatcher) {
  dispatcher.register(
    Commands.literal("swapthings")
    .then(DisconnectPlayer.register())
    .then(DisplayDeathBoard.register())
    .then(HeldEnchanting.register())
    .then(InventoryBomb.register())
    .then(InventoryEqualizer.register())
    .then(InventorySlotClearer.register())
    .then(InventorySlotEnchanting.register())
    .then(InventorySlotRenamer.register())
    .then(InventorySlotUnnamer.register())
//    .then(Panicing.register())
    .then(PlayerNudger.register())
    .then(PlayerRotate.register())
    .then(QuickHide.register())
    .then(RandomGift.register())
    .then(RandomTeleport.register())
    .then(ReplaceArmorPiece.register())
    .then(ShuffleHotbar.register())
    .then(ShuffleInventory.register())
    .then(ShuffleInventoryNames.register())
    .then(SummonMount.register())
    .then(SummonRider.register())
    .then(SwapArmor.register())
    .then(SwapHands.register())
    .then(SwapIdentity.register())
    .then(SwapInventoryNames.register())
    .then(SwapLocation.register())
    .then(ToggleCrouch.register())
    .then(ToggleRun.register())
    .then(UnMounter.register())
    .then(UnShuffleInventoryNames.register())
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
 
 public static String getRandomArmorSlotTarget(ServerPlayerEntity targetedPlayer,String targetedArmorSlot,Boolean isRandomArmorSlot) {
  ItemStack tempItem=ItemStack.EMPTY;
  Integer iCnt=0;
  Integer tempRand=0;
  String newEquipmentSlotTarget="";
  
  if (targetedArmorSlot == "RANDOM") {
   targetedArmorSlot=realArmorList.get(rand.nextInt(realArmorList.size()));
   isRandomArmorSlot=true;
   }
  tempItem=targetedPlayer.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlot));
  
  if (tempItem == ItemStack.EMPTY && isRandomArmorSlot) {
   do {
    tempRand=rand.nextInt(6);
    switch (tempRand) {
     case 0: targetedArmorSlot="HEAD"; break;
     case 1: targetedArmorSlot="CHEST"; break;
     case 2: targetedArmorSlot="LEGS"; break;
     case 3: targetedArmorSlot="FEET"; break;
     case 4: targetedArmorSlot="OFFHAND"; break;
     case 5: targetedArmorSlot="MAINHAND"; break;
     default: break;
    }
    tempItem=targetedPlayer.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlot));
    iCnt++;
   } while (tempItem == ItemStack.EMPTY && iCnt<=25);
  }
  return newEquipmentSlotTarget;
 }
 
 public static String getArmorSlotDescription(String targetedArmorSlot) {
  String armorSlotDesc="";
  
  switch (targetedArmorSlot.toUpperCase()) {
   case "HEAD": armorSlotDesc="HELM"; break;
   case "CHEST": armorSlotDesc="CHESTPLATE"; break;
   case "LEGS": armorSlotDesc="LEGGINGS"; break;
   case "FEET": armorSlotDesc="BOOTS"; break;
   case "OFFHAND": armorSlotDesc="OFFHAND"; break;
   case "MAINHAND": armorSlotDesc="HELD ITEM"; break;
   case "ALL": armorSlotDesc="EQUIPMENT"; break;
   case "SET": armorSlotDesc="ARMOR"; break;
   default: break;
  }
  return armorSlotDesc;
 }
 
 public static void playerMsger(CommandSource source,Collection<ServerPlayerEntity> targetPlayers,StringTextComponent msg) {
  if (Configs.cmdMsgAllServer) {
//   source.getServer().getPlayerList().func_232641_a_(msg);
   source.getServer().getPlayerList().sendPacketToAllPlayers(new SChatPacket(msg,ChatType.SYSTEM,targetPlayers.stream().findFirst().get().getUniqueID()));
   
  } else {
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    targetedPlayer.sendMessage(msg,targetedPlayer.getUniqueID());
   }
  } 
  SwappingThings.LOGGER.info(msg.getUnformattedComponentText());
 }
}
