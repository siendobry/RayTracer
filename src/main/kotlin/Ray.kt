import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sqrt

data class Ray(
    var origin : Point3d = Point3d(0.0, 0.0, 0.0),
    var direction : Vector3d = Vector3d(0.0, 0.0, 0.0)
) {

    fun at(t : Double) = origin + direction * t

    fun getReflection(hr : HitRecord) : Ray {
        return Ray(at(hr.t), direction - 2 * dot(direction, hr.normal) * hr.normal)
    }

    fun getRefraction(hr : HitRecord, refractiveRatio : Double) : Ray {
        val cosTheta = min(dot(-direction, hr.normal), 1.0)
        val vecPerpetual = refractiveRatio * (direction + cosTheta * hr.normal)
        val vecParallel = -sqrt(abs(1 - vecPerpetual.lengthSq())) * hr.normal
        return Ray(at(hr.t), vecPerpetual + vecParallel)
    }

}