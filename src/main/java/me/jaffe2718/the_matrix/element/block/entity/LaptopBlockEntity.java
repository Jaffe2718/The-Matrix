package me.jaffe2718.the_matrix.element.block.entity;

import me.jaffe2718.the_matrix.unit.gui.screen.Game2048ScreenHandler;
import me.jaffe2718.the_matrix.unit.BlockRegistry;
import me.jaffe2718.the_matrix.unit.ItemRegistry;
import me.jaffe2718.the_matrix.unit.MathUnit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LaptopBlockEntity extends BlockEntity implements GeoBlockEntity, NamedScreenHandlerFactory {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private MathUnit.Matrix4i matrix2048;

    public SimpleInventory matrixInventory;
    public SimpleInventory rewardInventory;

    public LaptopBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.LAPTOP_BLOCK_ENTITY, pos, state);
        this.matrixInventory = new SimpleInventory(16);
        this.rewardInventory = new SimpleInventory(1);
        // Initialize matrix2048
        this.matrix2048 = new MathUnit.Matrix4i();
        for (int i = 0; i < 16; i++) {
            int number = MathHelper.floorLog2(MathHelper.clamp(this.matrix2048.matrix[i / 4][i % 4], 1, Integer.MAX_VALUE));
            this.matrixInventory.setStack(i, number > 0 ? new ItemStack(ItemRegistry.COIN, number) : ItemStack.EMPTY);
        }
        this.rewardInventory.setStack(0, new ItemStack(ItemRegistry.COIN, this.matrix2048.getScore()));
    }

    /**
     * Restore from a 4x4 matrix of integers.
     * The stored matrix will override the initial matrix {@link #matrix2048} in the constructor {@link #LaptopBlockEntity(BlockPos, BlockState)}.
     * When the player first places the laptop block {@link BlockRegistry#LAPTOP}, this method will not be called,
     * instead, the constructor {@link #LaptopBlockEntity(BlockPos, BlockState)} will initialize the matrix.
     * */
    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        int[] oneDimensionalArray = nbt.getIntArray("Matrix2048");
        if (oneDimensionalArray.length == 16) {
            this.matrix2048 = new MathUnit.Matrix4i(oneDimensionalArray);
        } else {
            this.matrix2048 = new MathUnit.Matrix4i();
        }
        if (this.matrixInventory != null && this.rewardInventory != null) {
            for (int i = 0; i < 16; i++) {
                int number = MathHelper.floorLog2(MathHelper.clamp(this.matrix2048.matrix[i / 4][i % 4], 1, Integer.MAX_VALUE));
                this.matrixInventory.setStack(i, number > 0 ? new ItemStack(ItemRegistry.COIN, number) : ItemStack.EMPTY);
            }
            this.rewardInventory.setStack(0, new ItemStack(ItemRegistry.COIN, this.matrix2048.getScore()));
        }
    }

    public MathUnit.Matrix4i getMatrix2048() {
        return this.matrix2048;
    }

    public void writeNbt(@NotNull NbtCompound nbt) {
        nbt.putIntArray("Matrix2048", this.matrix2048.to1DArray());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.the_matrix.laptop");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new Game2048ScreenHandler(syncId, playerInventory, this);
    }

}
