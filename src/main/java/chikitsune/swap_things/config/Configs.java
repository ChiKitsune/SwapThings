package chikitsune.swap_things.config;

import java.nio.file.Path;
import java.util.List;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;

import chikitsune.swap_things.SwappingThings;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

//@Mod.EventBusSubscriber
@Mod.EventBusSubscriber(modid = SwappingThings.MODID, bus = Bus.MOD)
public class Configs {

// public static final SwapThingsConfig STCONFIG;
 private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
 public static final ForgeConfigSpec STCONFIG_SPEC;
 
// static {
//  final Pair<SwapThingsConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SwapThingsConfig::new);
//  STCONFIG_SPEC= specPair.getRight();
//  STCONFIG= specPair.getLeft();
// }
 private static List<String> quiHidList_def;
 private static List<String> sumMountINCLUDEClassList_def;
 private static List<String> sumMountEXLUDEList_def;
 private static List<String> sumRiderClassList_def;
 private static List<String> deathBoardList_def;
 private static List<String> randGiftList_def;
 
 
 public static void init(Path file) {  
  final CommentedFileConfig configData = CommentedFileConfig.builder(file)
    .sync()
    .autosave()
    .writingMode(WritingMode.REPLACE)
    .autoreload()
    .preserveInsertionOrder()
    .build();
  configData.load();
  STCONFIG_SPEC.setConfig(configData);
 }
 

 public static final String CAT_CMDS = "commands";
 public static final String CAT_CMDS_CMT = "Command Settings";
 
 public static final String CAT_GEN = "general";
 public static ForgeConfigSpec.BooleanValue MSG_ALL_SERVER;
   public static boolean MSG_ALL_SERVER_DEF = false;
   private static final String MSG_ALL_SERVER_NAM = "msgAllPlayers";
   private static final String MSG_ALL_SERVER_CMT = "Command messages show to all players on server.";
   public static  ForgeConfigSpec.IntValue CMD_PERMISSION_LEVEL;
   public static int CMD_PERMISSION_LEVEL_DEF = 2;
   public static int CMD_PERMISSION_LEVEL_MIN = 0;
   public static int CMD_PERMISSION_LEVEL_MAX = 4;
   public static final String CMD_PERMISSION_LEVEL_NAM = "cmdPermissionsLevel";
   public static final String CMD_PERMISSION_LEVEL_CMT = "Sets all command to be this permission level.";

 public static final String CAT_IB = "InventoryBomb";
 public static final String CAT_IB_CMT = "InventoryBomb Command Settings";
   public static  ForgeConfigSpec.ConfigValue<String> IB_ITEM;
   public static final String IB_ITEM_DEF = Items.DEAD_BUSH.getRegistryName().toString();
   public static final String IB_ITEM_NAM = "inventoryBombItem";
   public static final String IB_ITEM_CMT = "Item that will replace all inventory slots with after items are dropped";

 public static final String CAT_DP = "DisconnectPlayer";
 public static final String CAT_DP_CMT = "DisconnectPlayer Command Settings";
   public static  ForgeConfigSpec.ConfigValue<String> DP_MSG;
   public static final String DP_MSG_DEF = " and had a Dark PANIC moment and some how left the server.";
   public static final String DP_MSG_NAM = "disconnectMsg";
   public static final String DP_MSG_CMT = "Message to show when disconnected"; 

