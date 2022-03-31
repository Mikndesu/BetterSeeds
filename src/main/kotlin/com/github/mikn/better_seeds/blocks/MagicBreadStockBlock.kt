package com.github.mikn.better_seeds.blocks

import com.github.mikn.better_seeds.blocks.entity.MagicBreadStockBlockEntity
import com.github.mikn.better_seeds.blocks.entity.SeedModifierBlockEntity
import com.github.mikn.better_seeds.container.MagicBreadBlockContainer
import com.github.mikn.better_seeds.container.SeedModifierBlockContainer
import com.github.mikn.better_seeds.init.BlockEntityInit
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.Nameable
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult
import net.minecraftforge.network.NetworkHooks

class MagicBreadStockBlock(properties:Properties): Block(properties), EntityBlock {
    override fun <T : BlockEntity?> getTicker(
        p_153212_: Level,
        p_153213_: BlockState,
        p_153214_: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if (p_153212_.isClientSide) null else BlockEntityTicker { _: Level?, _: BlockPos?, _: BlockState?, blockEntity: T -> (blockEntity as MagicBreadStockBlockEntity).tick() }
    }

    override fun newBlockEntity(p_153215_: BlockPos, p_153216_: BlockState): BlockEntity? {
        return MagicBreadStockBlockEntity(p_153215_, p_153216_)
    }

    override fun use(
        p_60503_: BlockState,
        p_60504_: Level,
        p_60505_: BlockPos,
        p_60506_: Player,
        p_60507_: InteractionHand,
        p_60508_: BlockHitResult
    ): InteractionResult {
        if (!p_60504_.isClientSide && p_60504_.getBlockEntity(p_60505_) is MagicBreadStockBlockEntity) {
            val chest = p_60504_.getBlockEntity(p_60505_) as MagicBreadStockBlockEntity
            val container = SimpleMenuProvider(MagicBreadBlockContainer.getServerContainer(chest, p_60505_),
                name)
            NetworkHooks.openGui(p_60506_ as ServerPlayer, container, p_60505_);
        }
        return InteractionResult.SUCCESS
    }
}