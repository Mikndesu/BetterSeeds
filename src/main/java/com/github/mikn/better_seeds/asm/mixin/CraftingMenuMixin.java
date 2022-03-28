package com.github.mikn.better_seeds.asm.mixin;

import com.github.mikn.better_seeds.BetterSeeds;
import com.github.mikn.better_seeds.init.ItemInit;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;

@Mixin(CraftingMenu.class)
public class CraftingMenuMixin {
    /**
     * @author Mikn
     * @reason This way is easier than defining all 30*30*30 patterns as json files
     */
    @Overwrite
    protected static void slotChangedCraftingGrid(AbstractContainerMenu p_150547_, Level p_150548_, Player p_150549_, CraftingContainer p_150550_, ResultContainer p_150551_) {
        if (!p_150548_.isClientSide) {
            ServerPlayer serverplayer = (ServerPlayer)p_150549_;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<CraftingRecipe> optional = p_150548_.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, p_150550_, p_150548_);
            if (optional.isPresent()) {
                CraftingRecipe craftingrecipe = optional.get();
                if (p_150551_.setRecipeUsed(p_150548_, serverplayer, craftingrecipe)) {
                    itemstack = craftingrecipe.assemble(p_150550_);
                    ItemStack itemStack1 = p_150550_.getItem(3);
                    ItemStack itemStack2 = p_150550_.getItem(4);
                    ItemStack itemStack3 = p_150550_.getItem(5);
                    if(ItemInit.getMAGIC_WHEAT().get().equals(p_150550_.getItem(3).getItem())) {
                        BetterSeeds.getLOGGER().error(itemStack1.getTag().getInt("effect_id"));
                        BetterSeeds.getLOGGER().error(itemStack2.getTag().getInt("effect_id"));
                        BetterSeeds.getLOGGER().error(itemStack3.getTag().getInt("effect_id"));
                    }
                }
            }
            p_150551_.setItem(0, itemstack);
            p_150547_.setRemoteSlot(0, itemstack);
            serverplayer.connection.send(new ClientboundContainerSetSlotPacket(p_150547_.containerId, p_150547_.incrementStateId(), 0, itemstack));
        }
    }
}