 public static final String CAT_PN = "PlayerNudger";
 public static final String CAT_PN_CMT = "PlayerNudger Command Settings";
   public static final String CAT_PN_N = "North";
     public static  ForgeConfigSpec.IntValue PN_N_CHA;
     public static int PN_N_CHA_DEF = 4;
     public static int PN_N_CHA_MIN = 0;
     public static int PN_N_CHA_MAX = 100;
     public static final String PN_N_CHA_NAM = "North_Chance";
     public static final String PN_N_CHA_CMT = "Chance for playernudger command to nudge North";
     public static  ForgeConfigSpec.DoubleValue PN_N_STR;
     public static double PN_N_STR_DEF = .7;
     public static double PN_N_STR_MIN = 0;
     public static double PN_N_STR_MAX = 10;
     public static final String PN_N_STR_NAM = "North_Strength";
     public static final String PN_N_STR_CMT = "Strength of playernudger command nudging North";
   public static final String CAT_PN_NE = "NorthEast";
     public static ForgeConfigSpec.IntValue PN_NE_CHA;
     public static int PN_NE_CHA_DEF = 2;
     public static int PN_NE_CHA_MIN = 0;
     public static int PN_NE_CHA_MAX = 100;
     public static final String PN_NE_CHA_NAM = "Northeast_Chance";
     public static final String PN_NE_CHA_CMT = "Chance for playernudger command to nudge Northeast";
     public static ForgeConfigSpec.DoubleValue PN_NE_STR;
     public static double PN_NE_STR_DEF = .7;
     public static double PN_NE_STR_MIN = 0;
     public static double PN_NE_STR_MAX = 10;
     public static final String PN_NE_STR_NAM = "Northeast_Strength";
     public static final String PN_NE_STR_CMT = "Strength of playernudger command nudging Northeast";
   public static final String CAT_PN_E = "East";
     public static ForgeConfigSpec.IntValue PN_E_CHA;
     public static int PN_E_CHA_DEF = 4;
     public static int PN_E_CHA_MIN = 0;
     public static int PN_E_CHA_MAX = 100;
     public static final String PN_E_CHA_NAM = "East_Chance";
     public static final String PN_E_CHA_CMT = "Chance for playernudger command to nudge East";
     public static ForgeConfigSpec.DoubleValue PN_E_STR;
     public static double PN_E_STR_DEF = .7;
     public static double PN_E_STR_MIN = 0;
     public static double PN_E_STR_MAX = 10;
     public static final String PN_E_STR_NAM = "East_Strength";
     public static final String PN_E_STR_CMT = "Strength of playernudger command nudging East";
   public static final String CAT_PN_SE = "SouthEast";
     public static ForgeConfigSpec.IntValue PN_SE_CHA;
     public static int PN_SE_CHA_DEF = 2;
     public static int PN_SE_CHA_MIN = 0;
     public static int PN_SE_CHA_MAX = 100;
     public static final String PN_SE_CHA_NAM = "SouthEast_Chance";
     public static final String PN_SE_CHA_CMT = "Chance for playernudger command to nudge Southeast";
     public static ForgeConfigSpec.DoubleValue PN_SE_STR;
     public static double PN_SE_STR_DEF = .7;
     public static double PN_SE_STR_MIN = 0;
     public static double PN_SE_STR_MAX = 10;
     public static final String PN_SE_STR_NAM = "SouthEast_Strength";
     public static final String PN_SE_STR_CMT = "Strength of playernudger command nudging Southeast";
   public static String CAT_PN_S = "South";
     public static ForgeConfigSpec.IntValue PN_S_CHA;
     public static int PN_S_CHA_DEF = 4;
     public static int PN_S_CHA_MIN = 0;
     public static int PN_S_CHA_MAX = 100;
     public static final String PN_S_CHA_NAM = "South_Chance";
     public static final String PN_S_CHA_CMT = "Chance for playernudger command to nudge South";
     public static ForgeConfigSpec.DoubleValue PN_S_STR;
     public static double PN_S_STR_DEF = .7;
     public static double PN_S_STR_MIN = 0;
     public static double PN_S_STR_MAX = 10;
     public static final String PN_S_STR_NAM = "South_Strength";
     public static final String PN_S_STR_CMT = "Strength of playernudger command nudging South";
   public static final String CAT_PN_SW = "SouthWest";
     public static ForgeConfigSpec.IntValue PN_SW_CHA;
     public static int PN_SW_CHA_DEF = 2;
     public static int PN_SW_CHA_MIN = 0;
     public static int PN_SW_CHA_MAX = 100;
     public static final String PN_SW_CHA_NAM = "SouthWest_Chance";
     public static final String PN_SW_CHA_CMT = "Chance for playernudger command to nudge Southwest";
     public static ForgeConfigSpec.DoubleValue PN_SW_STR;
     public static double PN_SW_STR_DEF = .7;
     public static double PN_SW_STR_MIN = 0;
     public static double PN_SW_STR_MAX = 10;
     public static final String PN_SW_STR_NAM = "SouthWest_Strength";
     public static final String PN_SW_STR_CMT = "Strength of playernudger command nudging Southwest";
   public static final String CAT_PN_W = "West";
     public static ForgeConfigSpec.IntValue PN_W_CHA;
     public static int PN_W_CHA_DEF = 4;
     public static int PN_W_CHA_MIN = 0;
     public static int PN_W_CHA_MAX = 100;
     public static final String PN_W_CHA_NAM = "West_Chance";
     public static final String PN_W_CHA_CMT = "Chance for playernudger command to nudge West";
     public static ForgeConfigSpec.DoubleValue PN_W_STR;
     public static double PN_W_STR_DEF = .7;
     public static double PN_W_STR_MIN = 0;
     public static double PN_W_STR_MAX = 10;
     public static final String PN_W_STR_NAM = "West_Strength";
     public static final String PN_W_STR_CMT = "Strength of playernudger command nudging West";
   public static final String CAT_PN_NW = "NorthWest";
     public static ForgeConfigSpec.IntValue PN_NW_CHA;
     public static int PN_NW_CHA_DEF = 2;
     public static int PN_NW_CHA_MIN = 0;
     public static int PN_NW_CHA_MAX = 100;
     public static final String PN_NW_CHA_NAM = "NorthWest_Chance";
     public static final String PN_NW_CHA_CMT = "Chance for playernudger command to nudge Northwest";
     public static ForgeConfigSpec.DoubleValue PN_NW_STR;
     public static double PN_NW_STR_DEF = .7;
     public static double PN_NW_STR_MIN = 0;
     public static double PN_NW_STR_MAX = 10;
     public static final String PN_NW_STR_NAM = "NorthWest_Strength";
     public static final String PN_NW_STR_CMT = "Strength of playernudger command nudging Northwest";
   public static final String CAT_PN_U = "Up";
     public static ForgeConfigSpec.IntValue PN_U_CHA;
     public static int PN_U_CHA_DEF = 1;
     public static int PN_U_CHA_MIN = 0;
     public static int PN_U_CHA_MAX = 100;
     public static final String PN_U_CHA_NAM = "Up_Chance";
     public static final String PN_U_CHA_CMT = "Chance for playernudger command to nudge Up";
     public static ForgeConfigSpec.DoubleValue PN_U_STR;
     public static double PN_U_STR_DEF = .7;
     public static double PN_U_STR_MIN = 0;
     public static double PN_U_STR_MAX = 10;
     public static final String PN_U_STR_NAM = "Up_Strength";
     public static final String PN_U_STR_CMT = "Strength of playernudger command nudging Up";
   public static final String CAT_PN_D = "Down";
     public static ForgeConfigSpec.IntValue PN_D_CHA;
     public static int PN_D_CHA_DEF = 0;
     public static int PN_D_CHA_MIN = 0;
     public static int PN_D_CHA_MAX = 100;
     public static final String PN_D_CHA_NAM = "Down_Chance";
     public static final String PN_D_CHA_CMT = "Chance for playernudger command to nudge Down";
     public static ForgeConfigSpec.DoubleValue PN_D_STR;
     public static double PN_D_STR_DEF = .7;
     public static double PN_D_STR_MIN = 0;
     public static double PN_D_STR_MAX = 10;
     public static final String PN_D_STR_NAM = "Down_Strength";
     public static final String PN_D_STR_CMT = "Strength of playernudger command nudging Down";
   public static final String CAT_PN_PFD = "PlayerFacingDirections";
     public static ForgeConfigSpec.IntValue PN_PFD_FOR;
     public static int PN_PFD_FOR_DEF = 1;
     public static int PN_PFD_FOR_MIN = 0;
     public static int PN_PFD_FOR_MAX = 100;
     public static final String PN_PFD_FOR_NAM = "Forward_Multiplier";
     public static final String PN_PFD_FOR_CMT = "Multiplier to increase the chance to nudge where the player is looking";
     public static ForgeConfigSpec.IntValue PN_PFD_BAC;
     public static int PN_PFD_BAC_DEF = 1;
     public static int PN_PFD_BAC_MIN = 0;
     public static int PN_PFD_BAC_MAX = 100;
     public static final String PN_PFD_BAC_NAM = "Backward_Multiplier";
     public static final String PN_PFD_BAC_CMT = "Multiplier to increase the chance to nudge opposite where the player is looking";
     public static ForgeConfigSpec.IntValue PN_PFD_LEF;
     public static int PN_PFD_LEF_DEF = 1;
     public static int PN_PFD_LEF_MIN = 0;
     public static int PN_PFD_LEF_MAX = 100;
     public static final String PN_PFD_LEF_NAM = "Left_Multiplier";
     public static final String PN_PFD_LEF_CMT = "Multiplier to increase the chance to nudge the player to the left";
     public static ForgeConfigSpec.IntValue PN_PFD_RIG;
     public static int PN_PFD_RIG_DEF = 1;
     public static int PN_PFD_RIG_MIN = 0;
     public static int PN_PFD_RIG_MAX = 100;
     public static final String PN_PFD_RIG_NAM = "Right_Multiplier";
     public static final String PN_PFD_RIG_CMT = "Multiplier to increase the chance to nudge the player to the right";

