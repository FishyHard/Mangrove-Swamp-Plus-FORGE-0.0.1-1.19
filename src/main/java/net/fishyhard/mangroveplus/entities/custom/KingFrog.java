package net.fishyhard.mangroveplus.entities.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class KingFrog extends Animal implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    public KingFrog(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 150.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0f)
                .add(Attributes.ATTACK_SPEED, 0.5f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 0.5D, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.2D, 32.0F));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(2, new JumpGoal() {
            @Override
            public boolean canUse() {
                return false;
            }
        });
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.kingfrog.walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.kingfrog.idle", true));
        return PlayState.CONTINUE;

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.FROG_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.FROG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.FROG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.FROG_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }
}
