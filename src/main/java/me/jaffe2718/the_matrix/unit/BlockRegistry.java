package me.jaffe2718.the_matrix.unit;

import me.jaffe2718.the_matrix.TheMatrix;
import me.jaffe2718.the_matrix.element.block.*;
import me.jaffe2718.the_matrix.element.block.entity.EngineCoreBlockEntity;
import me.jaffe2718.the_matrix.element.block.entity.LaptopBlockEntity;
import me.jaffe2718.the_matrix.element.block.entity.TeleporterBlockEntity;
import me.jaffe2718.the_matrix.element.block.entity.VendingMachineBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public abstract class BlockRegistry {
    // Blocks here
    public static final Block DEEPSLATE_PROMETHIUM_ORE = new ExperienceDroppingBlock(
            UniformIntProvider.create(3, 7),
            FabricBlockSettings
                    .create()
                    .collidable(true)
                    .instrument(Instrument.BASEDRUM)
                    .mapColor(MapColor.STONE_GRAY)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)
                    .luminance(7)
                    .strength(4.5F, 3.0F));

    public static final Block ENGINE_CORE = new EngineCoreBlock(FabricBlockSettings
            .create()
            .collidable(true)
            .instrument(Instrument.BELL)
            .requiresTool()
            .strength(50.0F, 1200.0F)
            .luminance(15)
            .sounds(BlockSoundGroup.METAL)
            .suffocates(Blocks::never)
            .nonOpaque());
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

    public static final Block PLASMA_LAMP = new Block(FabricBlockSettings
            .create()
            .collidable(true)
            .instrument(Instrument.PLING)
            .mapColor(MapColor.DIAMOND_BLUE)
            .strength(1.5F)
            .luminance(15)
            .sounds(BlockSoundGroup.GLASS)
            .solidBlock(Blocks::never));

    public static final Block PORTAL_01_BLOCK = new Portal01Block(FabricBlockSettings
            .create()
            .noCollision()
            .instrument(Instrument.PLING)
            .mapColor(MapColor.GREEN)
            .luminance(15)
            .strength(-1.0F, 3600000.0F)
            .sounds(BlockSoundGroup.GLASS)
            .suffocates(Blocks::never));

    public static final Block PROMETHIUM_BLOCK = new Block(FabricBlockSettings
            .create()
            .collidable(true)
            .instrument(Instrument.BELL)
            .mapColor(MapColor.DIAMOND_BLUE)
            .requiresTool()
            .luminance(15)
            .strength(5.0F, 6.0F));

    public static final Block PROMETHIUM_ORE = new ExperienceDroppingBlock(
            UniformIntProvider.create(3, 7),
            FabricBlockSettings
                    .create()
                    .collidable(true)
                    .instrument(Instrument.BASEDRUM)
                    .mapColor(MapColor.STONE_GRAY)
                    .requiresTool()
                    .luminance(7)
                    .strength(3.0F, 3.0F));

    public static final Block RAW_PROMETHIUM_BLOCK = new Block(FabricBlockSettings
            .create()
            .collidable(true)
            .instrument(Instrument.BASEDRUM)
            .mapColor(MapColor.DIAMOND_BLUE)
            .requiresTool()
            .luminance(7)
            .strength(5.0F, 6.0F));

    public static final Block TELEPORTER = new TeleporterBlock(FabricBlockSettings
            .create()
            .collidable(true)
            .instrument(Instrument.BASEDRUM)
            .requiresTool()
            .mapColor(MapColor.DIAMOND_BLUE)
            .strength(50.0F, 1200.0F)
            .luminance(15)
            .sounds(BlockSoundGroup.METAL));

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
            .requiresTool());

    // Block entities here
    public static BlockEntityType<EngineCoreBlockEntity> ENGINE_CORE_BLOCK_ENTITY;
    public static BlockEntityType<LaptopBlockEntity> LAPTOP_BLOCK_ENTITY;
    public static BlockEntityType<TeleporterBlockEntity> TELEPORTER_BLOCK_ENTITY;
    public static BlockEntityType<VendingMachineBlockEntity> VENDING_MACHINE_BLOCK_ENTITY;

    /**
     * Register blocks and block entities
     */
    public static void register() {
        Registry.register(Registries.BLOCK, TheMatrix.id("deepslate_promethium_ore"), DEEPSLATE_PROMETHIUM_ORE);
        Registry.register(Registries.BLOCK, TheMatrix.id("engine_core"), ENGINE_CORE);
        Registry.register(Registries.BLOCK, TheMatrix.id("laptop"), LAPTOP);
        Registry.register(Registries.BLOCK, TheMatrix.id("machine_block"), MACHINE_BLOCK);
        Registry.register(Registries.BLOCK, TheMatrix.id("plasma_lamp"), PLASMA_LAMP);
        Registry.register(Registries.BLOCK, TheMatrix.id("portal_01_block"), PORTAL_01_BLOCK);
        Registry.register(Registries.BLOCK, TheMatrix.id("promethium_block"), PROMETHIUM_BLOCK);
        Registry.register(Registries.BLOCK, TheMatrix.id("promethium_ore"), PROMETHIUM_ORE);
        Registry.register(Registries.BLOCK, TheMatrix.id("raw_promethium_block"), RAW_PROMETHIUM_BLOCK);
        Registry.register(Registries.BLOCK, TheMatrix.id("teleporter"), TELEPORTER);
        Registry.register(Registries.BLOCK, TheMatrix.id("vending_machine"), VENDING_MACHINE);
        ENGINE_CORE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                TheMatrix.id("engine_core"),
                FabricBlockEntityTypeBuilder.create(EngineCoreBlockEntity::new, ENGINE_CORE).build());
        LAPTOP_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                TheMatrix.id("laptop"),
                FabricBlockEntityTypeBuilder.create(LaptopBlockEntity::new, LAPTOP).build());
        TELEPORTER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                TheMatrix.id("teleporter"),
                FabricBlockEntityTypeBuilder.create(TeleporterBlockEntity::new, TELEPORTER).build());
        VENDING_MACHINE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                TheMatrix.id("vending_machine"),
                FabricBlockEntityTypeBuilder.create(VendingMachineBlockEntity::new, VENDING_MACHINE).build());
    }
}