 public static final String CAT_RT = "RandomTeleport";
 public static final String CAT_RT_CMT = "RandomTeleport Command Settings";
   public static final String CAT_RT_X = "X Axis";
     public static ForgeConfigSpec.IntValue RT_X_MIN;
     public static int RT_X_MIN_DEF = 100;
     public static int RT_X_MIN_MIN = 1;
     public static int RT_X_MIN_MAX = 100000;
     public static final String RT_X_MIN_NAM = "randomTeleportXMin";
     public static final String RT_X_MIN_CMT = "Min Block +/- range on the X Axis that could teleport along";
     public static ForgeConfigSpec.IntValue RT_X_MAX;
     public static int RT_X_MAX_DEF = 500;
     public static int RT_X_MAX_MIN = 1;
     public static int RT_X_MAX_MAX = 100000;
     public static final String RT_X_MAX_NAM = "randomTeleportXMax";
     public static final String RT_X_MAX_CMT = "Max Block +/- range on the X Axis that could teleport along";
   public static final String CAT_RT_Y = "Y Axis";
     public static ForgeConfigSpec.IntValue RT_Y_MIN;
     public static int RT_Y_MIN_DEF = 0;
     public static int RT_Y_MIN_MIN = 1;
     public static int RT_Y_MIN_MAX = 255;
     public static final String RT_Y_MIN_NAM = "randomTeleportYMin";
     public static final String RT_Y_MIN_CMT = "Min Block +/- range on the Y Axis that could teleport along";
     public static ForgeConfigSpec.IntValue RT_Y_MAX;
     public static int RT_Y_MAX_DEF = 10;
     public static int RT_Y_MAX_MIN = 1;
     public static int RT_Y_MAX_MAX = 255;
     public static final String RT_Y_MAX_NAM = "randomTeleportYMax";
     public static final String RT_Y_MAX_CMT = "Max Block +/- range on the Y Axis that could teleport along";
   public static final String CAT_RT_Z = "Z Axis";
     public static ForgeConfigSpec.IntValue RT_Z_MIN;
     public static int RT_Z_MIN_DEF = 100;
     public static int RT_Z_MIN_MIN = 1;
     public static int RT_Z_MIN_MAX = 100000;
     public static final String RT_Z_MIN_NAM = "randomTeleportZMin";
     public static final String RT_Z_MIN_CMT = "Min Block +/- range on the Z Axis that could teleport along";
     public static ForgeConfigSpec.IntValue RT_Z_MAX;
     public static int RT_Z_MAX_DEF = 500;
     public static int RT_Z_MAX_MIN = 1;
     public static int RT_Z_MAX_MAX = 100000;
     public static final String RT_Z_MAX_NAM = "randomTeleportZMax";
     public static final String RT_Z_MAX_CMT = "Max Block +/- range on the Z Axis that could teleport along";
       
