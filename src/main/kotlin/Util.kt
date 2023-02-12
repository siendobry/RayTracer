// Vector3d / Point3d

operator fun Double.times(v : Vector3d) = v * this


// Colour

operator fun Double.times(c : Colour) = c * this


// Utility

fun clamp(x : Double, min : Double, max : Double) : Double {
    if (x < min) return min
    else if (x > max) return max
    return x
}

fun degToRad(deg : Int) = deg * Math.PI / 180