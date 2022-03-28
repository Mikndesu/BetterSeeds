package com.github.mikn.better_seeds.container

import com.github.mikn.better_seeds.BetterSeeds
import com.github.mikn.better_seeds.init.BlockInit
import com.github.mikn.better_seeds.init.ContainerInit
import com.github.mikn.better_seeds.init.ItemInit
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.ResultContainer
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import java.util.*


class SeedModifierBlockContainer : AbstractContainerMenu {
    private val DECREASED_EXP = 6
    private val outputInventory = ResultContainer()
    private val inputInventory = object : SimpleContainer(1) {
        override fun setChanged() {
            super.setChanged()
            this@SeedModifierBlockContainer.slotsChanged(this)
        }
    }

    private var itemStackCountBeforeTaken: Int = 0
    private var levelPosCallable: ContainerLevelAccess? = null

    constructor(
        id: Int,
        inventory: Inventory,
        levelPosCallable: ContainerLevelAccess = ContainerLevelAccess.NULL
    ) : super(ContainerInit.SEED_MODIFIER_CONTAINER.get(), id) {
        this.levelPosCallable = levelPosCallable
        this.addSlot(object : Slot(inputInventory, 0, 15, 25) {
            override fun mayPlace(stack: ItemStack): Boolean {
                return Items.WHEAT_SEEDS == stack.item
            }

            override fun getMaxStackSize(): Int {
                return 64
            }
        })
        this.addSlot(object : Slot(this.outputInventory, 1, 145, 25) {
            override fun mayPlace(stack: ItemStack): Boolean {
                return false
            }

            override fun getMaxStackSize(): Int {
                return 64
            }

            override fun onTake(player: Player, itemStack: ItemStack) {
                val itemStack = ItemStack(Items.WHEAT_SEEDS)
                itemStack.count = itemStackCountBeforeTaken - 1
                this@SeedModifierBlockContainer.inputInventory.setItem(0, itemStack)
                player.giveExperiencePoints(-DECREASED_EXP)
            }
        })
        for (i in 0..2) {
            for (j in 0..8) {
                addSlot(Slot(inventory, j + i * 9 + 9, 8 + j * 18, 62 + i * 18))
            }
        }
        for (k in 0..8) {
            addSlot(Slot(inventory, k, 8 + k * 18, 120))
        }
    }

    override fun slotsChanged(inventory: Container) {
        super.slotsChanged(inventory)
        if (inventory == this.inputInventory) {
            this.updateInventory()
        }
    }

    private fun updateInventory() {
        val seed = this.inputInventory.getItem(0)
        itemStackCountBeforeTaken = seed.count
        val ready = !seed.isEmpty
        if (!ready) {
            this.outputInventory.setItem(0, ItemStack.EMPTY)
        } else {
            val itemStack = ItemStack(ItemInit.MAGIC_WHEAT_SEEDS.get())
            val tag = CompoundTag()
            tag.putInt("id", Random().nextInt(30)+1)
            itemStack.tag = tag
            this.outputInventory.setItem(0, itemStack)
            BetterSeeds.LOGGER.error(itemStack.tag!!.getInt("id"))
        }
        this.broadcastChanges()
    }

    override fun removed(player: Player) {
        super.removed(player)
        this.levelPosCallable?.execute { _, _ ->
            this.clearContainer(player, this.inputInventory)
        }
    }

    override fun stillValid(player: Player): Boolean {
        return stillValid(this.levelPosCallable, player, BlockInit.SEED_MODIFIER.get())
    }

    override fun quickMoveStack(player: Player?, index: Int): ItemStack? {
        var itemstack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasItem()) {
            val itemstack1 = slot.item
            itemstack = itemstack1.copy()
            if (index == 0) {
                if (!moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY
                }
            } else if (index == 1) {
                if (!moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY
                }
            } else if (itemstack1.item === Items.BOOK) {
                if (!moveItemStackTo(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY
                }
            } else {
                if (slots[0].hasItem() || !slots[0].mayPlace(itemstack1)) {
                    return ItemStack.EMPTY
                }
                val itemstack2 = itemstack1.copy()
                itemstack2.count = 1
                itemstack1.shrink(1)
                slots[0].set(itemstack2)
            }
            if (itemstack1.isEmpty) {
                slot.set(ItemStack.EMPTY)
            } else {
                slot.setChanged()
            }
            if (itemstack1.count == itemstack.count) {
                return ItemStack.EMPTY
            }
            slot.onTake(player, itemstack1)
        }
        return itemstack
    }
}