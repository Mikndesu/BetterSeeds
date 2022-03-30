package com.github.mikn.better_seeds.util

enum class EffectEnum(val id:Int){

    // The reason there are absent ids is
    // that the number stands for bad effect
    MOVEMENT_SPEED(1),
    MOVEMENT_SLOWDOWN(2),
    DIG_SPEED(3),
    DIG_SLOWDOWN(4),
    DAMAGE_BOOST(5),
    HEAL(6),
    JUMP(8),
    REGENERATION(10),
    DAMAGE_RESISTANCE(11),
    FIRE_RESISTANCE(12),
    WATER_BREATHING(13),
    INVISIBILITY(14),
    NIGHT_VISION(16),
    WEAKNESS(18),
    HEALTH_BOOST(21),
    ABSORPTION(22),
    SATURATION(23),
    GLOWING(24),
    LEVITATION(25),
    LUCK(26),
    SLOW_FALLING(28);

    companion object {
        fun getEffectById(id: Int): EffectEnum? {
            return values().find { it.id == id }
        }
    }
}