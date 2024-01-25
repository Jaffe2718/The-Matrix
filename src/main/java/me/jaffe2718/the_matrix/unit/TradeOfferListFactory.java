package me.jaffe2718.the_matrix.unit;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static me.jaffe2718.the_matrix.unit.ItemRegistry.COIN;

public interface TradeOfferListFactory {

    static @NotNull TradeOfferList createVendingMachineTradeOfferList() {
        TradeOfferList tradeOfferList = new TradeOfferList();
        tradeOfferList.add(coinForItem(1, Items.APPLE));
        tradeOfferList.add(coinForItem(2, Items.BAKED_POTATO));
        tradeOfferList.add(coinForItem(1, Items.BEETROOT));
        tradeOfferList.add(coinForItem(4, Items.COOKED_BEEF));
        tradeOfferList.add(coinForItem(3, Items.COOKED_CHICKEN));
        tradeOfferList.add(coinForItem(2, Items.COOKED_COD));
        tradeOfferList.add(coinForItem(3, Items.COOKED_MUTTON));
        tradeOfferList.add(coinForItem(4, Items.COOKED_PORKCHOP));
        tradeOfferList.add(coinForItem(3, Items.COOKED_RABBIT));
        tradeOfferList.add(coinForItem(2, Items.COOKED_SALMON));
        tradeOfferList.add(coinForItem(2, Items.COOKIE));
        tradeOfferList.add(coinForItem(2, Items.PUMPKIN_PIE));
        tradeOfferList.add(coinForItem(64, Items.ENCHANTED_GOLDEN_APPLE));
        return tradeOfferList;
    }

