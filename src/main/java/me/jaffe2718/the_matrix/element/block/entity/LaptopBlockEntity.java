package me.jaffe2718.the_matrix.element.block.entity;

import me.jaffe2718.the_matrix.unit.BlockRegistry;
import me.jaffe2718.the_matrix.unit.ItemRegistry;
import me.jaffe2718.the_matrix.unit.MathUnit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LaptopBlockEntity extends BlockEntity implements GeoBlockEntity, SingleStackInventory {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private ItemStack coinStack = ItemStack.EMPTY;
    private MathUnit.Matrix4i matrix2048;

    public LaptopBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.LAPTOP_BLOCK_ENTITY, pos, state);
        if (this.matrix2048 == null) {
            this.matrix2048 = new MathUnit.Matrix4i();
            this.coinStack = ItemStack.EMPTY;
        }
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        int[] oneDimensionalArray = nbt.getIntArray("Matrix2048");
        if (oneDimensionalArray.length == 16) {
            this.matrix2048 = new MathUnit.Matrix4i(oneDimensionalArray);
            this.coinStack = new ItemStack(ItemRegistry.COIN, this.matrix2048.getScore());
        }
    }

    public void writeNbt(@NotNull NbtCompound nbt) {
        nbt.putIntArray("Matrix2048", this.matrix2048.to1DArray());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // TODO: Register the animation controllers
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.coinStack;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        this.coinStack.decrement(amount);
        return this.coinStack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.coinStack = stack;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}
