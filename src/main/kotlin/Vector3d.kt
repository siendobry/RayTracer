import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

data class Vector3d(
    var x : Double = 0.0,
    var y : Double = 0.0,
    var z : Double = 0.0
) {

    operator fun plusAssign(other : Vector3d) {
        x += other.x
        y += other.y
        z += other.z
    }

    operator fun timesAssign(multiplier : Double) {
        x *= multiplier
        y *= multiplier
        z *= multiplier
    }

    operator fun unaryMinus() = Vector3d(-x, -y, -z)

    operator fun plus(other : Vector3d) = Vector3d(x + other.x, y + other.y, z + other.z)

    operator fun minus(other : Vector3d) = this + (-other)

    operator fun times(multiplier : Double) = Vector3d(x * multiplier, y * multiplier, z * multiplier)

    operator fun div(divider : Double) = this * (1 / divider)

    override fun toString() = "($x, $y, $z)"

    fun length() = sqrt(lengthSq())

    fun lengthSq() = dot(this, this)

    fun normalized() = this / length()

    fun nearZero() = length() < (10.0).pow(-8)

}

fun dot(a : Vector3d, b : Vector3d) : Double{
    return (a.x * b.x
         + a.y * b.y
         + a.z * b.z)
}

fun cross(a : Vector3d, b : Vector3d) : Vector3d {
    return Vector3d(a.y * b.z - a.z * b.y,
                    a.z * b.x - a.x * b.z,
                    a.x * b.y - a.y * b.x)
}

fun getRandomVector3d(min : Double, max : Double) = Vector3d(
    Random.nextDouble(min, max),
    Random.nextDouble(min, max),
    Random.nextDouble(min, max))

fun getRandomUnit() : Vector3d {
    return getRandomVector3d(-1.0, 1.0).normalized()
}

fun getRandomDisk() : Vector3d {
    val v = getRandomUnit()
    v.z = 0.0
    return v
}

typealias Point3d = Vector3d
