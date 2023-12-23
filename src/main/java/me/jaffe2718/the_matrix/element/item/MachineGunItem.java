package me.jaffe2718.the_matrix.element.item;

import me.jaffe2718.the_matrix.client.render.item.MachineGunRenderer;
import me.jaffe2718.the_matrix.element.entity.vehicle.MachineGunEntity;
import me.jaffe2718.the_matrix.unit.EntityRegistry;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class MachineGunItem extends Item implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public MachineGunItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(@NotNull ItemUsageContext context) {
        Direction direction = context.getSide();
        if (direction == Direction.DOWN) {
            return ActionResult.FAIL;
        }
        World world = context.getWorld();
        ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
        BlockPos blockPos = itemPlacementContext.getBlockPos();
        ItemStack itemStack = context.getStack();
        Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
        Box box = EntityRegistry.MACHINE_GUN.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
        if (!world.isSpaceEmpty(null, box) || !world.getOtherEntities(null, box).isEmpty()) {
            return ActionResult.FAIL;
        }
        if (world instanceof ServerWorld serverWorld) {
            Consumer<MachineGunEntity> consumer = EntityType.copier(serverWorld, itemStack, context.getPlayer());
            MachineGunEntity machineGunEntity = EntityRegistry.MACHINE_GUN.create(serverWorld, itemStack.getNbt(), consumer, blockPos, SpawnReason.SPAWN_EGG, true, true);
            if (machineGunEntity == null) {
                return ActionResult.FAIL;
            }
            serverWorld.spawnEntity(machineGunEntity);
            machineGunEntity.emitGameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
        }
        itemStack.decrement(1);
        return ActionResult.success(world.isClient);
    }

    @Override
    public void createRenderer(@NotNull Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final MachineGunRenderer renderer = new MachineGunRenderer();
            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.@NotNull ControllerRegistrar controllers) {
        // controllers.add(new AnimationController<>(this, "controller", state -> state.setAndContinue(COMMON)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
