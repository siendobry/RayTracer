import kotlin.math.max
import kotlin.math.pow

data class LightSource(
    val position : Point3d,
    val colour : Colour
) {

    fun enlighten(hr: HitRecord, world: EntityList): Colour {
        val hitPoint = hr.hitBy.at(hr.t)
        val shadowRay = (position - hitPoint).normalized()
        val closestHit = world.getClosestHit(Ray(hitPoint, shadowRay), 0.001, Double.MAX_VALUE)
        if (dot(hr.normal, shadowRay) < 0) return Colour(0.0, 0.0, 0.0)
        if (closestHit != null) return Colour(0.0, 0.0, 0.0)
        return (mix(colour, hr.material.albedo)
              * dot(hr.normal, shadowRay).pow(max(1, (hr.material.reflectance * 100).toInt())))
    }

}