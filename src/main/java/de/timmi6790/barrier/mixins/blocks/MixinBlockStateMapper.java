package de.timmi6790.barrier.mixins.blocks;


import de.timmi6790.barrier.Cache;
import de.timmi6790.barrier.McMod;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(BlockStateMapper.class)
public abstract class MixinBlockStateMapper {
    @Getter(lazy = true)
    private final Cache cache = McMod.getInstance().getCache();

    @Redirect(method = "putAllStateModelLocations", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
    public boolean getRenderType(Set instance, Object o) {
        return o != Blocks.barrier && instance.contains(o);
    }
}
