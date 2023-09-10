package de.timmi6790.barrier.mixins.blocks;


import de.timmi6790.barrier.Cache;
import de.timmi6790.barrier.McMod;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBarrier.class)
public abstract class MixinBlockBarrier extends Block {
    @Getter(lazy = true)
    private final Cache cache = McMod.getInstance().getCache();

    public MixinBlockBarrier(Material blockMaterialIn) {
        super(blockMaterialIn);
    }

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void getRenderType(final CallbackInfoReturnable<Integer> cir) {
        if (this.getCache().isVisibleBarrier()) {
            cir.setReturnValue(3);
        }
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return worldIn.getBlockState(pos).getBlock() != this && super.shouldSideBeRendered(worldIn, pos, side);
    }
}
