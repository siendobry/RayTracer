import kotlin.math.pow
import kotlin.math.sqrt

abstract class Diffusive(
    albedo : Colour,
    private val reflectance : Double,
    val refractiveIndex : Double,
    private val diffusionFactor : Double
) : Material(albedo) {

    init {
        if (diffusionFactor < 0 || diffusionFactor > 1) {
            throw IllegalArgumentException("Diffusion factor has to be in range 0 to 1")
        }
    }

    fun applyDiffusion(v : Vector3d) = v + diffusionFactor * getRandomUnit()

    fun fresnelReflectance(otherReflectance : Double, hr : HitRecord) : Double {
        var r0 = (reflectance - otherReflectance) / (reflectance + otherReflectance)
        r0 *= r0
        var cosX = -dot(hr.normal, hr.hitBy.direction)
        if (reflectance > otherReflectance) {
            val n = reflectance / otherReflectance
            val sinThetaSq = n.pow(2) * (1 - cosX.pow(2))
            if (sinThetaSq > 1) {
                return 1.0
            }
            cosX = sqrt(1 - sinThetaSq)
        }
        val ret = r0 + (1 - r0) * (1 - cosX).pow(5)
        return (reflectance + (1 - reflectance) * ret)
    }

    fun reflectance(cos : Double, refractiveRatio : Double) : Double {
        var r0 = (1 - refractiveRatio) / (1 + refractiveRatio)
        r0 *= r0
        return r0 + (1 - r0) * (1 - cos).pow(5)
    }

}