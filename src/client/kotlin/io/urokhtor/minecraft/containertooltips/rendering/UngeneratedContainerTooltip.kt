package io.urokhtor.minecraft.containertooltips.rendering

import io.urokhtor.minecraft.containertooltips.Container
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.util.Colors

class UngeneratedContainerTooltip {
    fun render(textRenderer: TextRenderer, horizontalCenter: Int, drawContext: DrawContext, container: Container) {
        drawContext.drawTooltip(
            textRenderer,
            listOf(
                Text.of(container.name).asOrderedText(),
                Text.translatable("container.ungenerated").withColor(Colors.GRAY).asOrderedText()
            ),
            DirectTooltipPositioner(),
            horizontalCenter - textRenderer.getWidth(container.name) / 2,
            Y_START
        )
    }
}