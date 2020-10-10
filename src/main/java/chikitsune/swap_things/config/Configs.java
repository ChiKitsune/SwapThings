package chikitsune.swap_things.config;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;

import chikitsune.swap_things.SwappingThings;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;

//@Mod.EventBusSubscriber
@Mod.EventBusSubscriber(modid = SwappingThings.MODID, bus = Bus.MOD)
public class Configs {

 public static final SwapThingsConfig STCONFIG;
 public static final ForgeConfigSpec STCONFIG_SPEC;
 static {
  final Pair<SwapThingsConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SwapThingsConfig::new);
  STCONFIG_SPEC= specPair.getRight();
  STCONFIG= specPair.getLeft();
 }
 
 public static boolean cmdMsgAllServer;
 public static String inventoryBombItem;
 public static int cmdSTPermissionsLevel;
 public static String disconnectMsg;
 
 public static int playerNudgerNorthChance;
 public static Double playerNudgerNorthStrength;
 public static int playerNudgerNorthEastChance;
 public static Double playerNudgerNorthEastStrength;
 public static int playerNudgerEastChance;
 public static Double playerNudgerEastStrength;
 public static int playerNudgerSouthEastChance;
 public static Double playerNudgerSouthEastStrength;
 public static int playerNudgerSouthChance;
 public static Double playerNudgerSouthStrength;
 public static int playerNudgerSouthWestChance;
 public static Double playerNudgerSouthWestStrength;
 public static int playerNudgerWestChance;
 public static Double playerNudgerWestStrength;
 public static int playerNudgerNorthWestChance;
 public static Double playerNudgerNorthWestStrength;
 public static int playerNudgerUpChance;
 public static Double playerNudgerUpStrength;
 public static int playerNudgerDownChance;
 public static Double playerNudgerDownStrength;  
 public static int playerNudgerForwardMultiplier;
 public static int playerNudgerBackwardMultiplier;
 public static int playerNudgerLeftMultiplier;
 public static int playerNudgerRightMultiplier;
 public static int randomTeleportXMax;
 public static int randomTeleportYMax;
 public static int randomTeleportZMax;
 public static int randomTeleportXMin;
 public static int randomTeleportYMin;
 public static int randomTeleportZMin;
 public static List<String> quickHideList;
 public static List<String> summonMountExcludeList;
 public static boolean summonMountCustomName;
 public static List<String> displayDeathBoardPlacesTextList;
 
 public static void loadConfig(ForgeConfigSpec spec, Path path) {

  final CommentedFileConfig configData = CommentedFileConfig.builder(path)
          .sync()
          .autosave()
          .writingMode(WritingMode.REPLACE)
          .build();

  configData.load();
  spec.setConfig(configData);
}
 
 @SubscribeEvent
 public static void onLoad(final ModConfig.Loading configEvent) {

 }

 @SubscribeEvent
 public static void onReload(final ModConfig.Reloading configEvent) {
 }
 
 @SubscribeEvent
 public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
  if (configEvent.getConfig().getSpec() == STCONFIG_SPEC) bakeConfig();
 }
 
 public static void bakeConfig() {
  cmdMsgAllServer=STCONFIG.cmdMsgAllServer.get();
  inventoryBombItem=STCONFIG.inventoryBombItem.get();
  cmdSTPermissionsLevel=STCONFIG.cmdSTPermissionsLevel.get();
  disconnectMsg=STCONFIG.disconnectMsg.get();
  
  playerNudgerNorthChance=STCONFIG.playerNudgerNorthChance.get();
  playerNudgerNorthStrength=STCONFIG.playerNudgerNorthStrength.get();
  playerNudgerNorthEastChance=STCONFIG.playerNudgerNorthEastChance.get();
  playerNudgerNorthEastStrength=STCONFIG.playerNudgerNorthEastStrength.get();
  playerNudgerEastChance=STCONFIG.playerNudgerEastChance.get();
  playerNudgerEastStrength=STCONFIG.playerNudgerEastStrength.get();
  playerNudgerSouthEastChance=STCONFIG.playerNudgerSouthEastChance.get();
  playerNudgerSouthEastStrength=STCONFIG.playerNudgerSouthEastStrength.get();
  playerNudgerSouthChance=STCONFIG.playerNudgerSouthChance.get();
  playerNudgerSouthStrength=STCONFIG.playerNudgerSouthStrength.get();
  playerNudgerSouthWestChance=STCONFIG.playerNudgerSouthWestChance.get();
  playerNudgerSouthWestStrength=STCONFIG.playerNudgerSouthWestStrength.get();
  playerNudgerWestChance=STCONFIG.playerNudgerWestChance.get();
  playerNudgerWestStrength=STCONFIG.playerNudgerWestStrength.get();
  playerNudgerNorthWestChance=STCONFIG.playerNudgerNorthWestChance.get();
  playerNudgerNorthWestStrength=STCONFIG.playerNudgerNorthWestStrength.get();
  playerNudgerUpChance=STCONFIG.playerNudgerUpChance.get();
  playerNudgerUpStrength=STCONFIG.playerNudgerUpStrength.get();
  playerNudgerDownChance=STCONFIG.playerNudgerDownChance.get();
  playerNudgerDownStrength=STCONFIG.playerNudgerDownStrength.get();
  playerNudgerForwardMultiplier=STCONFIG.playerNudgerForwardMultiplier.get();
  playerNudgerBackwardMultiplier=STCONFIG.playerNudgerBackwardMultiplier.get();
  playerNudgerLeftMultiplier=STCONFIG.playerNudgerLeftMultiplier.get();
  playerNudgerRightMultiplier=STCONFIG.playerNudgerRightMultiplier.get();
  
  randomTeleportXMax=STCONFIG.randomTeleportXMax.get();
  randomTeleportYMax=STCONFIG.randomTeleportYMax.get();
  randomTeleportZMax=STCONFIG.randomTeleportZMax.get();
  randomTeleportXMin=STCONFIG.randomTeleportXMin.get();
  randomTeleportYMin=STCONFIG.randomTeleportYMin.get();
  randomTeleportZMin=STCONFIG.randomTeleportZMin.get();
  
  quickHideList= new ArrayList<>();
  STCONFIG.quickHideList.get().forEach(str -> {
   if (str!=null) {
   quickHideList.add(str);
   }
  });
  
  summonMountExcludeList= new ArrayList<>();
  STCONFIG.summonMountExcludeList.get().forEach(str -> {
   if (str!=null) {
   summonMountExcludeList.add(str);
   }
  });
  summonMountCustomName=STCONFIG.summonMountCustomName.get();
  
  displayDeathBoardPlacesTextList= new ArrayList<>();
  STCONFIG.displayDeathBoardPlacesTextList.get().forEach(str -> {
   if (str!=null) {
    displayDeathBoardPlacesTextList.add(str);
    }
   });
 }
 
 public static class SwapThingsConfig {
  public final BooleanValue cmdMsgAllServer;
  public final ConfigValue<String> inventoryBombItem;
  public final IntValue cmdSTPermissionsLevel;
  public final ConfigValue<String> disconnectMsg;
  
  public final IntValue playerNudgerNorthChance;
  public final DoubleValue playerNudgerNorthStrength;
  public final IntValue playerNudgerNorthEastChance;
  public final DoubleValue playerNudgerNorthEastStrength;
  public final IntValue playerNudgerEastChance;
  public final DoubleValue playerNudgerEastStrength;
  public final IntValue playerNudgerSouthEastChance;
  public final DoubleValue playerNudgerSouthEastStrength;
  public final IntValue playerNudgerSouthChance;
  public final DoubleValue playerNudgerSouthStrength;
  public final IntValue playerNudgerSouthWestChance;
  public final DoubleValue playerNudgerSouthWestStrength;
  public final IntValue playerNudgerWestChance;
  public final DoubleValue playerNudgerWestStrength;
  public final IntValue playerNudgerNorthWestChance;
  public final DoubleValue playerNudgerNorthWestStrength;
  public final IntValue playerNudgerUpChance;
  public final DoubleValue playerNudgerUpStrength;
  public final IntValue playerNudgerDownChance;
  public final DoubleValue playerNudgerDownStrength;
  public final IntValue playerNudgerForwardMultiplier;
  public final IntValue playerNudgerBackwardMultiplier;
  public final IntValue playerNudgerLeftMultiplier;
  public final IntValue playerNudgerRightMultiplier;
  public final IntValue randomTeleportXMax;
  public final IntValue randomTeleportYMax;
  public final IntValue randomTeleportZMax;
  public final IntValue randomTeleportXMin;
  public final IntValue randomTeleportYMin;
  public final IntValue randomTeleportZMin;
  
  public final ConfigValue<List<? extends String>> quickHideList;
  
  public final ConfigValue<List<? extends String>> summonMountExcludeList;
  public final BooleanValue summonMountCustomName;
  
  public final ConfigValue<List<? extends String>> displayDeathBoardPlacesTextList;
  
  
  
  public SwapThingsConfig(ForgeConfigSpec.Builder builder) {
   builder.comment("Command Settings").push("commands");
   builder.push("general");
   cmdMsgAllServer=builder.comment("Command messages show to all players on server.").define("msgAllPlayers", false);
   cmdSTPermissionsLevel=builder.comment("Sets all command to be this permission level.").defineInRange("cmdPermissionsLevel", 2, 0, 4);
   builder.pop();
   
//   InventoryBomb config start
   builder.comment("InventoryBomb Command Settings").push("InventoryBomb");
   inventoryBombItem=builder.comment("Item that will replace all inventory slots with after items are dropped").define("inventoryBombItem", "minecraft:dead_bush");
   builder.pop();
//   InventoryBomb config end
   
// DisconnectPlayer config start
 builder.comment("DisconnectPlayer Command Settings").push("DisconnectPlayer");
 disconnectMsg=builder.comment("Message to show when disconnected").define("disconnectMsg", " and had a Dark PANIC moment and some how left the server.");
 builder.pop();
// DisconnectPlayer config end
   
//   PlayerNudger config end
   builder.comment("PlayerNudger Command Settings").push("PlayerNudger");
   builder.push("North");
   playerNudgerNorthChance=builder.comment("Chance for playernudger command to nudge North").defineInRange("North_Chance",4,0,100);
   playerNudgerNorthStrength=builder.comment("Strength of playernudger command nudging North").defineInRange("North_Strength",.7,0,10);
   builder.pop().push("NorthEast");
   playerNudgerNorthEastChance=builder.comment("Chance for playernudger command to nudge Northeast").defineInRange("Northeast_Chance",2,0,100);
   playerNudgerNorthEastStrength=builder.comment("Strength for playernudger command nudging Northeast").defineInRange("Northeast_Strength",.7,0,10);
   builder.pop().push("East");
   playerNudgerEastChance=builder.comment("Chance for playernudger command to nudge East").defineInRange("East_Chance",4,0,100);
   playerNudgerEastStrength=builder.comment("Strength for playernudger command to nudge East").defineInRange("East_Strength",.7,0,10);
   builder.pop().push("SouthEast");
   playerNudgerSouthEastChance=builder.comment("Chance for playernudger command to nudge Southeast").defineInRange("Southeast_Chance",2,0,100);
   playerNudgerSouthEastStrength=builder.comment("Strength for playernudger command to nudge Southeast").defineInRange("Southeast_Strength",.7,0,10);
   builder.pop().push("South");
   playerNudgerSouthChance=builder.comment("Chance for playernudger command to nudge South").defineInRange("South_Chance",4,0,100);
   playerNudgerSouthStrength=builder.comment("Strength for playernudger command to nudge South").defineInRange("South_Strength",.7,0,10);
   builder.pop().push("SouthWest");
   playerNudgerSouthWestChance=builder.comment("Chance for playernudger command to nudge Southwest").defineInRange("Southwest_Chance",2,0,100);
   playerNudgerSouthWestStrength=builder.comment("Strength for playernudger command to nudge Southwest").defineInRange("Southwest_Strength",.7,0,10);
   builder.pop().push("West");
   playerNudgerWestChance=builder.comment("Chance for playernudger command to nudge West").defineInRange("West_Chance",4,0,100);
   playerNudgerWestStrength=builder.comment("Strength for playernudger command to nudge West").defineInRange("West_Strength",.7,0,10);
   builder.pop().push("NorthWest");
   playerNudgerNorthWestChance=builder.comment("Chance for playernudger command to nudge Northwest").defineInRange("Northwest_Chance",2,0,100);
   playerNudgerNorthWestStrength=builder.comment("Strength for playernudger command to nudge Northwest").defineInRange("Northwest_Strength",.7,0,10);
   builder.pop().push("Up");
   playerNudgerUpChance=builder.comment("Chance for playernudger command to nudge Up").defineInRange("Up_Chance",1,0,100);
   playerNudgerUpStrength=builder.comment("Strength for playernudger command to nudge Up").defineInRange("Up_Strength",.7,0,10);
   builder.pop().push("Down");
   playerNudgerDownChance=builder.comment("Chance for playernudger command to nudge Down").defineInRange("Down_Chance",0,0,100);
   playerNudgerDownStrength=builder.comment("Strength for playernudger command to nudge Down").defineInRange("Down_Strength",.7,0,10);
   builder.pop().push("PlayerFacingDirections");
   playerNudgerForwardMultiplier=builder.comment("Multiplier to increase the chance to nudge where the player is looking").defineInRange("Forward_Multiplier",1,0,100);
   playerNudgerBackwardMultiplier=builder.comment("Multiplier to increase the chance to nudge opposite where the player is looking").defineInRange("Backward_Multiplier",1,0,100);
   playerNudgerLeftMultiplier=builder.comment("Multiplier to increase the chance to nudge the player to the right").defineInRange("Left_Multiplier",1,0,100);
   playerNudgerRightMultiplier=builder.comment("Multiplier to increase the chance to nudge the player to the left").defineInRange("Right_Multiplier",1,0,100);
   builder.pop(2);
//   PlayerNudger config end
   
//   RandomTeleport config start
   builder.comment("RandomTeleport Command Settings").push("RandomTeleport");
   builder.push("X Axis");
   randomTeleportXMax=builder.comment("Max Block +/- range on the X Axis that could teleport along").defineInRange("randomTeleportXMax", 500, 1, 100000);
   randomTeleportXMin=builder.comment("Min Block +/- range on the X Axis that could teleport along").defineInRange("randomTeleportXMin", 100, 1, 100000);
   builder.pop().push("Y Axis");
   randomTeleportYMax=builder.comment("Max Block +/- range on the Y Axis that could teleport along").defineInRange("randomTeleportYMax", 10, 0, 255);
   randomTeleportYMin=builder.comment("Min Block +/- range on the Y Axis that could teleport along").defineInRange("randomTeleportYMin", 0, 0, 255);
   builder.pop().push("Z Axis");
   randomTeleportZMax=builder.comment("Max Block +/- range on the Z Axis that could teleport along").defineInRange("randomTeleportZMax", 500, 1, 100000);
   randomTeleportZMin=builder.comment("Min Block +/- range on the Z Axis that could teleport along").defineInRange("randomTeleportZMin", 100, 1, 100000);
   builder.pop(2);
//   RandomTeleport config end
   
//   QuickHide config start
   builder.comment("QuickHide Command settings").push("QuickHide");
   
   List<String> quiHidList=Lists.newArrayList();
   quiHidList.add("minecraft:dead_bush,quick hide in these bushes!");
   quiHidList.add("minecraft:wheat,quick hide in the wheat field!");
   quiHidList.add("minecraft:feather,quick act like a chicken!");
   quiHidList.add("minecraft:painting,quick blend into the wall!");
   
   quickHideList=builder.comment("List of random options that can be chosen when using quickhide command").defineList("Quick Hide Options", quiHidList, s -> s instanceof String);
   builder.pop();
//   QuickHide config end
   
//   SummonMount config start
   builder.comment("SummonMount Command settings").push("SummonMount");
   
   List<String> sumMountList=Lists.newArrayList();
   sumMountList.add("minecraft:wither");
   sumMountList.add("minecraft:ender_dragon");
   
   summonMountExcludeList=builder.comment("List of entities for SummonMount command to EXCLUDE from possible entities").defineList("SummonMount", sumMountList, s -> s instanceof String);
   summonMountCustomName=builder.comment("Custom name for SummonMount entities.").define("summonMountCustomName", true);
   
   builder.pop();
//   SummonMount config end
   
   
// DisplayDeathBoard config start
 builder.comment("DisplayDeathBoard Command settings").push("DisplayDeathBoard");
 
 List<String> deathBoardList=Lists.newArrayList();
 deathBoardList.add(" rip");
// deathBoardList.add(" has found what they are best at and will defend their spot to the death!");
// deathBoardList.add(" has found a new rival they never knew they had. Lets cheer them on.");
// deathBoardList.add("is grateful they made it this far but wish they had done better at this point.");
// deathBoardList.add(" is lost in the confusion about their feelings on their rank.");
// deathBoardList.add(" tried to rely on their ancient ancestors only to end up outdated advice.");
// deathBoardList.add(" some other people who showed a small bit of their spirit.");
 
 displayDeathBoardPlacesTextList=builder.comment("List of strings to put at the end based on position. Ranks without a separate value will use whatever the last value is").defineList("SummonMount", deathBoardList, s -> s instanceof String);
 
 builder.pop();
// DisplayDeathBoard config end
   
   builder.pop();
  }  
 }
 
 
}