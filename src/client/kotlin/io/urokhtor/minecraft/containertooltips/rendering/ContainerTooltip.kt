package io.urokhtor.minecraft.containertooltips.rendering

import com.anthonyhilyard.legendarytooltips.tooltip.TooltipDecor
import io.urokhtor.minecraft.containertooltips.Container
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.TextColor
import net.minecraft.util.Colors
import net.minecraft.util.Formatting
import kotlin.math.roundToInt

class ContainerTooltip {
    fun render(textRenderer: TextRenderer, horizontalCenter: Int, drawContext: DrawContext, container: Container) {
        resetLegendaryTooltipsColors()

        // Calcul de la position de l'inventaire
        val contentX = horizontalCenter - (getItemsOnOneRow(container) / 2.0 * ITEM_SIZE_X).toInt()
        val contentY = Y_START + textRenderer.fontHeight + TOOLTIP_TOP_PADDING + TOOLTIP_TEXT_PADDING // Espacement supplémentaire du texte

        // Calcul de la largeur et de la hauteur de l'inventaire
        val contentWidth = getWidth(container)
        val contentHeight = getHeight(container)

        // Calcul de la largeur du texte
        val textWidth = textRenderer.getWidth(container.name)

        // Calcul de la largeur et de la hauteur du fond
        val backgroundX = contentX - TOOLTIP_BORDER_PADDING
        val backgroundY = Y_START
        // Si la largeur du texte dépasse la largeur de l'inventaire, ajuster la largeur du fond
        val backgroundWidth = maxOf(contentWidth + TOOLTIP_BORDER_PADDING * 2, textWidth + TOOLTIP_BORDER_PADDING * 2)
        val backgroundHeight = contentHeight + textRenderer.fontHeight + TOOLTIP_TOP_PADDING * 2 + TOOLTIP_TEXT_PADDING // Ajustement pour une meilleure marge en bas

        val backgroundColor = 0xAA000000.toInt() // Sombre avec opacité
        val borderColor = 0xFF8B4513.toInt()     // Bord blanc opaque

        // 1. Fond sombre
        drawContext.fill(
            backgroundX,
            backgroundY,
            backgroundX + backgroundWidth,
            backgroundY + backgroundHeight,
            backgroundColor
        )

        // 2. Bord marrons (haut, bas, gauche, droite)
        drawContext.fill(backgroundX, backgroundY, backgroundX + backgroundWidth, backgroundY + 1, borderColor) // haut
        drawContext.fill(backgroundX, backgroundY + backgroundHeight - 1, backgroundX + backgroundWidth, backgroundY + backgroundHeight, borderColor) // bas
        drawContext.fill(backgroundX, backgroundY, backgroundX + 1, backgroundY + backgroundHeight, borderColor) // gauche
        drawContext.fill(backgroundX + backgroundWidth - 1, backgroundY, backgroundX + backgroundWidth, backgroundY + backgroundHeight, borderColor) // droite

        // 3. Texte centré horizontalement
        val textX = backgroundX + (backgroundWidth - textWidth) / 2
        drawContext.drawText(textRenderer, container.name, textX, Y_START + TOOLTIP_TOP_PADDING + TOOLTIP_TEXT_PADDING, Colors.WHITE, true)

        // 4. Texture de fond (au-dessus du fond sombre mais en-dessous des slots)
        // Le même centrage horizontal est appliqué à l'inventaire
        val inventoryX = backgroundX + (backgroundWidth - contentWidth) / 2
        drawContext.drawGuiTexture(
            BACKGROUND_TEXTURE,
            inventoryX,
            contentY,
            contentWidth,
            contentHeight
        )

        // 5. Affichage des items
        container.inventory.chunked(getItemsOnOneRow(container)).forEachIndexed { stackIndex, itemStacks ->
            itemStacks.forEachIndexed { itemIndex, itemStack ->
                val xOffset = inventoryX + itemIndex * ITEM_SIZE_X + 1
                val yOffset = contentY + stackIndex * ITEM_SIZE_Y + 1
                drawContext.drawGuiTexture(SLOT_TEXTURE, xOffset, yOffset, 0, ITEM_SIZE_X, ITEM_SIZE_Y)
                drawContext.drawItem(itemStack, xOffset + 1, yOffset + 1)
                drawContext.drawItemInSlot(textRenderer, itemStack, xOffset + 1, yOffset + 1)
            }
        }
    }

    private fun getWidth(container: Container): Int {
        val itemsOnOneRow = getItemsOnOneRow(container)
        return itemsOnOneRow * ITEM_SIZE_X + 2
    }

    private fun getHeight(container: Container): Int {
        return rowCount(container) * ITEM_SIZE_Y + 2
    }

    private fun rowCount(container: Container): Int {
        return if (container.inventory.size < 9) 1 else (container.inventory.size / 9.0).roundToInt()
    }

    private fun getItemsOnOneRow(container: Container) = if (container.inventory.size < 9) container.inventory.size else MAX_ROW_LENGTH

    fun resetLegendaryTooltipsColors() {
        TooltipDecor.setCurrentTooltipBackgroundStart(TextColor.fromFormatting(Formatting.BLACK)!!.rgb)
        TooltipDecor.setCurrentTooltipBackgroundEnd(TextColor.fromFormatting(Formatting.BLACK)!!.rgb)
        TooltipDecor.setCurrentTooltipBorderStart(TextColor.fromFormatting(Formatting.WHITE)!!.rgb)
        TooltipDecor.setCurrentTooltipBorderEnd(TextColor.fromFormatting(Formatting.WHITE)!!.rgb)
    }
}
