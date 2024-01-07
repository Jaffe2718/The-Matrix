package me.jaffe2718.the_matrix.client.gui.screen;

import me.jaffe2718.the_matrix.client.gui.screen.listener.Game2048ScreenListener;
import me.jaffe2718.the_matrix.client.gui.screen.slot.ButtonSlot;
import me.jaffe2718.the_matrix.client.gui.screen.slot.MatrixSlot;
import me.jaffe2718.the_matrix.client.gui.screen.slot.RewardSlot;
import me.jaffe2718.the_matrix.element.block.entity.LaptopBlockEntity;
import me.jaffe2718.the_matrix.unit.ItemRegistry;
import me.jaffe2718.the_matrix.unit.ScreenRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

/**
 * This class is the screen handler of the game 2048.
 * There are 5 kinds of slots:
 * <ul>
 *     <li>
 *         <b>Player inventory slots: 0~26</b><br>
 *         <p>These slots are used to store the player's items.</p>
 *     </li>
 *     <li>
 *         <b>Player hotbar slots: 27~35</b>
 *         <p>These slots are used to store the player's items in the hotbar.</p>
 *     </li>
 *     <li>
 *         <b>Reward slot: 36</b>
 *         <p>This slot is used to store the reward coins.</p>
 *     </li>
 *     <li>
 *         <b>Matrix slots: 37~52</b>
 *         <p>These slots are used to store the matrix of the game 2048, the number of each slot is the power of 2.</p>
 *     </li>
 *     <li>
 *         <b>Button slots: 53~57</b>
 *         <p>These slots are used to be the buttons of the game 2048.</p>
 *         <p>53: up button</p>
 *         <p>54: down button</p>
 *         <p>55: left button</p>
 *         <p>56: right button</p>
 *         <p>57: reset button</p>
 *     </li>
 * </ul>
 * */
public class Game2048ScreenHandler extends ScreenHandler {

    public LaptopBlockEntity laptopBlockEntity = null;

    /**
     * Default constructor to call in the server.
     * @see me.jaffe2718.the_matrix.element.block.entity.LaptopBlockEntity#createMenu(int, PlayerInventory, PlayerEntity) 
     * */
    public Game2048ScreenHandler(int syncId, @NotNull PlayerInventory playerInventory, @NotNull LaptopBlockEntity laptopBlockEntity) {
        super(ScreenRegistry.GAME_2048_SCREEN_HANDLER, syncId);
        this.laptopBlockEntity = laptopBlockEntity;
        for(int i = 0; i < 3; ++i) {     // 0~26
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for(int i = 0; i < 9; ++i) {    // 27~35
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.addSlot(new RewardSlot(this, 148, 35));    // reward slot: 36
        for (int i = 0; i < 16; i++) {                                // matrix slots: 37~52
            this.addSlot(new MatrixSlot(this.laptopBlockEntity.matrixInventory, i));
        }
        this.addSlot(new ButtonSlot(() -> {
            this.laptopBlockEntity.getMatrix2048().up2048();
            this.updateInventories();
        }, 94, 16));   // up button: 53
        this.addSlot(new ButtonSlot(() -> {
            this.laptopBlockEntity.getMatrix2048().down2048();
            this.updateInventories();
        }, 94, 56));   // down button: 54
        this.addSlot(new ButtonSlot(() -> {
            this.laptopBlockEntity.getMatrix2048().left2048();
            this.updateInventories();
        }, 74, 36));   // left button: 55
        this.addSlot(new ButtonSlot(() -> {
            this.laptopBlockEntity.getMatrix2048().right2048();
            this.updateInventories();
        }, 114, 36));  // right button: 56
        this.addSlot(new ButtonSlot(() -> {
            this.laptopBlockEntity.getMatrix2048().reset();
            this.updateInventories();
        }, 94, 36));  // reset button: 57
        this.addListener(new Game2048ScreenListener());
    }
    
    /**
     * Default constructor to call in the client.
     * @see ScreenRegistry#register() 
     * */
    public Game2048ScreenHandler(int syncId, @NotNull PlayerInventory playerInventory) {
        super(ScreenRegistry.GAME_2048_SCREEN_HANDLER, syncId);
        for(int i = 0; i < 3; ++i) {     // 0~26
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for(int i = 0; i < 9; ++i) {    // 27~35
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
        this.addSlot(new Slot(new SimpleInventory(1), 0,148, 35));    // reward slot: 36
        Inventory matrixInventory = new SimpleInventory(16);
        for (int i = 0; i < 16; i++) {                               // matrix slots: 37~52
            this.addSlot(new MatrixSlot(matrixInventory, i));
        }
        this.addSlot(new ButtonSlot(null, 94, 16));   // up button: 53
        this.addSlot(new ButtonSlot(null, 94, 56));   // down button: 54
        this.addSlot(new ButtonSlot(null, 74, 36));   // left button: 55
        this.addSlot(new ButtonSlot(null, 114, 36));  // right button: 56
        this.addSlot(new ButtonSlot(null, 94, 36));  // reset button: 57
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);
        Slot slot = this.slots.get(slotIndex);
        if (slot instanceof ButtonSlot buttonSlot) {
            buttonSlot.runnable.run();
        }
    }

    @Override
    public void onClosed(PlayerEntity player) {
        if (this.laptopBlockEntity != null) {
            int[] oneDimensionalArray = new int[16];
            for (int i = 0; i < 16; i++) {
                ItemStack stack = this.laptopBlockEntity.matrixInventory.getStack(i);
                int count = stack.getCount();
                oneDimensionalArray[i] = stack.isOf(ItemRegistry.COIN) ? (int) Math.pow(2, count) : 0;
            }
            this.laptopBlockEntity.getMatrix2048().update(oneDimensionalArray);
            this.laptopBlockEntity.markDirty();
        }
        super.onClosed(player);
    }

    public void updateInventories() {
        if (this.laptopBlockEntity != null) {
            int[] oneDimensionalArray = this.laptopBlockEntity.getMatrix2048().to1DArray();
            for (int i = 0; i < 16; i++) {
                int number = MathHelper.floorLog2(MathHelper.clamp(oneDimensionalArray[i], 1, Integer.MAX_VALUE));
                this.laptopBlockEntity.matrixInventory.setStack(i, number > 0 ? new ItemStack(ItemRegistry.COIN, number) : ItemStack.EMPTY);
            }
            this.laptopBlockEntity.rewardInventory.setStack(0, new ItemStack(ItemRegistry.COIN, this.laptopBlockEntity.getMatrix2048().getScore()));
        }
    }
}
