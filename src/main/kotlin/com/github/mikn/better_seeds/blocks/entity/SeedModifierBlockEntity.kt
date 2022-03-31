package com.github.mikn.better_seeds.blocks.entity

import com.github.mikn.better_seeds.init.BlockEntityInit
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.Nameable
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class SeedModifierBlockEntity(blockPos: BlockPos?, blockState: BlockState?) : BlockEntity(BlockEntityInit.SEED_MODIFIER_BLOCK_ENTITY.get(), blockPos, blockState), Nameable {

    private var customName: Component? = null

    override fun saveAdditional(compoundTag: CompoundTag) {
        super.saveAdditional(compoundTag)
        if (hasCustomName()) {
            compoundTag.putString("CustomName", Component.Serializer.toJson(this.customName))
        }
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        if (tag.contains("CustomName", 8)) {
            this.customName = Component.Serializer.fromJson(tag.getString("CustomName"))
        }
    }

    override fun getName(): Component {
        return if (this.customName != null) this.customName!! else TranslatableComponent("")
    }

    fun setCustomName(name: Component?) {
        this.customName = name
    }

    override fun getCustomName(): Component? {
        return this.customName
    }
}