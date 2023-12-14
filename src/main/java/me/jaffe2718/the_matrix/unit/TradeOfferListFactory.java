package me.jaffe2718.the_matrix.unit;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static me.jaffe2718.the_matrix.unit.ItemRegistry.COIN;

public abstract class TradeOfferListFactory {

    public static @NotNull TradeOfferList createZionPeopleTradeOfferList(int jobID) {
        TradeOfferList tradeOfferList = new TradeOfferList();
        switch (jobID) {
            case 1 -> {    // APU Pilot
            }
            case 2 -> {    // Carpenter
                tradeOfferList.add(itemForCoin(1, Items.OAK_LOG));
                tradeOfferList.add(itemForCoin(1, Items.SPRUCE_LOG));
                tradeOfferList.add(itemForCoin(1, Items.BIRCH_LOG));
                tradeOfferList.add(itemForCoin(1, Items.JUNGLE_LOG));
                tradeOfferList.add(itemForCoin(1, Items.ACACIA_LOG));
                tradeOfferList.add(itemForCoin(1, Items.DARK_OAK_LOG));
                tradeOfferList.add(itemForCoin(1, Items.MANGROVE_LOG));
                tradeOfferList.add(itemForCoin(1, Items.CHERRY_LOG));
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
            }
            case 6 -> {    // Infantry
                tradeOfferList.add(new TradeOffer(costCoin(64), ItemRegistry.MACHINE_PART.getDefaultStack().copyWithCount(8), ItemRegistry.ELECTROMAGNETIC_GUN.getDefaultStack(), Integer.MAX_VALUE, 1, 1F));
                tradeOfferList.add(coinForItem(5, ItemRegistry.BATTERY));
            }
            case 7 -> {    // Machinist
                tradeOfferList.add(coinForItem(64, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE));
                tradeOfferList.add(itemForCoin(25, ItemRegistry.MACHINE_PART));
                tradeOfferList.add(itemForCoin(64, ItemRegistry.CPU));
            }
            case 8 -> {    // Miner
                tradeOfferList.add(coinForItem(3, Items.COAL));
                tradeOfferList.add(coinForItem(20, Items.IRON_INGOT));
                tradeOfferList.add(coinForItem(30, Items.GOLD_INGOT));
                tradeOfferList.add(coinForItem(50, Items.DIAMOND));
                tradeOfferList.add(new TradeOffer(costCoin(64), Items.GOLD_INGOT.getDefaultStack(), Items.NETHERITE_INGOT.getDefaultStack(), Integer.MAX_VALUE, 1, 1F));
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
            }
            case 9 -> {    // Rifleman
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
