package com.github.mikn.better_seeds.blocks

import com.github.mikn.better_seeds.blocks.entity.SeedModifierBlockEntity
import com.github.mikn.better_seeds.container.SeedModifierContainer
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraftforge.network.NetworkHooks

class SeedModifierBlock(properties:Properties): Block(properties), EntityBlock {
    override fun <T : BlockEntity?> getTicker(
        p_153212_: Level,
        p_153213_: BlockState,
        p_153214_: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if (p_153212_.isClientSide) null else BlockEntityTicker { _: Level?, _: BlockPos?, _: BlockState?, blockEntity: T -> (blockEntity as SeedModifierBlockEntity).tick() }
    }

    override fun newBlockEntity(p_153215_: BlockPos, p_153216_: BlockState): BlockEntity? {
        return SeedModifierBlockEntity(p_153215_, p_153216_)
    }

    override fun use(
        p_60503_: BlockState,
        p_60504_: Level,
        p_60505_: BlockPos,
        p_60506_: Player,
        p_60507_: InteractionHand,
        p_60508_: BlockHitResult
    ): InteractionResult {
        if (!p_60504_.isClientSide && p_60504_.getBlockEntity(p_60505_) is SeedModifierBlockEntity) {
            val chest = p_60504_.getBlockEntity(p_60505_) as SeedModifierBlockEntity
            val container = SimpleMenuProvider(
                SeedModifierContainer.getServerContainer(chest, p_60505_),
                SeedModifierBlockEntity.TITLE);
            NetworkHooks.openGui(p_60506_ as ServerPlayer, container, p_60505_);
        }
        return InteractionResult.SUCCESS
    }
}