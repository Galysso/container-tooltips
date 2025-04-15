package io.urokhtor.minecraft.containertooltips.rendering

import net.minecraft.util.Identifier

const val Y_START: Int = 10
const val MAX_ROW_LENGTH: Int = 9
const val ITEM_SIZE_X: Int = 18
const val ITEM_SIZE_Y: Int = 20

/**
 * The padding in pixels applied by Minecraft's TooltipBackgroundRenderer.
 */
const val TOOLTIP_TOP_PADDING: Int = 3
const val TOOLTIP_BORDER_PADDING: Int = 3
const val TOOLTIP_TEXT_PADDING: Int = 0
val BACKGROUND_TEXTURE: Identifier = Identifier.ofVanilla("container/bundle/background")
val SLOT_TEXTURE: Identifier = Identifier.ofVanilla("container/bundle/slot")
