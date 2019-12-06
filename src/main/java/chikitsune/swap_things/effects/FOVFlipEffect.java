package chikitsune.swap_things.effects;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class FOVFlipEffect extends Effect {
 protected static Integer max_dur=0;
 
 protected FOVFlipEffect(final EffectType effectType, final int liquidColor) {
  super(effectType, liquidColor);
 }

 public FOVFlipEffect(final EffectType effectType, final int liquidR, final int liquidG, final int liquidB) {
  this(effectType, new Color(liquidR, liquidG, liquidB).getRGB());
 }
 
 @Override
 public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
  if (entityLivingBaseIn instanceof ServerPlayerEntity && !entityLivingBaseIn.isSpectator()) {
   
   if (max_dur>1) {
   Minecraft.getInstance().getRenderManager().options.fov*=-1;
   } else {
    Minecraft.getInstance().getRenderManager().options.fov=Math.abs(Minecraft.getInstance().getRenderManager().options.fov);
   }
  }
 }
 
 @Override
 public boolean isReady(int duration, int amplifier) {
  boolean tBool=false;
  if (duration>max_dur) {
   max_dur=duration;
  } else if (duration==1) {
   max_dur=1;
  }
  
  if (max_dur==duration) tBool=true;
  if (duration==1) tBool=true;
  
  return (max_dur==duration || duration==1);
 }
}
