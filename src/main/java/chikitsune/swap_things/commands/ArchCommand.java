package chikitsune.swap_things.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.mojang.brigadier.CommandDispatcher;

import chikitsune.swap_things.SwappingThings;
import chikitsune.swap_things.commands.arguments.RandomArmorSlotArgument;
import chikitsune.swap_things.commands.arguments.RandomEffectTypeArgument;
import chikitsune.swap_things.commands.arguments.RandomSingleArmorSlotArgument;
import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ArchCommand {
 public static Random rand= new Random();
 public static List<String> realArmorList = Arrays.asList("MAINHAND", "OFFHAND","FEET","LEGS","CHEST","HEAD");
 
 public static final DeferredRegister<ArgumentTypeInfo<?, ?>> CMD_ARG_REG= DeferredRegister.create(Registry.COMMAND_ARGUMENT_TYPE_REGISTRY, SwappingThings.MODID);
 public static final RegistryObject<ArgumentTypeInfo<?,?>> R_A_S_ARG= CMD_ARG_REG.register("r_a_s_arg", () -> ArgumentTypeInfos.registerByClass(RandomArmorSlotArgument.class,SingletonArgumentInfo.contextFree(RandomArmorSlotArgument::allArmorSlots)));
 public static final RegistryObject<ArgumentTypeInfo<?,?>> R_E_T_ARG= CMD_ARG_REG.register("r_e_t_arg", () -> ArgumentTypeInfos.registerByClass(RandomEffectTypeArgument.class,SingletonArgumentInfo.contextFree(RandomEffectTypeArgument::randomEffectTypeArgument)));
 public static final RegistryObject<ArgumentTypeInfo<?,?>> R_S_A_S_ARG= CMD_ARG_REG.register("r_s_a_s_arg", () -> ArgumentTypeInfos.registerByClass(RandomSingleArmorSlotArgument.class,SingletonArgumentInfo.contextFree(RandomSingleArmorSlotArgument::allArmorSlots)));
 
 public static void register(final CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext cmdBuildContext) {
  dispatcher.register(
    Commands.literal("swapthings")
    .then(DisconnectPlayer.register())
    .then(DisplayDeathBoard.register())
    .then(HeldEnchanting.register())
    .then(InventoryBomb.register())
    .then(InventoryEqualizer.register(cmdBuildContext))
    .then(InventorySlotClearer.register())
    .then(InventorySlotEnchanting.register())
    .then(InventorySlotRenamer.register())
    .then(InventorySlotReplacer.register(cmdBuildContext))
    .then(InventorySlotUnnamer.register())
//    .then(Panicing.register())
    .then(PlayerNudger.register())
    .then(PlayerRotate.register())
    .then(QuickHide.register(cmdBuildContext))
    .then(RandomEnchanting.register())
    .then(RandomGift.register(cmdBuildContext))
    .then(RandomTeleport.register())
    .then(RandomTeleportDirection.register())
    .then(ReplaceArmorPiece.register(cmdBuildContext))
//    .then(ScalledSummon.register())
    .then(ShuffleHotbar.register())
    .then(ShuffleInventory.register())
    .then(ShuffleInventoryNames.register())
//    .then(SummonAmbush.register())
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
 
 public static Component getRainbowizedStr(String strMsg) {
  MutableComponent newStrMsg = Component.literal("");
  Integer colorStrLen=strMsg.length()/7;
  Integer iCnt=0;
  strMsg=ChatFormatting.stripFormatting(strMsg);
  
//  newStrMsg.append(new TextComponent(ChatFormatting.RED + strMsg.substring(iCnt, iCnt+colorStrLen)));
  newStrMsg.append(Component.literal(strMsg.substring(iCnt, iCnt+colorStrLen)).withStyle(ChatFormatting.RED));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(Component.literal(strMsg.substring(iCnt, iCnt+colorStrLen)).withStyle(ChatFormatting.GOLD));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(Component.literal(strMsg.substring(iCnt, iCnt+colorStrLen)).withStyle(ChatFormatting.YELLOW));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(Component.literal(strMsg.substring(iCnt, iCnt+colorStrLen)).withStyle(ChatFormatting.GREEN));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(Component.literal(strMsg.substring(iCnt, iCnt+colorStrLen)).withStyle(ChatFormatting.BLUE));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(Component.literal(strMsg.substring(iCnt, iCnt+colorStrLen)).withStyle(ChatFormatting.DARK_PURPLE));
  iCnt=iCnt+colorStrLen;
  newStrMsg.append(Component.literal(strMsg.substring(iCnt, iCnt+colorStrLen)).withStyle(ChatFormatting.LIGHT_PURPLE));
  
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
 
 public static void playerMsger(CommandSourceStack source,Collection<ServerPlayer> targetPlayers,Component msg) {
  if (!Configs.MSG_SHOW.get()) return;
  if (Configs.MSG_ALL_SERVER.get()) {
//   source.getServer().getPlayerList().broadcastAll(msg);
//   source.getServer().getPlayerList().broadcastAll(new ClientboundChatPacket(msg,ChatType.SYSTEM,targetPlayers.stream().findFirst().get().m_142081_()));
//   for(ServerPlayer allTargetedPlayer : source.getServer().getPlayerList().getPlayers()) {
//    allTargetedPlayer.sendSystemMessage(msg);
//    }
   sendAllPlayMsg(source, msg);
   
  } else {
   for(ServerPlayer targetedPlayer : targetPlayers) {
//    targetedPlayer.m_6352_(msg,targetedPlayer.m_142081_());
    targetedPlayer.sendSystemMessage(msg);
   }
  } 
  SwappingThings.LOGGER.info(msg);
 }
 
 public static void sendAllPlayMsg(CommandSourceStack source,Component msg) {
  for(ServerPlayer allTargetedPlayer : source.getServer().getPlayerList().getPlayers()) {
   allTargetedPlayer.sendSystemMessage(msg);
   }
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
 
 public static List<String> GetRE_EXT_LIST() {
 List<String> randomEnchantExcludeList= new ArrayList<>();
 Configs.RE_EX_LIST.get().forEach(str -> {
  if (str!=null) {
   randomEnchantExcludeList.add(str);
  }
 });
 return randomEnchantExcludeList;
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
 
 @Nullable
 public static ItemEntity ibDropItem(ItemStack droppedItem, ServerPlayer targetedPlayer) {
  if (droppedItem.isEmpty()) {
   return null;
  } else {
   double d0 = targetedPlayer.getY() - (double)0.3F + (double)targetedPlayer.getEyeHeight();
   ItemEntity itementity = new ItemEntity(targetedPlayer.level, targetedPlayer.getX(), d0, targetedPlayer.getZ(), droppedItem);
   itementity.setPickUpDelay(20);
   itementity.setThrower(targetedPlayer.getUUID());
   float f = rand.nextFloat() * 0.5F;
   float f1 = rand.nextFloat() * ((float)Math.PI * 2F);
   itementity.setDeltaMovement((double)(-Mth.sin(f1) * f), (double)0.2F, (double)(Mth.cos(f1) * f));
   
   if (itementity.captureDrops() != null) itementity.captureDrops().add(itementity);
   else
    itementity.level.addFreshEntity(itementity);
   return itementity;
  }
 }
}
