package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.element.block.LaptopBlock;
import me.jaffe2718.the_matrix.element.block.entity.LaptopBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public abstract class BlockRegistry {
    // Blocks here
    public static final Block LAPTOP = new LaptopBlock(FabricBlockSettings
            .create()
            .collidable(true)
            .strength(0.5F)
            .luminance(8)
            .nonOpaque());

    // Block entities here
    public static BlockEntityType<LaptopBlockEntity> LAPTOP_BLOCK_ENTITY;


    /**
     * Register blocks and block entities
     */
    public static void register() {
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "laptop"), LAPTOP);
        LAPTOP_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, "laptop"),
                FabricBlockEntityTypeBuilder.create(LaptopBlockEntity::new, LAPTOP).build());
    }
}
