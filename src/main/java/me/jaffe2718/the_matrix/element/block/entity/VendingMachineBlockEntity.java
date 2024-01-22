package me.jaffe2718.the_matrix.element.block.entity;

import me.jaffe2718.the_matrix.unit.BlockRegistry;
import me.jaffe2718.the_matrix.unit.SoundEventRegistry;
import me.jaffe2718.the_matrix.unit.TradeOfferListFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import org.jetbrains.annotations.Nullable;

public class VendingMachineBlockEntity
        extends BlockEntity
        implements Merchant {

    // private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Nullable
    private PlayerEntity customer;
    @Nullable
    protected TradeOfferList offers;

    public VendingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.VENDING_MACHINE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity customer) {
        this.customer = customer;
    }

    @Nullable
    @Override
    public PlayerEntity getCustomer() {
        return this.customer;
    }

    @Override
    public TradeOfferList getOffers() {
        return TradeOfferListFactory.createVendingMachineTradeOfferList();
    }

    @Override
    public void setOffersFromServer(TradeOfferList offers) {
        this.offers = offers;
    }

    @Override
    public void trade(TradeOffer offer) {
        if (this.world != null && !this.world.isClient)
            this.world.playSound(null, this.pos, this.getYesSound(), SoundCategory.RECORDS, 1.0F, 1.0F);
    }

    @Override
    public void onSellingItem(ItemStack stack) {
        if (this.world != null && !this.world.isClient)
            this.world.playSound(null, this.pos, SoundEventRegistry.VENDING_MACHINE_SWITCHING_OPTIONS, SoundCategory.RECORDS, 1.0F, 1.0F);
    }

    @Override
    public int getExperience() {
        return 1;
    }

    @Override
    public void setExperienceFromServer(int experience) {}

    @Override
    public boolean isLeveledMerchant() {
        return false;
    }

    @Override
    public SoundEvent getYesSound() {
        return SoundEventRegistry.VENDING_MACHINE_GOODS_POPPING;
    }

    @Override
    public boolean isClient() {
        return this.world == null || this.world.isClient;
    }

    //
//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
//    }
//
//    @Override
//    public AnimatableInstanceCache getAnimatableInstanceCache() {
//        return this.cache;
//    }
}
