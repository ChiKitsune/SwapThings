package chikitsune.swap_things.commands;

import java.util.ArrayList;
import java.util.List;

import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.stats.Stats;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class DisplayDeathBoard {
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("displaydeathboard").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return displayDeathBoardLogic(cmd_0arg.getSource());
  });
 }
  
  
  private static int displayDeathBoardLogic(CommandSource source) {
   List<ServerPlayerEntity> plyList=new ArrayList<ServerPlayerEntity>(source.getServer().getPlayerList().getPlayers());
   //List<ServerPlayerEntity> plyList=source.getWorld().getPlayers();
//   plyList.forEach((s)->System.out.println(s));
//   plyList.sort(Comparator.comparing(pl ->pl.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS))));
   plyList.sort((ServerPlayerEntity spe1, ServerPlayerEntity spe2) -> spe2.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS))-spe1.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS)));
   String placeStrPre = null,placeStrPost = null;
   source.getServer().getPlayerList().sendPacketToAllPlayers(new SChatPacket(new StringTextComponent(TextFormatting.GOLD + "** DEATH LEADERBOARD **"),ChatType.SYSTEM,plyList.stream().findFirst().get().getUniqueID()));
   for(int i = 0; i < plyList.size(); i++) {
    int c=i+1;
    if (plyList.get(i).getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS))==0) continue; 
    switch (c %10) {
     case 1: placeStrPre=c + "st: "; break;
     case 2: placeStrPre=c + "nd: "; break;
     case 3: placeStrPre=c + "rd: "; break;
     default: placeStrPre=c + "th: "; break;
    }
    
    switch (c) {
     case 1: placeStrPost=" has found what they are best at and will defend their spot to the death!"; break;
     case 2: placeStrPost=" has found a new rival they never knew they had. Lets cheer them on."; break;
     case 3: placeStrPost=" is grateful they made it this far but wish they had done better at this point."; break;
     case 4: placeStrPost=" is lost in the confusion about their feelings on their rank."; break;
     case 5: placeStrPost=" tried to rely on their ancient ancestors only to end up outdated advice."; break;
     default: placeStrPost=" some other people who showed a small bit of their spirit."; break;
    }
    source.getServer().getPlayerList().sendPacketToAllPlayers(new SChatPacket(new StringTextComponent(
      TextFormatting.GOLD + placeStrPre + 
      TextFormatting.DARK_RED + plyList.get(i).getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS)) +
      TextFormatting.GOLD + " deaths: " + 
      TextFormatting.RED + plyList.get(i).getName().getUnformattedComponentText() +
      TextFormatting.WHITE + placeStrPost
      ),ChatType.SYSTEM,plyList.stream().findFirst().get().getUniqueID()));
   }
   source.getServer().getPlayerList().sendPacketToAllPlayers(new SChatPacket(new StringTextComponent(TextFormatting.GOLD + "*********************************"),ChatType.SYSTEM,plyList.stream().findFirst().get().getUniqueID()));
   return 0;
  }
}