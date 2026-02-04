package me.oondanomala.barriervisibility.mixins;

import me.oondanomala.barriervisibility.BarrierVisibility;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBarrier.class)
public abstract class MixinBlockBarrier extends Block {
    public MixinBlockBarrier(Material blockMaterialIn) {
        super(blockMaterialIn);
    }

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void getRenderType(CallbackInfoReturnable<Integer> cir) {
        if (BarrierVisibility.enabled) {
            cir.setReturnValue(3);
        }
    }

    public boolean isOpaqueCube() {
        return BarrierVisibility.enabled;
    }
}
