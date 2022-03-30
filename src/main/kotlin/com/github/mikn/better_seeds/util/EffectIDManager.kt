package com.github.mikn.better_seeds.util

import com.github.mikn.better_seeds.BetterSeeds
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.world.item.ItemStack

class EffectIDManager(private val itemStack: ItemStack) {
    var listTag:ListTag
    private val rootKeyName = "Effects"
    init {
        listTag = getOrCreateListTag(itemStack)
    }
    private fun getOrCreateListTag(itemStack: ItemStack): ListTag {
        itemStack.tag?.let {
            return it.getList(rootKeyName, 10)
        }
        return ListTag()
    }
    fun setEffect(i:Int,j:Int) {
        val tag = CompoundTag()
        tag.putInt("effect_id", i)
        tag.putInt("effect_level", j)
        listTag.add(tag)
        itemStack.addTagElement(rootKeyName, listTag)
    }
}