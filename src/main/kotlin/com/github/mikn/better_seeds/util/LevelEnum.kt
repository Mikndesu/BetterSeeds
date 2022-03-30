package com.github.mikn.better_seeds.util

enum class LevelEnum(val number:Int) {

    I(1),
    II(2),
    III(3),
    IV(4),
    V(5),
    VI(6),
    VII(7),
    VIII(8),
    IX(9),
    X(10);

    companion object {
        fun getLevelEnumByNumber(number: Int): LevelEnum? {
            return values().find { it.number == number }
        }
    }
}