    static @NotNull TradeOfferList createZionPeopleTradeOfferList(int jobID) {
        TradeOfferList tradeOfferList = new TradeOfferList();
        switch (jobID) {
            case 1 -> {    // APU Pilot
                tradeOfferList.add(coinForItem(1, ItemRegistry.BULLET));
                tradeOfferList.add(coinForItem(8, ItemRegistry.BOXED_BULLETS));
                tradeOfferList.add(coinForItem(25, ItemRegistry.MACHINE_PART));
            }
            case 2 -> {    // Carpenter
                // buy
                tradeOfferList.add(itemForCoin(1, Items.OAK_LOG));
                tradeOfferList.add(itemForCoin(1, Items.SPRUCE_LOG));
                tradeOfferList.add(itemForCoin(1, Items.BIRCH_LOG));
                tradeOfferList.add(itemForCoin(1, Items.JUNGLE_LOG));
                tradeOfferList.add(itemForCoin(1, Items.ACACIA_LOG));
                tradeOfferList.add(itemForCoin(1, Items.DARK_OAK_LOG));
                tradeOfferList.add(itemForCoin(1, Items.MANGROVE_LOG));
                tradeOfferList.add(itemForCoin(1, Items.CHERRY_LOG));
                // sell
                tradeOfferList.add(coinForItem(1, Items.OAK_LOG));
                tradeOfferList.add(coinForItem(1, Items.SPRUCE_LOG));
                tradeOfferList.add(coinForItem(1, Items.BIRCH_LOG));
                tradeOfferList.add(coinForItem(1, Items.JUNGLE_LOG));
                tradeOfferList.add(coinForItem(1, Items.ACACIA_LOG));
                tradeOfferList.add(coinForItem(1, Items.DARK_OAK_LOG));
                tradeOfferList.add(coinForItem(1, Items.MANGROVE_LOG));
                tradeOfferList.add(coinForItem(1, Items.CHERRY_LOG));
                tradeOfferList.add(coinForItem(2, Items.CRIMSON_STEM));
                tradeOfferList.add(coinForItem(2, Items.WARPED_STEM));
                tradeOfferList.add(coinForItem(2, Items.BAMBOO));
                tradeOfferList.add(coinForItem(64, Items.BEEHIVE));

            }
            case 3 -> {    // Farm Breeder
                tradeOfferList.add(new TradeOffer(costCoin(10), Items.BUCKET.getDefaultStack(), Items.MILK_BUCKET.getDefaultStack(), Integer.MAX_VALUE, 1, 1F));
                tradeOfferList.add(coinForItem(20, Items.CHICKEN_SPAWN_EGG));
                tradeOfferList.add(coinForItem(25, Items.BEE_SPAWN_EGG));
                tradeOfferList.add(coinForItem(30, Items.PIG_SPAWN_EGG));
                tradeOfferList.add(coinForItem(30, Items.SHEEP_SPAWN_EGG));
                tradeOfferList.add(coinForItem(30, Items.RABBIT_SPAWN_EGG));
                tradeOfferList.add(coinForItem(35, Items.COW_SPAWN_EGG));
                tradeOfferList.add(coinForItem(56, Items.HORSE_SPAWN_EGG));
                tradeOfferList.add(coinForItem(48, Items.DONKEY_SPAWN_EGG));
                tradeOfferList.add(coinForItem(48, Items.OCELOT_SPAWN_EGG));
                tradeOfferList.add(coinForItem(56, Items.WOLF_SPAWN_EGG));
                tradeOfferList.add(coinForItem(56, Items.LLAMA_SPAWN_EGG));
                tradeOfferList.add(coinForItem(56, Items.FOX_SPAWN_EGG));
                tradeOfferList.add(coinForItem(56, Items.PARROT_SPAWN_EGG));
                tradeOfferList.add(coinForItem(64, Items.PANDA_SPAWN_EGG));
                tradeOfferList.add(coinForItem(64, Items.AXOLOTL_SPAWN_EGG));
                tradeOfferList.add(itemForCoin(1, Items.CARROT));
                tradeOfferList.add(itemForCoin(1, Items.WHEAT));
                tradeOfferList.add(itemForCoin(1, Items.EGG));
                tradeOfferList.add(itemForCoin(1, Items.COD));
                tradeOfferList.add(itemForCoin(1, Items.SALMON));
                tradeOfferList.add(itemForCoin(1, Items.TROPICAL_FISH));
                tradeOfferList.add(itemForCoin(2, Items.PUFFERFISH));
            }
            case 4 -> {    // Farmer
                tradeOfferList.add(coinForItem(1, Items.WHEAT_SEEDS));
                tradeOfferList.add(coinForItem(1, Items.BEETROOT_SEEDS));
                tradeOfferList.add(coinForItem(2, Items.POTATO));
                tradeOfferList.add(coinForItem(2, Items.CARROT));
                tradeOfferList.add(coinForItem(1, Items.SWEET_BERRIES));
                tradeOfferList.add(coinForItem(2, Items.COCOA_BEANS));
                tradeOfferList.add(coinForItem(2, Items.SUGAR_CANE));
                tradeOfferList.add(coinForItem(4, Items.PUMPKIN_SEEDS));
                tradeOfferList.add(coinForItem(4, Items.MELON_SEEDS));
                tradeOfferList.add(coinForItem(4, Items.OAK_SAPLING));
                tradeOfferList.add(coinForItem(4, Items.SPRUCE_SAPLING));
                tradeOfferList.add(coinForItem(4, Items.BIRCH_SAPLING));
                tradeOfferList.add(coinForItem(4, Items.JUNGLE_SAPLING));
                tradeOfferList.add(coinForItem(4, Items.ACACIA_SAPLING));
                tradeOfferList.add(coinForItem(4, Items.DARK_OAK_SAPLING));
                tradeOfferList.add(coinForItem(4, Items.MANGROVE_PROPAGULE));
                tradeOfferList.add(coinForItem(4, Items.CHERRY_SAPLING));
                tradeOfferList.add(itemForCoin(15, Items.MELON));
                tradeOfferList.add(itemForCoin(6, Items.PUMPKIN));
                tradeOfferList.add(itemForCoin(8, Items.POISONOUS_POTATO));
                tradeOfferList.add(itemForCoin(1, Items.BEETROOT));
                tradeOfferList.add(itemForCoin(2, Items.BAKED_POTATO));
            }
            case 5 -> {    // Grocer
                tradeOfferList.add(coinForItem(1, Items.APPLE));
                tradeOfferList.add(coinForItem(1, Items.BONE));
                tradeOfferList.add(coinForItem(1, Items.LEATHER));
                tradeOfferList.add(coinForItem(1, Items.STRING));
                tradeOfferList.add(coinForItem(1, Items.SLIME_BALL));
                tradeOfferList.add(coinForItem(1, Items.SNOWBALL));
                tradeOfferList.add(coinForItem(1, Items.ENDER_PEARL));
                tradeOfferList.add(coinForItem(1, Items.WHITE_DYE));
                tradeOfferList.add(coinForItem(1, Items.LIGHT_GRAY_DYE));
                tradeOfferList.add(coinForItem(1, Items.GRAY_DYE));
                tradeOfferList.add(coinForItem(1, Items.BLACK_DYE));
                tradeOfferList.add(coinForItem(1, Items.RED_DYE));
                tradeOfferList.add(coinForItem(1, Items.ORANGE_DYE));
                tradeOfferList.add(coinForItem(1, Items.YELLOW_DYE));
                tradeOfferList.add(coinForItem(1, Items.LIME_DYE));
                tradeOfferList.add(coinForItem(1, Items.GREEN_DYE));
                tradeOfferList.add(coinForItem(1, Items.CYAN_DYE));
                tradeOfferList.add(coinForItem(1, Items.LIGHT_BLUE_DYE));
                tradeOfferList.add(coinForItem(1, Items.BLUE_DYE));
                tradeOfferList.add(coinForItem(1, Items.PURPLE_DYE));
                tradeOfferList.add(coinForItem(1, Items.MAGENTA_DYE));
                tradeOfferList.add(coinForItem(1, Items.PINK_DYE));
                tradeOfferList.add(coinForItem(1, Items.GUNPOWDER));
            }
            case 6 -> {    // Infantry
                tradeOfferList.add(new TradeOffer(costCoin(64), ItemRegistry.MACHINE_PART.getDefaultStack().copyWithCount(8), ItemRegistry.ELECTROMAGNETIC_GUN.getDefaultStack(), Integer.MAX_VALUE, 1, 1F));
                tradeOfferList.add(coinForItem(25, ItemRegistry.MACHINE_PART));
            }
            case 7 -> {    // Machinist
                tradeOfferList.add(coinForItem(64, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE));
                tradeOfferList.add(itemForCoin(64, ItemRegistry.PROMETHIUM_INGOT));
                tradeOfferList.add(itemForCoin(25, ItemRegistry.MACHINE_PART));
                tradeOfferList.add(itemForCoin(64, ItemRegistry.CPU));
            }
            case 8 -> {    // Miner
                // sell
                tradeOfferList.add(coinForItem(3, Items.COAL));
                tradeOfferList.add(coinForItem(20, Items.IRON_INGOT));
                tradeOfferList.add(coinForItem(30, Items.GOLD_INGOT));
                tradeOfferList.add(coinForItem(50, Items.DIAMOND));
                tradeOfferList.add(new TradeOffer(costCoin(64), Items.GOLD_INGOT.getDefaultStack().copyWithCount(3), Items.NETHERITE_INGOT.getDefaultStack(), Integer.MAX_VALUE, 1, 1F));
                tradeOfferList.add(coinForItem(40, Items.EMERALD));
                tradeOfferList.add(new TradeOffer(costCoin(10), Items.BUCKET.getDefaultStack(), Items.LAVA_BUCKET.getDefaultStack(), Integer.MAX_VALUE, 1, 1F));
                tradeOfferList.add(new TradeOffer(costCoin(5), Items.BUCKET.getDefaultStack(), Items.WATER_BUCKET.getDefaultStack(), Integer.MAX_VALUE, 1, 1F));
                tradeOfferList.add(coinForItem(40, Items.AMETHYST_SHARD));
                tradeOfferList.add(coinForItem(10, Items.REDSTONE));
                tradeOfferList.add(coinForItem(10, Items.LAPIS_LAZULI));
                tradeOfferList.add(coinForItem(3, Items.COPPER_INGOT));
                tradeOfferList.add(coinForItem(3, Items.QUARTZ));
                tradeOfferList.add(coinForItem(3, Items.GLOWSTONE_DUST));
                tradeOfferList.add(coinForItem(3, Items.FLINT));
                tradeOfferList.add(coinForItem(3, Items.POINTED_DRIPSTONE));
                tradeOfferList.add(coinForItem(2, Items.CLAY_BALL));
                tradeOfferList.add(coinForItem(1, Items.GRANITE));
                tradeOfferList.add(coinForItem(1, Items.STONE));
                tradeOfferList.add(coinForItem(1, Items.COBBLESTONE));
                tradeOfferList.add(coinForItem(1, Items.DIRT));
                tradeOfferList.add(coinForItem(1, Items.SAND));
                tradeOfferList.add(coinForItem(1, Items.RED_SAND));
                tradeOfferList.add(coinForItem(1, Items.GRAVEL));
                tradeOfferList.add(coinForItem(1, Items.MAGMA_BLOCK));
                // purchase
                tradeOfferList.add(itemForCoin(3, Items.COAL));
                tradeOfferList.add(itemForCoin(20, Items.IRON_INGOT));
                tradeOfferList.add(itemForCoin(30, Items.GOLD_INGOT));
                tradeOfferList.add(itemForCoin(50, Items.DIAMOND));
                tradeOfferList.add(itemForCoin(40, Items.EMERALD));
                tradeOfferList.add(itemForCoin(40, Items.AMETHYST_SHARD));
                tradeOfferList.add(itemForCoin(10, Items.REDSTONE));
                tradeOfferList.add(itemForCoin(10, Items.LAPIS_LAZULI));
                tradeOfferList.add(itemForCoin(3, Items.COPPER_INGOT));
                tradeOfferList.add(itemForCoin(3, Items.FLINT));
                tradeOfferList.add(itemForCoin(3, Items.POINTED_DRIPSTONE));
                tradeOfferList.add(itemForCoin(2, Items.CLAY_BALL));
                tradeOfferList.add(itemForCoin(64, ItemRegistry.PROMETHIUM_INGOT));
            }
            case 9 -> {    // Rifleman
                tradeOfferList.add(coinForItem(1, ItemRegistry.BULLET));
                tradeOfferList.add(coinForItem(8, ItemRegistry.BOXED_BULLETS));
                tradeOfferList.add(new TradeOffer(costCoin(64), ItemRegistry.MACHINE_PART.getDefaultStack().copyWithCount(32), ItemRegistry.MACHINE_GUN.getDefaultStack(), Integer.MAX_VALUE, 1, 1F));
            }
        }
        return tradeOfferList;
    }

    @Contract("_, _ -> new")
    private static @NotNull TradeOffer coinForItem(int price, @NotNull Item good) {
        return new TradeOffer(costCoin(price), good.getDefaultStack(), Integer.MAX_VALUE, 1, 1F);
    }

    private static @NotNull TradeOffer itemForCoin(int gain, @NotNull Item good) {
        return new TradeOffer(good.getDefaultStack(), costCoin(gain), Integer.MAX_VALUE, 1, 1F);
    }

    private static @NotNull ItemStack costCoin(int amount) {
        return COIN.getDefaultStack().copyWithCount(amount);
    }

}
