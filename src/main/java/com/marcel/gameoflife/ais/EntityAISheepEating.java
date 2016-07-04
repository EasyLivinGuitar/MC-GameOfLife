package com.marcel.gameoflife.ais;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by kipu5728 on 7/1/16.
 */
public class EntityAISheepEating<T extends EntityLivingBase> extends EntityAITarget
{
    protected final Class<T> targetClass;
    private final int targetChance;
    /** Instance of EntityAINearestAttackableTargetSorter. */
    protected final net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
    protected final Predicate<? super T > targetEntitySelector;
    protected T targetEntity;

    private long lastEaten;
    private long wolfStarvingPeriod;

    public EntityAISheepEating(EntityCreature creature, Class<T> classTarget, boolean checkSight, long wolfStarvingPeriod)
    {
        this(creature, classTarget, checkSight, false);
        this.wolfStarvingPeriod = wolfStarvingPeriod;
    }

    public EntityAISheepEating(EntityCreature creature, Class<T> classTarget, boolean checkSight, boolean onlyNearby)
    {
        this(creature, classTarget, 10, checkSight, onlyNearby, (Predicate <? super T >)null);
    }

    public EntityAISheepEating(EntityCreature creature, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable final Predicate <? super T > targetSelector)
    {
        super(creature, checkSight, onlyNearby);
        this.targetClass = classTarget;
        this.targetChance = chance;
        this.theNearestAttackableTargetSorter = new net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter(creature);
        this.setMutexBits(1);
        this.targetEntitySelector = new Predicate<T>()
        {
            public boolean apply(@Nullable T p_apply_1_)
            {
                return p_apply_1_ == null ? false : (targetSelector != null && !targetSelector.apply(p_apply_1_) ? false : (!EntitySelectors.NOT_SPECTATING.apply(p_apply_1_) ? false : EntityAISheepEating.this.isSuitableTarget(p_apply_1_, false)));
            }
        };

        this.lastEaten = System.currentTimeMillis();
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if(System.currentTimeMillis() - this.lastEaten >= this.wolfStarvingPeriod){
            this.taskOwner.setHealth(0);
        }

        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
        {
            return false;
        }
        else if (this.targetClass != EntityPlayer.class && this.targetClass != EntityPlayerMP.class)
        {
            List<T> list = this.taskOwner.worldObj.<T>getEntitiesWithinAABB(this.targetClass, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);

            if (list.isEmpty())
            {
                return false;
            }
            else
            {
                Collections.sort(list, this.theNearestAttackableTargetSorter);
                this.targetEntity = list.get(0);
                return true;
            }
        }
        else
        {
            this.targetEntity = (T)this.taskOwner.worldObj.getNearestAttackablePlayer(this.taskOwner.posX, this.taskOwner.posY + (double)this.taskOwner.getEyeHeight(), this.taskOwner.posZ, this.getTargetDistance(), this.getTargetDistance(), new Function<EntityPlayer, Double>()
            {
                @Nullable
                public Double apply(@Nullable EntityPlayer p_apply_1_)
                {
                    ItemStack itemstack = p_apply_1_.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                    if (itemstack != null && itemstack.getItem() == Items.SKULL)
                    {
                        int i = itemstack.getItemDamage();
                        boolean flag = EntityAISheepEating.this.taskOwner instanceof EntitySkeleton && ((EntitySkeleton) EntityAISheepEating.this.taskOwner).getSkeletonType() == 0 && i == 0;
                        boolean flag1 = EntityAISheepEating.this.taskOwner instanceof EntityZombie && i == 2;
                        boolean flag2 = EntityAISheepEating.this.taskOwner instanceof EntityCreeper && i == 4;

                        if (flag || flag1 || flag2)
                        {
                            return Double.valueOf(0.5D);
                        }
                    }

                    return Double.valueOf(1.0D);
                }
            }, (Predicate<EntityPlayer>)this.targetEntitySelector);
            return this.targetEntity != null;
        }
    }

    protected AxisAlignedBB getTargetableArea(double targetDistance)
    {
        return this.taskOwner.getEntityBoundingBox().expand(targetDistance, 4.0D, targetDistance);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.targetEntity);
//        this.lastEaten = System.currentTimeMillis();
        super.startExecuting();
    }

    public void updateTask(){
        /*if(targetEntity != null)
        if(targetEntity.getHealth() <= 0){
            this.lastEaten = System.currentTimeMillis();
        }*/

        if(targetEntity != null){
            if(targetEntity.getAttackingEntity() == this.taskOwner){
//                System.out.println("ATTACK");
                this.lastEaten = System.currentTimeMillis();
            }
        }

    }

    public static class Sorter implements Comparator<Entity>
    {
        private final Entity theEntity;

        public Sorter(Entity theEntityIn)
        {
            this.theEntity = theEntityIn;
        }

        public int compare(Entity p_compare_1_, Entity p_compare_2_)
        {
            double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
            double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
            return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
        }
    }
}
