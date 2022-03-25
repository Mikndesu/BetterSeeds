package com.github.mikn.better_seeds.container

import com.github.mikn.better_seeds.blocks.entity.SeedModifierBlockEntity
import com.github.mikn.better_seeds.init.BlockInit.Companion.SEED_MODIFIER
import com.github.mikn.better_seeds.init.ContainerInit
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.MenuConstructor
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.items.SlotItemHandler

class SeedModifierContainer @JvmOverloads constructor(
    id: Int,
    playerInv: Inventory,
    slots: IItemHandler? = ItemStackHandler(27),
    pos: BlockPos? = BlockPos.ZERO
) :
    AbstractContainerMenu(ContainerInit.SEED_MODIFIER.get(), id) {
    private val containerAccess: ContainerLevelAccess

    init {
        containerAccess = ContainerLevelAccess.create(playerInv.player.level, pos)
        val slotSizePlus2 = 18
        val startX = 8
        val startY = 86
        val hotbarY = 144
        val inventoryY = 18
        for (column in 0..8) {
            for (row in 0..2) {
                addSlot(
                    SlotItemHandler(
                        slots, row * 9 + column, startX + column * slotSizePlus2,
                        inventoryY + row * slotSizePlus2
                    )
                )
            }
        }
        for (column in 0..8) {
            for (row in 0..2) {
                addSlot(
                    Slot(
                        playerInv, 9 + row * 9 + column, startX + column * slotSizePlus2,
                        startY + row * slotSizePlus2
                    )
                )
            }
            addSlot(Slot(playerInv, column, startX + column * slotSizePlus2, hotbarY))
        }
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var retStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val item = slot.item
            retStack = item.copy()
            if (index < 27) {
                if (!moveItemStackTo(item, 27, slots.size, true)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(item, 0, 27, false)) return ItemStack.EMPTY
            if (item.isEmpty) {
                slot.set(ItemStack.EMPTY)
            } else {
                slot.setChanged()
            }
        }
        return retStack
    }

    override fun stillValid(player: Player): Boolean {
        return stillValid(containerAccess, player, SEED_MODIFIER.get())
    }

    companion object {
        fun getServerContainer(chest: SeedModifierBlockEntity, pos: BlockPos?): MenuConstructor {
            return MenuConstructor { id: Int, playerInv: Inventory, _: Player? ->
                SeedModifierContainer(
                    id,
                    playerInv,
                    chest.inventory,
                    pos
                )
            }
        }
    }
}