package com.github.mikn.better_seeds.items

import com.github.mikn.better_seeds.util.EffectEnum
import com.github.mikn.better_seeds.util.EffectIDManager
import com.github.mikn.better_seeds.util.LevelEnum
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.Mth
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.animal.Fox
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.event.ForgeEventFactory

class MagicBread(properties: Properties) : Item(properties.food(
    FoodProperties.Builder().fast().nutrition(5).saturationMod(0.6f).build())) {
    override fun appendHoverText(
        itemStack: ItemStack, level: Level?, list: MutableList<Component>, tooltipFlag: TooltipFlag
    ) {
        val manager = EffectIDManager(itemStack)
        manager.listTag.forEach {
            val tag = it as CompoundTag
            val id = tag.getInt("effect_id")
            val level = tag.getInt("effect_level")
            list.add(TextComponent("${EffectEnum.getEffectById(id)} ${LevelEnum.getLevelEnumByNumber(level)}"))
        }
    }
    override fun finishUsingItem(itemStack: ItemStack?, level: Level, livingEntity: LivingEntity): ItemStack? {
        val itemstack = super.finishUsingItem(itemStack, level, livingEntity)
        if (!level.isClientSide && livingEntity is Player) {
            val manager = EffectIDManager(itemstack)
            manager.listTag.forEach {
                val tag = it as CompoundTag
                val id = tag.getInt("effect_id")
                val level = tag.getInt("effect_level")
                livingEntity.addEffect(MobEffectInstance(MobEffect.byId(id), 100, level-1))
            }
        }
        return itemstack
    }
}