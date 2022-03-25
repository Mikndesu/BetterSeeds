package com.github.mikn.better_seeds.client.screen

import com.github.mikn.better_seeds.BetterSeeds
import com.github.mikn.better_seeds.container.SeedModifierContainer
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class SeedModifierScreen(container: SeedModifierContainer?, playerInv: Inventory?, title: Component?) :
    AbstractContainerScreen<SeedModifierContainer?>(container, playerInv, title) {

    init {
        leftPos = 0
        topPos = 0
        imageWidth = 176
        imageHeight = 168
    }

    override fun render(stack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.render(stack, mouseX, mouseY, partialTicks)
        font.draw(stack, title, (leftPos + 20).toFloat(), (topPos + 5).toFloat(), 0x404040)
        font.draw(stack, playerInventoryTitle, (leftPos + 8).toFloat(), (topPos + 75).toFloat(), 0x404040)
    }

    override fun renderBg(stack: PoseStack, mouseX: Float, mouseY: Int, partialTicks: Int) {
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, TEXTURE)
        blit(stack, leftPos, topPos, 0, 0, imageWidth, imageHeight)
    }

    override fun renderLabels(stack: PoseStack, mouseX: Int, mouseY: Int) {}

    companion object {
        private val TEXTURE = ResourceLocation(
            BetterSeeds.MOD_ID,
            "textures/gui/example_chest.png"
        )
    }
}