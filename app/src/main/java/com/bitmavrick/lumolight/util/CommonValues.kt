package com.bitmavrick.lumolight.util

class TimeDuration{
    companion object {
        val list: List<Duration> = listOf(
            Duration("N/A", -1),
            Duration("1 min", 1),
            Duration("2 min", 2),
            Duration("5 min", 5),
            Duration("10 min", 10),
            Duration("15 min", 15),
            Duration("30 min", 30),
            Duration("45 min", 45),
            Duration("60 min", 60),
            Duration("120 min", 120),
        )
    }
}

class ColorValue{
    companion object {
        val list: List<Color> = listOf(
            Color("White", "#FFFFFF"),
            // Color("Silver", "#C0C0C0"),
            Color("Gray", "#808080"),
            Color("Black", "#000000"),
            Color("Red", "#FF0000"),
            // Color("Maroon", "#800000"),
            Color("Yellow", "#FFFF00"),
            // Color("Olive", "#808000"),
            Color("Lime", "#00FF00"),
            Color("Green", "#008000"),
            Color("Aqua", "#00FFFF"),
            // Color("Teal", "#008080"),
            Color("Blue", "#0000FF"),
            // Color("Navy", "#000080"),
            Color("Fuchsia", "#FF00FF"),
            Color("Purple", "#800080"),


        )
    }
}

class BrightnessValue{
    companion object {
        val list : List<Brightness> = listOf(
            Brightness("100%", 100),
            Brightness("80%", 80),
            Brightness("60%", 60),
            Brightness("40%", 40),
            Brightness("20%", 20)
        )
    }
}


data class Duration(
    val duration: String,
    val time: Int
)

data class Color(
    val name: String,
    val code: String,
)

data class Brightness(
    val title: String,
    val value: Int
)