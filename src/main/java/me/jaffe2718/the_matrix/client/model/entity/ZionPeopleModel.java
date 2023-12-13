package me.jaffe2718.the_matrix.client.model.entity;

import me.jaffe2718.the_matrix.element.entity.mob.ZionPeopleEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

public class ZionPeopleModel extends GeoModel<ZionPeopleEntity> {

    /**
     * For apu_pilot, when driving Armored Personnel Unit (APU)
     * */
    public static final RawAnimation APU_PILOT_DRIVE = RawAnimation.begin().then("animation.zion_people.apu_pilot.drive", Animation.LoopType.DEFAULT);

    /**
     * For infantry, walk or stand still with weapon on hand
     * */
    public static final RawAnimation INFANTRY_COMBAT = RawAnimation.begin().then("animation.zion_people.infantry.combat", Animation.LoopType.DEFAULT);

    /**
     * For infantry, walk or stand still
     * */
    public static final RawAnimation INFANTRY_IDLE = RawAnimation.begin().then("animation.zion_people.infantry.idle", Animation.LoopType.DEFAULT);

    /**
     * For api_pilot, carpenter, farmer, farm_breeder, grocer, machinist, rifleman, walk or stand still
     * */
    public static final RawAnimation COMMON = RawAnimation.begin().then("animation.zion_people.common", Animation.LoopType.DEFAULT);

    /**
     * For machinist, fix
     * */
    public static final RawAnimation MACHINIST_FIX = RawAnimation.begin().then("animation.zion_people.machinist.fix", Animation.LoopType.DEFAULT);

    /**
     * For miner, walk or stand still
     * */
    public static final RawAnimation MINER_COMMON = RawAnimation.begin().then("animation.zion_people.miner.common", Animation.LoopType.DEFAULT);

    /**
     * For rifleman, shoot
     * */
    public static final RawAnimation RIFLEMAN_SHOOT = RawAnimation.begin().then("animation.zion_people.rifleman.shoot", Animation.LoopType.DEFAULT);

    private static final String HEAD_PITCH = "variable.head_pitch";  // register head_pitch variable to molang parser
    private static final String IS_WALKING = "variable.is_walking";

    @Override
    public Identifier getModelResource(@NotNull ZionPeopleEntity animatable) {    // TODO: add model support
        return new Identifier("the_matrix", "geo/entity/zion_people/" + animatable.getJobName() + ".geo.json");
    }

    @Override
    public Identifier getTextureResource(@NotNull ZionPeopleEntity animatable) {  // TODO: add texture support
        return new Identifier("the_matrix", "textures/entity/zion_people/" + animatable.getJobName() + ".png");
    }

    @Override
    public Identifier getAnimationResource(@NotNull ZionPeopleEntity animatable) {  // TODO: add animation support
        return new Identifier("the_matrix", "animations/entity/zion_people/" + animatable.getJobName() + ".animation.json");
    }
    @Override
    public void applyMolangQueries(ZionPeopleEntity animatable, double animTime) {
        super.applyMolangQueries(animatable, animTime);
        MolangParser parser = MolangParser.INSTANCE;
        parser.setMemoizedValue(HEAD_PITCH, animatable::getPitch);
        parser.setMemoizedValue(IS_WALKING, () -> {      // register is_walking variable to molang parser
            var velocity = animatable.getVelocity();
            double hSpeed = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getZ() * velocity.getZ());
            return hSpeed > 5E-3 ? 1.0 : 0.0;
        });
    }
}
