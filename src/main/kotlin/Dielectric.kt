import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class Dielectric(
    albedo : Colour,
    private val refractiveIndex : Double
) : Material(albedo, 1.0) {

    override fun scatter(hr: HitRecord, scatteredRay: Ray): Boolean {
        val refractiveRatio = if (hr.outerFace) 1 / refractiveIndex else refractiveIndex
        val cosTheta = min(dot(-hr.hitBy.direction, hr.normal), 1.0)
        val sinTheta = sqrt(1 - cosTheta.pow(2))

        // needs high samplesPerPixel to mix reflection and refraction properly
        val outputRay = if (refractiveRatio * sinTheta > 1 || reflectance(cosTheta, refractiveRatio) > Random.nextDouble()) {
            hr.hitBy.getReflection(hr)
        } else {
            hr.hitBy.getRefraction(hr, refractiveRatio)
        }
        scatteredRay.origin = outputRay.origin
        scatteredRay.direction = outputRay.direction
        return true
    }

    private fun reflectance(cos : Double, refractiveRatio : Double) : Double {
        var r0 = (1 - refractiveRatio) / (1 + refractiveRatio)
        r0 *= r0
        return r0 + (1 - r0) * (1 - cos).pow(5)
    }

    companion object {
        fun parse(data : String) : Material {
            val (colour, refractiveIndex) = data.split(";")
            return Metal(Colour.parse(colour), refractiveIndex.toDouble())
        }
    }

}