package com.github.mikn.better_seeds.client.screen

import com.github.mikn.better_seeds.BetterSeeds
import com.github.mikn.better_seeds.container.MagicBreadBlockContainer
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraftforge.client.event.ScreenEvent
import net.minecraftforge.common.MinecraftForge

class MagicBreadStockScreen(screenContainer: MagicBreadBlockContainer?, inv: Inventory?, title: Component?) :
    AbstractContainerScreen<MagicBreadBlockContainer?>(screenContainer, inv, title) {
    override fun render(matrixStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.renderBackground(matrixStack)
        super.render(matrixStack, mouseX, mouseY, partialTicks)
        this.renderTooltip(matrixStack, mouseX, mouseY)
    }

    override fun renderBackground(matrixStack: PoseStack) {
        this.renderBackground(matrixStack, 0)
    }

    override fun renderBackground(matrixStack: PoseStack, vOffset: Int) {
        if (minecraft!!.level != null) {
            this.fillGradient(matrixStack, 0, 0, width, height, -1072689136, -804253680)
            MinecraftForge.EVENT_BUS.post(ScreenEvent.BackgroundDrawnEvent(this, matrixStack))
        } else {
            renderDirtBackground(vOffset)
        }
    }

    override fun renderTooltip(matrixStack: PoseStack, x: Int, y: Int) {
        if (this.menu!!.carried.isEmpty && this.hoveredSlot != null && this.hoveredSlot!!.hasItem()) {
            this.renderTooltip(matrixStack, this.hoveredSlot!!.item, x, y)
        }
    }

    override fun renderLabels(matrixStack: PoseStack, mouseX: Int, mouseY: Int) {
    }

    override fun renderBg(matrixStack: PoseStack, partialTicks: Float, x: Int, y: Int) {
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, GUI)
        val relX = (width - imageWidth) / 2
        val relY = (height - imageHeight) / 2
        this.blit(matrixStack, relX, relY, 0, 0, imageWidth, imageHeight)
    }

    companion object {
        @JvmStatic
        private val GUI = ResourceLocation("textures/gui/container/shulker_box.png")
    }
}