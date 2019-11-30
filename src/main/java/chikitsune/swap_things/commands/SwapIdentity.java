package chikitsune.swap_things.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;

public class SwapIdentity {
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("swapdentity").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return swapLocationLogic(cmd_0arg.getSource(),cmd_0arg.getSource().asPlayer(),cmd_0arg.getSource().asPlayer());
  }).then(Commands.argument("targetedPlayerOne", EntityArgument.players()).executes((cmd_1arg) -> {
   return swapLocationLogic(cmd_1arg.getSource(),EntityArgument.getPlayer(cmd_1arg, "targetedPlayerOne"),cmd_1arg.getSource().asPlayer());
  }).then(Commands.argument("targetedPlayerTwo", EntityArgument.players()).executes((cmd_2arg) -> {
   return swapLocationLogic(cmd_2arg.getSource(),EntityArgument.getPlayer(cmd_2arg, "targetedPlayerOne"),EntityArgument.getPlayer(cmd_2arg, "targetedPlayerTwo"));
  })));
 }
  
  private static int swapLocationLogic(CommandSource source, ServerPlayerEntity targetedPlayerOne, ServerPlayerEntity targetedPlayerTwo) {
   if (targetedPlayerOne.getName().getUnformattedComponentText() == targetedPlayerTwo.getName().getUnformattedComponentText()) targetedPlayerTwo=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
   
   Boolean isSameDim=(targetedPlayerOne.dimension == targetedPlayerTwo.dimension);
   DimensionType playerOneDim=targetedPlayerOne.dimension;
   DimensionType playerTwoDim=targetedPlayerTwo.dimension;
   Vec3d playerOneVec=targetedPlayerOne.getPositionVec();
   Float playerOneYaw=targetedPlayerOne.rotationYaw;
   Float playerOnePitch=targetedPlayerOne.rotationPitch;
   Vec3d playerTwoVec=targetedPlayerTwo.getPositionVec();
   Float playerTwoYaw=targetedPlayerTwo.rotationYaw;
   Float playerTwoPitch=targetedPlayerTwo.rotationPitch;
   ServerWorld playerOnedimWorld=targetedPlayerOne.getServerWorld();
   ServerWorld playerTwodimWorld=targetedPlayerTwo.getServerWorld();
   
   PlayerInventory playerOneInv=new PlayerInventory(null);
   PlayerInventory playerTwoInv=new PlayerInventory(null);
   Integer playerOneCurSlot=targetedPlayerOne.inventory.currentItem;
   Integer playerTwoCurSlot=targetedPlayerTwo.inventory.currentItem;
   ItemStack playerOneHead=GetCustomHead(targetedPlayerOne.getName().getUnformattedComponentText(), targetedPlayerOne.getName().getUnformattedComponentText());
   ItemStack playerTwoHead=GetCustomHead(targetedPlayerTwo.getName().getUnformattedComponentText(), targetedPlayerTwo.getName().getUnformattedComponentText());
   Float playerOneExp,playerTwoExp;
   Integer playerOneExpLvl, playerTwoExpLvl;
   
   playerOneInv.copyInventory(targetedPlayerOne.inventory);
   playerTwoInv.copyInventory(targetedPlayerTwo.inventory);
   
   playerOneExp=targetedPlayerOne.experience;
   playerOneExpLvl=targetedPlayerOne.experienceLevel;
   Integer playerOneExpTot=targetedPlayerOne.experienceTotal;
   playerTwoExp=targetedPlayerTwo.experience;
   playerTwoExpLvl=targetedPlayerTwo.experienceLevel;
   Integer playerTwoExpTot=targetedPlayerTwo.experienceTotal;
   
   targetedPlayerOne.addExperienceLevel(-(playerOneExpLvl+1));
   targetedPlayerTwo.addExperienceLevel(-(playerTwoExpLvl+1));
   
   targetedPlayerOne.addExperienceLevel(playerTwoExpLvl);
   targetedPlayerOne.experience=playerTwoExp;
   targetedPlayerTwo.addExperienceLevel(playerOneExpLvl);
   targetedPlayerTwo.experience=playerOneExp;

   targetedPlayerOne.teleport(playerTwodimWorld, MathHelper.floor(playerTwoVec.getX()), MathHelper.floor(playerTwoVec.getY()), MathHelper.floor(playerTwoVec.getZ()), playerTwoYaw, playerTwoPitch);

   targetedPlayerTwo.teleport(playerOnedimWorld, MathHelper.floor(playerOneVec.getX()), MathHelper.floor(playerOneVec.getY()), MathHelper.floor(playerOneVec.getZ()), playerOneYaw, playerOnePitch);
   
   if (targetedPlayerOne.getName() == targetedPlayerTwo.getName()) {
    source.getServer().getPlayerList().sendMessage(ArchCommand.getRainbowizedStr("Well I guess if you really want to swap locations with yourself you can go right ahead " + targetedPlayerOne.getName().getFormattedText() + "."));
  } else {
   source.getServer().getPlayerList().sendMessage(ArchCommand.getRainbowizedStr("Wow what a trip. A fresh perspective is nice once a while wouldn't you agree " + targetedPlayerOne.getName().getFormattedText() + " and " + targetedPlayerTwo.getName().getFormattedText() + "?"));
   }
   
   return 0;
  }
  
  private static ItemStack GetCustomHead(String playerName, String headName) {
   ItemStack customHead = new ItemStack(net.minecraft.item.Items.PLAYER_HEAD);
   customHead.setTag(new CompoundNBT());
   customHead.getTag().putString("SkullOwner", playerName);

   return customHead;         
  }
}
