package chikitsune.swap_things.commands;

import java.util.Arrays;
import java.util.Collection;

import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;

public class SwapIdentity {
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("swapidentity").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return swapLocationLogic(cmd_0arg.getSource(),cmd_0arg.getSource().getPlayerOrException(),cmd_0arg.getSource().getPlayerOrException());
  }).then(Commands.argument("targetedPlayerOne", EntityArgument.players()).executes((cmd_1arg) -> {
   return swapLocationLogic(cmd_1arg.getSource(),EntityArgument.getPlayer(cmd_1arg, "targetedPlayerOne"),cmd_1arg.getSource().getPlayerOrException());
  }).then(Commands.argument("targetedPlayerTwo", EntityArgument.players()).executes((cmd_2arg) -> {
   return swapLocationLogic(cmd_2arg.getSource(),EntityArgument.getPlayer(cmd_2arg, "targetedPlayerOne"),EntityArgument.getPlayer(cmd_2arg, "targetedPlayerTwo"));
  })));
 }
  
  private static int swapLocationLogic(CommandSourceStack source, ServerPlayer targetedPlayerOne, ServerPlayer targetedPlayerTwo) {
   ArchCommand.ReloadConfig();
   if (targetedPlayerOne.getName().getContents() == targetedPlayerTwo.getName().getContents()) targetedPlayerTwo=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
   
//   Boolean isSameDim=(targetedPlayerOne.dimension == targetedPlayerTwo.dimension);
   Boolean isSameDim=(targetedPlayerOne.getCommandSenderWorld().dimensionType() == targetedPlayerTwo.getCommandSenderWorld().dimensionType());
   DimensionType playerOneDim=targetedPlayerOne.getCommandSenderWorld().dimensionType();
   DimensionType playerTwoDim=targetedPlayerTwo.getCommandSenderWorld().dimensionType();
   Vec3 playerOneVec=targetedPlayerOne.position();
   Float playerOneYaw=targetedPlayerOne.getYRot();
   Float playerOnePitch=targetedPlayerOne.getXRot();
   Vec3 playerTwoVec=targetedPlayerTwo.position();
   Float playerTwoYaw=targetedPlayerTwo.getYRot();
   Float playerTwoPitch=targetedPlayerTwo.getXRot();
   ServerLevel playerOnedimWorld=targetedPlayerOne.serverLevel();
   ServerLevel playerTwodimWorld=targetedPlayerTwo.serverLevel();
   
   Inventory playerOneInv=new Inventory(null);
   Inventory playerTwoInv=new Inventory(null);
   Integer playerOneCurSlot=targetedPlayerOne.getInventory().selected;
   Integer playerTwoCurSlot=targetedPlayerTwo.getInventory().selected;
   ItemStack playerOneHead=GetCustomHead(targetedPlayerOne.getName().getContents().toString(), targetedPlayerOne.getName().getContents().toString());
   ItemStack playerTwoHead=GetCustomHead(targetedPlayerTwo.getName().getContents().toString(), targetedPlayerTwo.getName().getContents().toString());
   Float playerOneExp,playerTwoExp, playerOneHealth, playerTwoHealth;
   Integer playerOneExpLvl, playerTwoExpLvl;
   
   playerOneInv.replaceWith(targetedPlayerOne.getInventory());
   playerTwoInv.replaceWith(targetedPlayerTwo.getInventory());
   
   targetedPlayerOne.getInventory().replaceWith(playerTwoInv);
   targetedPlayerTwo.getInventory().replaceWith(playerOneInv);
   
   playerOneExp=targetedPlayerOne.experienceProgress;
   playerOneExpLvl=targetedPlayerOne.experienceLevel;
   Integer playerOneExpTot=targetedPlayerOne.totalExperience;
   playerTwoExp=targetedPlayerTwo.experienceProgress;
   playerTwoExpLvl=targetedPlayerTwo.experienceLevel;
   Integer playerTwoExpTot=targetedPlayerTwo.totalExperience;
   
   targetedPlayerOne.giveExperienceLevels(-(playerOneExpLvl+1));
   targetedPlayerTwo.giveExperienceLevels(-(playerTwoExpLvl+1));
   targetedPlayerOne.giveExperienceLevels(playerTwoExpLvl);
   targetedPlayerOne.experienceProgress=playerTwoExp;
   targetedPlayerTwo.giveExperienceLevels(playerOneExpLvl);
   targetedPlayerTwo.experienceProgress=playerOneExp;
   
   playerOneHealth=targetedPlayerOne.getHealth();
   playerTwoHealth=targetedPlayerTwo.getHealth();
   
   targetedPlayerOne.setHealth(playerTwoHealth);
   targetedPlayerTwo.setHealth(playerOneHealth);

   targetedPlayerOne.teleportTo(playerTwodimWorld, Mth.floor(playerTwoVec.x()), Mth.floor(playerTwoVec.y()), Mth.floor(playerTwoVec.z()), playerTwoYaw, playerTwoPitch);
   targetedPlayerTwo.teleportTo(playerOnedimWorld, Mth.floor(playerOneVec.x()), Mth.floor(playerOneVec.y()), Mth.floor(playerOneVec.z()), playerOneYaw, playerOnePitch);
   
   if (targetedPlayerOne.getItemBySlot(EquipmentSlot.HEAD) != ItemStack.EMPTY) targetedPlayerOne.drop(targetedPlayerOne.getItemBySlot(EquipmentSlot.HEAD), false, true);
   if (targetedPlayerTwo.getItemBySlot(EquipmentSlot.HEAD) != ItemStack.EMPTY && targetedPlayerOne.getName() != targetedPlayerTwo.getName()) targetedPlayerTwo.drop(targetedPlayerTwo.getItemBySlot(EquipmentSlot.HEAD), false, true);
   
   targetedPlayerOne.setItemSlot(EquipmentSlot.HEAD, playerTwoHead);
   targetedPlayerTwo.setItemSlot(EquipmentSlot.HEAD, playerOneHead);
   
   Collection<ServerPlayer> targetPlayers=Arrays.asList(targetedPlayerOne);
   if (targetedPlayerOne.getName().getContents() == targetedPlayerTwo.getName().getContents()) {
    ArchCommand.playerMsger(source, targetPlayers,ArchCommand.getRainbowizedStr("Hurray " +targetedPlayerTwo.getName().getString()  + " you found yourself!"));
  } else {
   ArchCommand.playerMsger(source, targetPlayers,ArchCommand.getRainbowizedStr("That was quite a trip " +targetedPlayerTwo.getName().getString()  + " ... wait a second you are " + targetedPlayerOne.getName().getString() + ". You didn't go anywhere so better luck next time."));
   targetPlayers=Arrays.asList(targetedPlayerTwo);
   ArchCommand.playerMsger(source, targetPlayers,ArchCommand.getRainbowizedStr("That was quite a trip " +targetedPlayerOne.getName().getString()  + " ... wait a second you are " + targetedPlayerTwo.getName().getString() + ". You didn't go anywhere so better luck next time."));
   }
   return 0;
  }
  
  private static ItemStack GetCustomHead(String playerName, String headName) {
   ItemStack customHead = new ItemStack(net.minecraft.world.item.Items.PLAYER_HEAD);
   customHead.setTag(new CompoundTag());
   customHead.getTag().putString("SkullOwner", playerName);
   return customHead;         
  }
}
