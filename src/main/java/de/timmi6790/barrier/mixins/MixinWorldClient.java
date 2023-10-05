package de.timmi6790.barrier.mixins;

import de.timmi6790.barrier.Cache;
import de.timmi6790.barrier.McMod;
import lombok.Getter;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldClient.class)
public abstract class MixinWorldClient {
    @Getter(lazy = true)
    private final Cache cache = McMod.getInstance().getCache();

    @Inject(method = "doVoidFogParticles", at = @At("HEAD"), cancellable = true)
    public void getRenderType(CallbackInfo ci) {
        if (this.getCache().isVisibleBarrier()) {
            ci.cancel();
        }
    }
}
