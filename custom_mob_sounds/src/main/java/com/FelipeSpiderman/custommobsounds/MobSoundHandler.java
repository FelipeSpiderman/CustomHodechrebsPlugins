package com.FelipeSpiderman.custommobsounds;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MobSoundHandler {
    @Inject(
            method = "getHurtSound",
            at = @At("HEAD"),
            cancellable = true
    )
    private void customHurtSound(DamageSource damageSource, CallbackInfoReturnable<SoundEvent> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        String mobId = EntityType.getId(entity.getType()).toString();
        String soundId = ConfigHandler.config.has(mobId) ? ConfigHandler.config.getAsJsonObject(mobId).get("hurt").getAsString() : null;
        if (soundId != null && SoundRegistry.SOUND_EVENTS.containsKey(soundId)) {
            cir.setReturnValue(SoundRegistry.SOUND_EVENTS.get(soundId));
        }
    }

    @Inject(
            method = "getDeathSound",
            at = @At("HEAD"),
            cancellable = true
    )
    private void customDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        String mobId = EntityType.getId(entity.getType()).toString();
        String soundId = ConfigHandler.config.has(mobId) ? ConfigHandler.config.getAsJsonObject(mobId).get("death").getAsString() : null;
        if (soundId != null && SoundRegistry.SOUND_EVENTS.containsKey(soundId)) {
            cir.setReturnValue(SoundRegistry.SOUND_EVENTS.get(soundId));
        }
    }

    @Inject(
            method = "getAmbientSound",
            at = @At("HEAD"),
            cancellable = true
    )
    private void customAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        String mobId = EntityType.getId(entity.getType()).toString();
        String soundId = ConfigHandler.config.has(mobId) ? ConfigHandler.config.getAsJsonObject(mobId).get("ambient").getAsString() : null;
        if (soundId != null && SoundRegistry.SOUND_EVENTS.containsKey(soundId)) {
            cir.setReturnValue(SoundRegistry.SOUND_EVENTS.get(soundId));
        }
    }
}