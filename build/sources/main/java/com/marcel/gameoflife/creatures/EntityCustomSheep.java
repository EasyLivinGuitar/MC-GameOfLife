package com.marcel.gameoflife.creatures;


import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created by marcel on 23.06.16.
 */
public class EntityCustomSheep extends Entity {
    public EntityCustomSheep(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 1.3f);
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }
}
