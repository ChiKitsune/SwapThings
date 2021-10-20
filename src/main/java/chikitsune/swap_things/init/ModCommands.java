package chikitsune.swap_things.init;

import chikitsune.swap_things.SwappingThings;
import chikitsune.swap_things.commands.ArchCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SwappingThings.MODID)
public class ModCommands {
 @SubscribeEvent
 public static void registerCommands(final RegisterCommandsEvent event) {
  
  ArchCommand.register(event.getDispatcher());
 }
}