   public static final String CAT_QH = "QuickHide";
   public static final String CAT_QH_CMT = "QuickHide Command Settings";
     public static ConfigValue<List<? extends String>> QH_LIST;
     public static List<String> QH_LIST_DEF = quiHidList_def; 
     public static final String QH_LIST_NAM = "QuickHideList";
     public static final String QH_LIST_CMT = "List of random options that can be chosen when using quickhide command";
 
   public static final String CAT_SM = "SummonMount";
   public static final String CAT_SM_CMT = "SummonMount Command Settings";
     public static ConfigValue<List<? extends String>> SM_IN_LIST = null;
     public static List<String> SM_IN_LIST_DEF = sumMountINCLUDEClassList_def; 
     public static final String SM_IN_LIST_NAM = "SummonMountClass";
     public static final String SM_IN_LIST_CMT = "List of Classifications for SummonMount command to INCLUDE from possible entities. Use \"ANY\" for all.";
     public static ConfigValue<List<? extends String>> SM_EX_LIST = null;
     public static List<String> SM_EX_LIST_DEF = sumMountEXLUDEList_def; 
     public static final String SM_EX_LIST_NAM = "SummonMount";
     public static final String SM_EX_LIST_CMT = "List of entities for SummonMount command to EXCLUDE from possible entities";
     public static ForgeConfigSpec.BooleanValue SM_CUS_NAME = null;
     public static boolean SM_CUS_NAME_DEF = true;
     private static String SM_CUS_NAME_NAM = "summonMountCustomName";
     private static final String SM_CUS_NAME_CMT = "Custom name for SummonMount entities.";
     public static ForgeConfigSpec.BooleanValue SM_CUS_TAME = null;
     public static boolean SM_CUS_TAME_DEF = true;
     private static final String SM_CUS_TAME_NAM = "summonMountTamed";
     private static final String SM_CUS_TAME_CMT = "Try to summon entity already tamed.";
 
   public static final String CAT_SR = "SummonRider";
   public static final String CAT_SR_CMT = "SummonRider Command Settings";
     public static ConfigValue<List<? extends String>> SR_IN_LIST = null;
     public static List<String> SR_IN_LIST_DEF = sumRiderClassList_def; 
     public static final String SR_IN_LIST_NAM = "SummonRiderClass";
     public static final String SR_IN_LIST_CMT = "List of Classifications for SummonRide command to INCLUDE from possible entities. Use \"ANY\" for all.";
     
   public static final String CAT_DB = "DisplayDeathBoard";
   public static final String CAT_DB_CMT = "DisplayDeathBoard Command Settings";
     public static ConfigValue<List<? extends String>> DB_LIST = null;
     public static List<String> DB_LIST_DEF = deathBoardList_def;
     public static final String DB_LIST_NAM = "DisplayDeathBoard";
     public static final String DB_LIST_CMT = "List of strings to put at the end based on position. Ranks without a separate value will use whatever the last value is";
     
   public static final String CAT_RG = "RandomGift";
   public static final String CAT_RG_CMT = "RandomGift Command Settings";
     public static ConfigValue<List<? extends String>> RG_LIST = null;
     public static List<String> RG_LIST_DEF = randGiftList_def; 
     public static final String RG_LIST_NAM = "RandomGift";
     public static final String RG_LIST_CMT = "List of items (resource location,amount,weighted chance) to be chosen for RandomGift command to choose from";
 
