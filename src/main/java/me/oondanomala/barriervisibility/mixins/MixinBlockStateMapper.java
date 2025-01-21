package me.oondanomala.barriervisibility.mixins;

import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(BlockStateMapper.class)
public abstract class MixinBlockStateMapper {
    @Redirect(method = "putAllStateModelLocations", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
    public boolean getRenderType(Set<?> instance, Object o) {
        return o != Blocks.barrier && instance.contains(o);
    }
}
