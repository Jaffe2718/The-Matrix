package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.element.block.LaptopBlock;
import me.jaffe2718.the_matrix.element.block.PlasmaEmitterBlock;
import me.jaffe2718.the_matrix.element.block.VendingMachineBlock;
import me.jaffe2718.the_matrix.element.block.entity.LaptopBlockEntity;
import me.jaffe2718.the_matrix.element.block.entity.PlasmaEmitterBlockEntity;
import me.jaffe2718.the_matrix.element.block.entity.VendingMachineBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static me.jaffe2718.the_matrix.TheMatrix.MOD_ID;

public abstract class BlockRegistry {
    // Blocks here
    public static final Block LAPTOP = new LaptopBlock(FabricBlockSettings
            .create()
            .collidable(true)
            .strength(0.5F)
            .luminance(8)
            .suffocates(Blocks::never)
            .nonOpaque());

    public static final Block MACHINE_BLOCK = new Block(FabricBlockSettings
            .create()
            .collidable(true)
            .instrument(Instrument.BASEDRUM)
            .requiresTool()
            .mapColor(MapColor.BLACK)
            .strength(50.0F, 1200.0F)
            .sounds(BlockSoundGroup.NETHERITE));

    public static final Block PLASMA_EMITTER = new PlasmaEmitterBlock(FabricBlockSettings
            .create()
            .collidable(true)
            .instrument(Instrument.BASEDRUM)
            .requiresTool()
            .mapColor(MapColor.DIAMOND_BLUE)
            .strength(50.0F, 1200.0F)
            .luminance(15)
            .sounds(BlockSoundGroup.METAL));

    public static final Block PLASMA_LAMP = new Block(FabricBlockSettings
            .create()
            .collidable(true)
            .instrument(Instrument.PLING)
            .mapColor(MapColor.DIAMOND_BLUE)
            .strength(1.5F)
            .luminance(15)
            .sounds(BlockSoundGroup.GLASS)
            .solidBlock(Blocks::never));

    public static final Block VENDING_MACHINE = new VendingMachineBlock(FabricBlockSettings
            .create()
            .collidable(true)
            .instrument(Instrument.PLING)
            .mapColor(MapColor.DIAMOND_BLUE)
            .strength(3.5F)
            .luminance(15)
            .sounds(BlockSoundGroup.GLASS)
            .suffocates(Blocks::never)
            .nonOpaque()
            .requiresTool()
            .solidBlock(Blocks::never));

    // Block entities here
    public static BlockEntityType<LaptopBlockEntity> LAPTOP_BLOCK_ENTITY;
    public static BlockEntityType<PlasmaEmitterBlockEntity> PLASMA_EMITTER_BLOCK_ENTITY;
    public static BlockEntityType<VendingMachineBlockEntity> VENDING_MACHINE_BLOCK_ENTITY;

    /**
     * Register blocks and block entities
     */
    public static void register() {
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "laptop"), LAPTOP);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "machine_block"), MACHINE_BLOCK);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "plasma_emitter"), PLASMA_EMITTER);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "plasma_lamp"), PLASMA_LAMP);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "vending_machine"), VENDING_MACHINE);
        LAPTOP_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, "laptop"),
                FabricBlockEntityTypeBuilder.create(LaptopBlockEntity::new, LAPTOP).build());
        PLASMA_EMITTER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, "plasma_emitter"),
                FabricBlockEntityTypeBuilder.create(PlasmaEmitterBlockEntity::new, PLASMA_EMITTER).build());
        VENDING_MACHINE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, "vending_machine"),
                FabricBlockEntityTypeBuilder.create(VendingMachineBlockEntity::new, VENDING_MACHINE).build());
    }
}