 static {
  PopLists();
  
  //*
  BUILDER.comment(CAT_CMDS_CMT).push(CAT_CMDS);
  
  BUILDER.push(CAT_GEN);
  MSG_ALL_SERVER=BUILDER.comment(MSG_ALL_SERVER_CMT).define(MSG_ALL_SERVER_NAM, MSG_ALL_SERVER_DEF);
  CMD_PERMISSION_LEVEL=BUILDER.comment(CMD_PERMISSION_LEVEL_CMT).defineInRange(CMD_PERMISSION_LEVEL_NAM, CMD_PERMISSION_LEVEL_DEF, CMD_PERMISSION_LEVEL_MIN, CMD_PERMISSION_LEVEL_MAX);
  BUILDER.pop();
  
  BUILDER.comment(CAT_IB_CMT).push(CAT_IB);
  IB_ITEM=BUILDER.comment(IB_ITEM_CMT).define(IB_ITEM_NAM, IB_ITEM_DEF);
  BUILDER.pop();
  
  BUILDER.comment(CAT_DP_CMT).push(CAT_DP);
  DP_MSG=BUILDER.comment(DP_MSG_CMT).define(DP_MSG_NAM, DP_MSG_DEF);
  BUILDER.pop();
  
  BUILDER.comment(CAT_PN_CMT).push(CAT_PN);
  BUILDER.push(CAT_PN_N);
  PN_N_CHA=BUILDER.comment(PN_N_CHA_CMT).defineInRange(PN_N_CHA_NAM, PN_N_CHA_DEF, PN_N_CHA_MIN, PN_N_CHA_MAX);
  PN_N_STR=BUILDER.comment(PN_N_STR_CMT).defineInRange(PN_N_STR_NAM, PN_N_STR_DEF, PN_N_STR_MIN, PN_N_STR_MAX);
  BUILDER.pop().push(CAT_PN_NE);
  PN_NE_CHA=BUILDER.comment(PN_NE_CHA_CMT).defineInRange(PN_NE_CHA_NAM, PN_NE_CHA_DEF, PN_NE_CHA_MIN, PN_NE_CHA_MAX);
  PN_NE_STR=BUILDER.comment(PN_NE_STR_CMT).defineInRange(PN_NE_STR_NAM, PN_NE_STR_DEF, PN_NE_STR_MIN, PN_NE_STR_MAX);
  BUILDER.pop().push(CAT_PN_E);
  PN_E_CHA=BUILDER.comment(PN_E_CHA_CMT).defineInRange(PN_E_CHA_NAM, PN_E_CHA_DEF, PN_E_CHA_MIN, PN_E_CHA_MAX);
  PN_E_STR=BUILDER.comment(PN_E_STR_CMT).defineInRange(PN_E_STR_NAM, PN_E_STR_DEF, PN_E_STR_MIN, PN_E_STR_MAX);
  BUILDER.pop().push(CAT_PN_SE);
  PN_SE_CHA=BUILDER.comment(PN_SE_CHA_CMT).defineInRange(PN_SE_CHA_NAM, PN_SE_CHA_DEF, PN_SE_CHA_MIN, PN_SE_CHA_MAX);
  PN_SE_STR=BUILDER.comment(PN_SE_STR_CMT).defineInRange(PN_SE_STR_NAM, PN_SE_STR_DEF, PN_SE_STR_MIN, PN_SE_STR_MAX);
  BUILDER.pop().push(CAT_PN_S);
  PN_S_CHA=BUILDER.comment(PN_S_CHA_CMT).defineInRange(PN_S_CHA_NAM, PN_S_CHA_DEF, PN_S_CHA_MIN, PN_S_CHA_MAX);
  PN_S_STR=BUILDER.comment(PN_S_STR_CMT).defineInRange(PN_S_STR_NAM, PN_S_STR_DEF, PN_S_STR_MIN, PN_S_STR_MAX);
  BUILDER.pop().push(CAT_PN_SW);
  PN_SW_CHA=BUILDER.comment(PN_SW_CHA_CMT).defineInRange(PN_SW_CHA_NAM, PN_SW_CHA_DEF, PN_SW_CHA_MIN, PN_SW_CHA_MAX);
  PN_SW_STR=BUILDER.comment(PN_SW_STR_CMT).defineInRange(PN_SW_STR_NAM, PN_SW_STR_DEF, PN_SW_STR_MIN, PN_SW_STR_MAX);
  BUILDER.pop().push(CAT_PN_W);
  PN_W_CHA=BUILDER.comment(PN_W_CHA_CMT).defineInRange(PN_W_CHA_NAM, PN_W_CHA_DEF, PN_W_CHA_MIN, PN_W_CHA_MAX);
  PN_W_STR=BUILDER.comment(PN_W_STR_CMT).defineInRange(PN_W_STR_NAM, PN_W_STR_DEF, PN_W_STR_MIN, PN_W_STR_MAX);
  BUILDER.pop().push(CAT_PN_NW);
  PN_NW_CHA=BUILDER.comment(PN_NW_CHA_CMT).defineInRange(PN_NW_CHA_NAM, PN_NW_CHA_DEF, PN_NW_CHA_MIN, PN_NW_CHA_MAX);
  PN_NW_STR=BUILDER.comment(PN_NW_STR_CMT).defineInRange(PN_NW_STR_NAM, PN_NW_STR_DEF, PN_NW_STR_MIN, PN_NW_STR_MAX);
  BUILDER.pop().push(CAT_PN_U);
  PN_U_CHA=BUILDER.comment(PN_U_CHA_CMT).defineInRange(PN_U_CHA_NAM, PN_U_CHA_DEF, PN_U_CHA_MIN, PN_U_CHA_MAX);
  PN_U_STR=BUILDER.comment(PN_U_STR_CMT).defineInRange(PN_U_STR_NAM, PN_U_STR_DEF, PN_U_STR_MIN, PN_U_STR_MAX);
  BUILDER.pop().push(CAT_PN_D);
  PN_D_CHA=BUILDER.comment(PN_D_CHA_CMT).defineInRange(PN_D_CHA_NAM, PN_D_CHA_DEF, PN_D_CHA_MIN, PN_D_CHA_MAX);
  PN_D_STR=BUILDER.comment(PN_D_STR_CMT).defineInRange(PN_D_STR_NAM, PN_D_STR_DEF, PN_D_STR_MIN, PN_D_STR_MAX);
  BUILDER.pop().push(CAT_PN_PFD);
  PN_PFD_FOR=BUILDER.comment(PN_PFD_FOR_CMT).defineInRange(PN_PFD_FOR_NAM, PN_PFD_FOR_DEF, PN_PFD_FOR_MIN, PN_PFD_FOR_MAX);
  PN_PFD_BAC=BUILDER.comment(PN_PFD_BAC_CMT).defineInRange(PN_PFD_BAC_NAM, PN_PFD_BAC_DEF, PN_PFD_BAC_MIN, PN_PFD_BAC_MAX);
  PN_PFD_LEF=BUILDER.comment(PN_PFD_LEF_CMT).defineInRange(PN_PFD_LEF_NAM, PN_PFD_LEF_DEF, PN_PFD_LEF_MIN, PN_PFD_LEF_MAX);
  PN_PFD_RIG=BUILDER.comment(PN_PFD_RIG_CMT).defineInRange(PN_PFD_RIG_NAM, PN_PFD_RIG_DEF, PN_PFD_RIG_MIN, PN_PFD_RIG_MAX);
  BUILDER.pop(2);
  
  BUILDER.comment(CAT_RT_CMT).push(CAT_RT);
  BUILDER.push(CAT_RT_X);
  RT_X_MIN=BUILDER.comment(RT_X_MIN_CMT).defineInRange(RT_X_MIN_NAM, RT_X_MIN_DEF, RT_X_MIN_MIN, RT_X_MIN_MAX);
  RT_X_MAX=BUILDER.comment(RT_X_MAX_CMT).defineInRange(RT_X_MAX_NAM, RT_X_MAX_DEF, RT_X_MAX_MIN, RT_X_MAX_MAX);
  BUILDER.pop().push(CAT_RT_Y);
  RT_Y_MIN=BUILDER.comment(RT_Y_MIN_CMT).defineInRange(RT_Y_MIN_NAM, RT_Y_MIN_DEF, RT_Y_MIN_MIN, RT_Y_MIN_MAX);
  RT_Y_MAX=BUILDER.comment(RT_Y_MAX_CMT).defineInRange(RT_Y_MAX_NAM, RT_Y_MAX_DEF, RT_Y_MAX_MIN, RT_Y_MAX_MAX);
  BUILDER.pop().push(CAT_RT_Z);
  RT_Z_MIN=BUILDER.comment(RT_Z_MIN_CMT).defineInRange(RT_Z_MIN_NAM, RT_Z_MIN_DEF, RT_Z_MIN_MIN, RT_Z_MIN_MAX);
  RT_Z_MAX=BUILDER.comment(RT_Z_MAX_CMT).defineInRange(RT_Z_MAX_NAM, RT_Z_MAX_DEF, RT_Z_MAX_MIN, RT_Z_MAX_MAX);
  BUILDER.pop(2);
  
  BUILDER.comment(CAT_QH_CMT).push(CAT_QH);
//  QH_LIST=BUILDER.comment(QH_LIST_CMT).defineList(QH_LIST_NAM, quiHidList_def, (s -> s instanceof String));
  QH_LIST=BUILDER.comment(QH_LIST_CMT).defineList(QH_LIST_NAM, QH_LIST_DEF, s -> s instanceof String);
  BUILDER.pop();
  

  
  //*
  BUILDER.comment(CAT_SM_CMT).push(CAT_SM);
//  SM_IN_LIST=BUILDER.comment(SM_IN_LIST_CMT).defineList(SM_IN_LIST_NAM, sumMountINCLUDEClassList_def, s -> s instanceof String);
  SM_IN_LIST=BUILDER.comment(SM_IN_LIST_CMT).defineList(SM_IN_LIST_NAM, SM_IN_LIST_DEF, s -> s instanceof String);
//  SM_EX_LIST=BUILDER.comment(SM_EX_LIST_CMT).defineList(SM_EX_LIST_NAM, sumMountEXLUDEList_def, s -> s instanceof String);
  SM_EX_LIST=BUILDER.comment(SM_EX_LIST_CMT).defineList(SM_EX_LIST_NAM, SM_EX_LIST_DEF, s -> s instanceof String);
  SM_CUS_NAME=BUILDER.comment(SM_CUS_NAME_CMT).define(SM_CUS_NAME_NAM, SM_CUS_NAME_DEF);
  SM_CUS_TAME=BUILDER.comment(SM_CUS_TAME_CMT).define(SM_CUS_TAME_NAM, SM_CUS_TAME_DEF);
  BUILDER.pop();
  
  BUILDER.comment(CAT_SR_CMT).push(CAT_SR);
//  SR_IN_LIST=BUILDER.comment(SR_IN_LIST_CMT).defineList(SR_IN_LIST_NAM, sumRiderClassList_def, s -> s instanceof String);
  SR_IN_LIST=BUILDER.comment(SR_IN_LIST_CMT).defineList(SR_IN_LIST_NAM, SR_IN_LIST_DEF, s -> s instanceof String);
  BUILDER.pop();
  
  BUILDER.comment(CAT_DB_CMT).push(CAT_DB);
//  DB_LIST=BUILDER.comment(DB_LIST_CMT).defineList(DB_LIST_NAM, deathBoardList_def, s -> s instanceof String);
  DB_LIST=BUILDER.comment(DB_LIST_CMT).defineList(DB_LIST_NAM, DB_LIST_DEF, s -> s instanceof String);
  BUILDER.pop();
  
  BUILDER.comment(CAT_RG_CMT).push(CAT_RG);
//  RG_LIST=BUILDER.comment(RG_LIST_CMT).defineList(RG_LIST_NAM, randGiftList_def, s -> s instanceof String);
  RG_LIST=BUILDER.comment(RG_LIST_CMT).defineList(RG_LIST_NAM, RG_LIST_DEF, s -> s instanceof String);
  BUILDER.pop();
  //*/
  BUILDER.pop();
  
  STCONFIG_SPEC = BUILDER.build();
 }
 
