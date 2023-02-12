import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class Dielectric(
    albedo : Colour,
    reflectance : Double,
    refractiveIndex : Double,
    diffusionFactor : Double
) : Diffusive(albedo, reflectance, refractiveIndex, diffusionFactor) {

    override fun scatter(hr: HitRecord, scatteredRay: Ray): Boolean {
        val refractiveRatio = if (hr.outerFace) 1 / refractiveIndex else refractiveIndex
        val cosTheta = min(dot(-hr.hitBy.direction, hr.normal), 1.0)
        val sinTheta = sqrt(1 - cosTheta.pow(2))
        val outputRay = if (refractiveRatio * sinTheta > 1 || reflectance(cosTheta, refractiveRatio) > Random.nextDouble()) {
            hr.hitBy.getReflection(hr)
        } else {
            hr.hitBy.getRefraction(hr, refractiveRatio)
        }
        scatteredRay.origin = outputRay.origin
        scatteredRay.direction = outputRay.direction
        return true
    }

}