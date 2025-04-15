package io.urokhtor.minecraft.containertooltips

import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemStack

data class Container(val name: String, val inventory: List<ItemStack>, val generated: Boolean) {
    val isEmpty: Boolean
        get() = inventory.all { it.isEmpty }

    val blockEntity: BlockEntity?
        get() = null // Placeholder for actual block entity retrieval logic
}

object CurrentContainerContext {

    private var container: Container? = null

    fun get(): Container? {
        return container
    }

    fun set(container: Container) {
        this.container = container
    }

    fun reset() {
        this.container = null
    }
}