 private static void PopLists() {
  quiHidList_def=Lists.newArrayList();
  quiHidList_def.add(Items.DEAD_BUSH.getRegistryName().toString()+",quick hide in these bushes!");
  quiHidList_def.add(Items.WHEAT.getRegistryName().toString()+",quick hide in the wheat field!");
  quiHidList_def.add(Items.FEATHER.getRegistryName().toString()+",quick act like a chicken!");
  quiHidList_def.add(Items.PAINTING.getRegistryName().toString()+",quick blend into the wall!");
  QH_LIST_DEF=quiHidList_def;
  
  sumMountINCLUDEClassList_def=Lists.newArrayList();
  sumMountINCLUDEClassList_def.add(MobCategory.AMBIENT.getName());
  sumMountINCLUDEClassList_def.add(MobCategory.CREATURE.getName());
  sumMountINCLUDEClassList_def.add(MobCategory.WATER_CREATURE.getName());
  sumMountINCLUDEClassList_def.add(MobCategory.WATER_AMBIENT.getName());
  sumMountINCLUDEClassList_def.add(MobCategory.MONSTER.getName());
  SM_IN_LIST_DEF = sumMountINCLUDEClassList_def; 
  
  sumMountEXLUDEList_def=Lists.newArrayList();
  sumMountEXLUDEList_def.add(EntityType.WITHER.getRegistryName().toString());
  sumMountEXLUDEList_def.add(EntityType.ENDER_DRAGON.getRegistryName().toString());
  SM_EX_LIST_DEF = sumMountEXLUDEList_def;
  
  sumRiderClassList_def=Lists.newArrayList();
  sumRiderClassList_def.add(MobCategory.AMBIENT.getName());
  sumRiderClassList_def.add(MobCategory.CREATURE.getName()); 
  sumRiderClassList_def.add(MobCategory.WATER_CREATURE.getName());
  sumRiderClassList_def.add(MobCategory.WATER_AMBIENT.getName());
  SR_IN_LIST_DEF = sumRiderClassList_def;
  
  deathBoardList_def=Lists.newArrayList();
  deathBoardList_def.add(" rip");
  DB_LIST_DEF = deathBoardList_def;
  
  randGiftList_def=Lists.newArrayList();
  randGiftList_def.add(Items.DIAMOND.getRegistryName().toString()+",1,1");
  randGiftList_def.add(Items.FEATHER.getRegistryName().toString()+",64,99");
  RG_LIST_DEF = randGiftList_def;
 }
 
     
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 /*
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
 public static boolean summonMountTamed;
 public static List<MobCategory> summonMountIncludeList;
 public static List<MobCategory> summonRiderIncludeList;
 public static List<String> displayDeathBoardPlacesTextList;
 public static List<String> randomGiftList;
 
 public static void loadConfig(ForgeConfigSpec spec, Path path) {

  final CommentedFileConfig configData = CommentedFileConfig.builder(path)
          .sync()
          .autosave()
          .writingMode(WritingMode.REPLACE)
          .build();

  configData.load();
  spec.setConfig(configData);
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
  
  summonMountIncludeList=new ArrayList<>();
  
  if (STCONFIG.summonMountIncludeList.get().stream().map(String::toLowerCase).collect(Collectors.toList()).contains("any")) {
     for( MobCategory ec : MobCategory.values()) {
      summonMountIncludeList.add(ec);
     }
   
  } else {  
   STCONFIG.summonMountIncludeList.get().forEach(str -> {
    if (str!=null) {
     summonMountIncludeList.add(MobCategory.byName(str.toLowerCase()));
    }
   });
  }  
  summonMountExcludeList= new ArrayList<>();
  STCONFIG.summonMountExcludeList.get().forEach(str -> {
   if (str!=null) {
   summonMountExcludeList.add(str);
   }
  });
  summonMountCustomName=STCONFIG.summonMountCustomName.get();
  summonMountTamed=STCONFIG.summonMountTamed.get();
  
  summonRiderIncludeList=new ArrayList<>();
  
  if (STCONFIG.summonRiderIncludeList.get().stream().map(String::toLowerCase).collect(Collectors.toList()).contains("any")) {
     for( MobCategory ec : MobCategory.values()) {
      summonRiderIncludeList.add(ec);
     }
   
  } else {  
   STCONFIG.summonRiderIncludeList.get().forEach(str -> {
    if (str!=null) {
     summonRiderIncludeList.add(MobCategory.byName(str.toLowerCase()));
    }
   });
  }  
  
  displayDeathBoardPlacesTextList= new ArrayList<>();
  STCONFIG.displayDeathBoardPlacesTextList.get().forEach(str -> {
   if (str!=null) {
    displayDeathBoardPlacesTextList.add(str);
    }
   });
  
  randomGiftList= new ArrayList<>();
  STCONFIG.randomGiftList.get().forEach(str -> {
   if (str!=null) {
    randomGiftList.add(str);
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
  public final ConfigValue<List<? extends String>> summonMountIncludeList;
  public final BooleanValue summonMountCustomName;
  public final BooleanValue summonMountTamed;
  
  public final ConfigValue<List<? extends String>> summonRiderIncludeList;
  
  public final ConfigValue<List<? extends String>> displayDeathBoardPlacesTextList;
  
  public final ConfigValue<List<? extends String>> randomGiftList;  
  
  
  public SwapThingsConfig(ForgeConfigSpec.Builder builder) {
   builder.comment("Command Settings").push("commands");
   builder.push("general");
   cmdMsgAllServer=builder.comment("Command messages show to all players on server.").define("msgAllPlayers", false);
   cmdSTPermissionsLevel=builder.comment("Sets all command to be this permission level.").defineInRange("cmdPermissionsLevel", 2, 0, 4);
   builder.pop();
   
   
   
//   InventoryBomb config start
   builder.comment("InventoryBomb Command Settings").push("InventoryBomb");
   inventoryBombItem=builder.comment("Item that will replace all inventory slots with after items are dropped").define("inventoryBombItem", Items.DEAD_BUSH.getRegistryName().toString());
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
   quiHidList.add(Items.DEAD_BUSH.getRegistryName().toString()+",quick hide in these bushes!");
   quiHidList.add(Items.WHEAT.getRegistryName().toString()+",quick hide in the wheat field!");
   quiHidList.add(Items.FEATHER.getRegistryName().toString()+",quick act like a chicken!");
   quiHidList.add(Items.PAINTING.getRegistryName().toString()+",quick blend into the wall!");
   
   quickHideList=builder.comment("List of random options that can be chosen when using quickhide command").defineList("Quick Hide Options", quiHidList, s -> s instanceof String);
   builder.pop();
//   QuickHide config end
   
//   SummonMount config start
   builder.comment("SummonMount Command settings").push("SummonMount");
   
   List<String> sumMountList=Lists.newArrayList();
   sumMountList.add(EntityType.WITHER.getRegistryName().toString());
   sumMountList.add(EntityType.ENDER_DRAGON.getRegistryName().toString());
   
   List<String> sumMountClassList=Lists.newArrayList();
   sumMountClassList.add(MobCategory.AMBIENT.getName());
   sumMountClassList.add(MobCategory.CREATURE.getName());
   sumMountClassList.add(MobCategory.WATER_CREATURE.getName());
   sumMountClassList.add(MobCategory.WATER_AMBIENT.getName());
   sumMountClassList.add(MobCategory.MONSTER.getName());
   
   summonMountIncludeList=builder.comment("List of Classifications for SummonMount command to INCLUDE from possible entities. Use \"ANY\" for all.").defineList("SummonMountClass", sumMountClassList, s -> s instanceof String);
   summonMountExcludeList=builder.comment("List of entities for SummonMount command to EXCLUDE from possible entities").defineList("SummonMount", sumMountList, s -> s instanceof String);
   summonMountCustomName=builder.comment("Custom name for SummonMount entities.").define("summonMountCustomName", true);
   summonMountTamed=builder.comment("Try to summon entity already tamed.").define("summonMountTamed", true);
   
   builder.pop();
//   SummonMount config end
   
// SummonRider config start
 builder.comment("SummonRider Command settings").push("SummonRider");
 
 List<String> sumRiderClassList=Lists.newArrayList();
 sumRiderClassList.add(MobCategory.AMBIENT.getName());
 sumRiderClassList.add(MobCategory.CREATURE.getName()); 
 sumRiderClassList.add(MobCategory.WATER_CREATURE.getName());
 sumRiderClassList.add(MobCategory.WATER_AMBIENT.getName());
 
 summonRiderIncludeList=builder.comment("List of Classifications for SummonRide command to INCLUDE from possible entities. Use \"ANY\" for all.").defineList("SummonRiderClass", sumRiderClassList, s -> s instanceof String);
 
 builder.pop();
// SummonRider config end
   
   
   
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
 
 displayDeathBoardPlacesTextList=builder.comment("List of strings to put at the end based on position. Ranks without a separate value will use whatever the last value is").defineList("DisplayDeathBoard", deathBoardList, s -> s instanceof String);
 
 builder.pop();
// DisplayDeathBoard config end
 
//RandomGift config start
builder.comment("RandomGift Command settings").push("RandomGift");

List<String> randGiftList=Lists.newArrayList();
randGiftList.add(Items.DIAMOND.getRegistryName().toString()+",1,1");
randGiftList.add(Items.FEATHER.getRegistryName().toString()+",64,99");

randomGiftList=builder.comment("List of items (resource location,amount,weighted chance) to be chosen for RandomGift command to choose from").defineList("RandomGift", randGiftList, s -> s instanceof String);

builder.pop();
//RandomGift config end
   
   builder.pop();
  }  
 }
 
 */
}