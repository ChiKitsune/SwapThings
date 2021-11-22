package chikitsune.swap_things.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.mojang.brigadier.CommandDispatcher;

import chikitsune.swap_things.SwappingThings;
import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLPaths;

public class ArchCommand {
 public static Random rand= new Random();
 public static List<String> realArmorList = Arrays.asList("MAINHAND", "OFFHAND","FEET","LEGS","CHEST","HEAD");
 
 public static void register(final CommandDispatcher<CommandSourceStack> dispatcher) {
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
    .then(InventorySlotReplacer.register())
    .then(InventorySlotUnnamer.register())
//    .then(Panicing.register())
    .then(PlayerNudger.register())
    .then(PlayerRotate.register())
    .then(QuickHide.register())
    .then(RandomGift.register())
    .then(RandomTeleport.register())
    .then(RandomTeleportDirection.register())
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
 
 public static ServerPlayer getNewRandomSecondTarget(ServerPlayer curTargetOne, ServerPlayer curTargetTwo, MinecraftServer server) {
  ServerPlayer newSecondTarget=curTargetTwo;
  
  List<String> curPlayers=Arrays.asList(server.getPlayerNames());
  Integer ranNumPlayers=0;
  Integer iCnt=0;
  
  if (server.getPlayerNames().length > 1) {
   do {
    ranNumPlayers=rand.nextInt(curPlayers.size());
    newSecondTarget=server.getPlayerList().getPlayerByName(curPlayers.get(ranNumPlayers));
    iCnt++;
   } while (curTargetOne.getName().getContents()==newSecondTarget.getName().getContents() && iCnt<=(curPlayers.size()*20));
  }
  
  return newSecondTarget;
 }
 
 public static void swapArmorItems(ServerPlayer curTargetOne, ServerPlayer curTargetTwo, String  targetedArmorSlotOne) {
  swapArmorItems(curTargetOne, curTargetTwo, targetedArmorSlotOne,targetedArmorSlotOne);
 }
 
 public static void swapArmorItems(ServerPlayer curTargetOne, ServerPlayer curTargetTwo, String  targetedArmorSlotOne, String targetedArmorSlotTwo) {
  ItemStack tempStackOne, tempStackTwo;
  
  tempStackOne=curTargetOne.getItemBySlot(EquipmentSlot.byName(targetedArmorSlotOne.toLowerCase()));
  tempStackTwo=curTargetTwo.getItemBySlot(EquipmentSlot.byName(targetedArmorSlotTwo.toLowerCase()));
  
  curTargetOne.setItemSlot(EquipmentSlot.byName(targetedArmorSlotOne.toLowerCase()), tempStackTwo);
  curTargetTwo.setItemSlot(EquipmentSlot.byName(targetedArmorSlotTwo.toLowerCase()), tempStackOne);
 }
 
 public static TextComponent getRainbowizedStr(String strMsg) {
  TextComponent newStrMsg = new TextComponent("");
  Integer colorStrLen=strMsg.length()/7;
  Integer iCnt=0;
  strMsg=ChatFormatting.stripFormatting(strMsg);
  
  newStrMsg.append(new TextComponent(ChatFormatting.RED + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(new TextComponent(ChatFormatting.GOLD + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(new TextComponent(ChatFormatting.YELLOW + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(new TextComponent(ChatFormatting.GREEN + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(new TextComponent(ChatFormatting.BLUE + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(new TextComponent(ChatFormatting.DARK_PURPLE + strMsg.substring(iCnt, iCnt+colorStrLen)));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(new TextComponent(ChatFormatting.LIGHT_PURPLE + strMsg.substring(iCnt)));
  
  return newStrMsg;
 }
 
 public static String getRandomArmorSlotTarget(ServerPlayer targetedPlayer,String targetedArmorSlot,Boolean isRandomArmorSlot) {
  ItemStack tempItem=ItemStack.EMPTY;
  Integer iCnt=0;
  Integer tempRand=0;
  String newEquipmentSlotTarget="";
  
  if (targetedArmorSlot == "RANDOM") {
   targetedArmorSlot=realArmorList.get(rand.nextInt(realArmorList.size()));
   isRandomArmorSlot=true;
   }
  tempItem=targetedPlayer.getItemBySlot(EquipmentSlot.byName(targetedArmorSlot));
  
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
    tempItem=targetedPlayer.getItemBySlot(EquipmentSlot.byName(targetedArmorSlot));
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
 
 public static void playerMsger(CommandSourceStack source,Collection<ServerPlayer> targetPlayers,TextComponent msg) {
  if (!Configs.MSG_SHOW.get()) return;
  if (Configs.MSG_ALL_SERVER.get()) {
//   source.getServer().getPlayerList().broadcastMessage(msg);
   source.getServer().getPlayerList().broadcastAll(new ClientboundChatPacket(msg,ChatType.SYSTEM,targetPlayers.stream().findFirst().get().getUUID()));
   
  } else {
   for(ServerPlayer targetedPlayer : targetPlayers) {
    targetedPlayer.sendMessage(msg,targetedPlayer.getUUID());
   }
  } 
  SwappingThings.LOGGER.info(msg.getContents());
 }
 
 public static List<String> GetSM_EXT_LIST() {
 List<String> summonMountExcludeList= new ArrayList<>();
 Configs.SM_EX_LIST.get().forEach(str -> {
  if (str!=null) {
  summonMountExcludeList.add(str);
  }
 });
 return summonMountExcludeList;
 }
 
 public static List<MobCategory> GetSM_INC_LIST() {
  System.out.println("Configs.SM_IN_LIST.get(): "+Configs.SM_IN_LIST.get());
 List<MobCategory> summonMountIncludeList=new ArrayList<>();
 
 if (Configs.SM_IN_LIST.get().stream().map(String::toLowerCase).collect(Collectors.toList()).contains("any")) {
    for( MobCategory mc : MobCategory.values()) {
     summonMountIncludeList.add(mc);
    }
 } else {  
  Configs.SM_IN_LIST.get().forEach(str -> {
   if (str!=null) {
    summonMountIncludeList.add(MobCategory.byName(str.toLowerCase()));
   }
  });
 }
 return summonMountIncludeList;
 }
 
 
 public static List<MobCategory> GetSR_INC_LIST() {
//  System.out.println("Configs.SR_IN_LIST.get(): "+Configs.SR_IN_LIST.get());
  List<MobCategory> summonRiderIncludeList=new ArrayList<>();
  
  if (Configs.SR_IN_LIST.get().stream().map(String::toLowerCase).collect(Collectors.toList()).contains("any")) {
     for( MobCategory ec : MobCategory.values()) {
      summonRiderIncludeList.add(ec);
     }
   
  } else {  
   Configs.SR_IN_LIST.get().forEach(str -> {
//    System.out.println("str: "+str);
    if (str!=null) {
//     System.out.println("MC: "+MobCategory.byName(str.toLowerCase()));
     summonRiderIncludeList.add(MobCategory.byName(str.toLowerCase()));
    }
   });
  }
  return summonRiderIncludeList;
 }
 
 public static void ReloadConfig() {
  final CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(SwappingThings.MODID + "-common.toml"))
    .sync()
    .autosave()
    .writingMode(WritingMode.REPLACE)
    .autoreload()
    .preserveInsertionOrder()
    .build();
  configData.load();
  Configs.STCONFIG_SPEC.setConfig(configData);
 }
}
