package me.jaffe2718.the_matrix.mixin.entity;

import me.jaffe2718.the_matrix.unit.Dimensions;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.TeleportTarget;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderPearlEntity.class)
public class EnderPearlEntityMixin {

    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    private void onCollisionHead(net.minecraft.util.hit.@NotNull HitResult hitResult, CallbackInfo ci) {
        if (hitResult instanceof BlockHitResult blockHitResult) {
            EnderPearlEntity self = (EnderPearlEntity) (Object) this;
            if (self.getWorld().getBlockState(blockHitResult.getBlockPos()).getBlock() == Blocks.DRAGON_EGG
                    && Dimensions.getDimensionFlag(self.getEntityWorld().getRegistryKey()) == Dimensions.DimensionFlags.VANILLA) {
                Entity owner = self.getOwner();
                if (owner != null && self.getWorld().getServer() != null) {
                    ServerWorld world = self.getWorld().getServer().getWorld(Dimensions.ZION);
                    FabricDimensions.teleport(owner, world, new TeleportTarget(Dimensions.getZionSpawnPos(world), owner.getVelocity(), owner.getYaw(), owner.getPitch()));
                }
                ci.cancel();
            }
        }
    }
}
