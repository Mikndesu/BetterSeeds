package com.github.mikn.better_seeds.util

enum class EffectEnum(val id:Int){
    MOVEMENT_SPEED(1),
    MOVEMENT_SLOWDOWN(2),
    DIG_SPEED(3),
    DIG_SLOWDOWN(4),
    DAMAGE_BOOST(5),
    HEAL(6),
    HARM(7),
    JUMP(8),
    CONFUSION(9),
    REGENERATION(10),
    DAMAGE_RESISTANCE(11),
    FIRE_RESISTANCE(12),
    WATER_BREATHING(13),
    INVISIBILITY(14),
    BLINDNESS(15),
    NIGHT_VISION(16),
    HUNGER(17),
    WEAKNESS(18),
    POISON(19),
    WITHER(20),
    HEALTH_BOOST(21),
    ABSORPTION(22),
    SATURATION(23),
    GLOWING(24),
    LEVITATION(25),
    LUCK(26),
    UNLUCK(27),
    SLOW_FALLING(28),
    CONDUIT_POWER(29),
    DOLPHINS_GRACE(30);

    companion object {
        fun getEffectById(id: Int): EffectEnum? {
            return values().find { it.id == id }
        }
    }
}