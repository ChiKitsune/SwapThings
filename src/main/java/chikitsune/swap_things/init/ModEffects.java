package chikitsune.swap_things.init;

import chikitsune.swap_things.SwappingThings;
import chikitsune.swap_things.effects.FOVFlipEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(SwappingThings.MODID)
public class ModEffects {
 
 public static final FOVFlipEffect fovFlip=null;
 
 @Mod.EventBusSubscriber(modid = SwappingThings.MODID, bus = Bus.MOD)
 public static class RegistrationHandler {
  
  @SubscribeEvent
  public static void registerPotions(final RegistryEvent.Register<Effect> event) {
   final Effect[] potions = {
//     new FOVFlipEffect(EffectType.NEUTRAL, 2, 2, 2).setRegistryName("fovflip"),
   };
   event.getRegistry().registerAll(potions);
  }
 }
}
