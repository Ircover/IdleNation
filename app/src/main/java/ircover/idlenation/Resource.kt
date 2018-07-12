package ircover.idlenation

import ircover.idlenation.library.IdleNationApplication

enum class ResourceType {
    Food;

    fun getTitle() = IdleNationApplication.getString(when(this) {
        Food -> R.string.food
    })

    fun getIconResourceId() = when(this) {
        Food -> R.drawable.icon_resource_food
    }

}