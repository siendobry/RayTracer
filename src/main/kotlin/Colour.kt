import kotlin.math.sqrt

data class Colour(
    val r : Double,
    val g : Double,
    val b : Double
) {

    // it would be good to put a constraint on rgb values,
    // so they would be required to fit in 0 to 1 range,
    // but in order for msaa (multisampling anti-aliasing)
    // to work it is impossible to do so

//    init {
//        if (r < 0 || r > 1 ||
//            g < 0 || g > 1 ||
//            b < 0 || b > 1) {
//
//            throw IllegalArgumentException("Forbidden parameter value - every parameter must be in between 0 and 1")
//        }
//    }

    operator fun plus(other : Colour) = Colour(r + other.r, g + other.g, b + other.b)

    operator fun times(multiplier : Double) = Colour(r * multiplier, g * multiplier, b * multiplier)

    operator fun times(multiplier : Int) = times(multiplier.toDouble())

    operator fun div(divider : Double) = times(1 / divider)

    operator fun div(divider : Int) = div(divider.toDouble())

    fun write(maxVal : Int, samplesCount : Int) : String {
        val finalR = clamp(r / samplesCount, 0.0, 1.0)
        val finalG = clamp(g / samplesCount, 0.0, 1.0)
        val finalB = clamp(b / samplesCount, 0.0, 1.0)
        return "${(sqrt(finalR) * maxVal).toInt()} ${(sqrt(finalG) * maxVal).toInt()} ${(sqrt(finalB) * maxVal).toInt()}"
    }
}

fun mix(c1 : Colour, c2 : Colour) = Colour(c1.r * c2.r, c1.g * c2.g, c1.b * c2.b)