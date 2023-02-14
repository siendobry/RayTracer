import kotlin.math.pow

open class Plane(
    private val point : Point3d,
    var normal : Vector3d,
    material : Material
) : Entity(material) {

    init {
        normal = normal.normalized()
    }

    override fun hit(r: Ray, hitRecord: HitRecord, minT: Double, maxT: Double): Boolean {
        val denominator = dot(r.direction, normal)
        if (denominator < (10.0).pow(-8)) return false
        val t = dot(point - r.origin, normal) / denominator
        if (t < minT || t > maxT) return false
        hitRecord.hitBy = r
        hitRecord.t = t
        hitRecord.material = material
        hitRecord.setHitSideNormal(normal)
        return true
    }

    companion object {
        fun parse(data : String) : Plane {
            val params = data.split(";", limit = 3).onEach { it.trim() }
            val point = Point3d.parse(params[0])
            val normal = Vector3d.parse(params[1])
            return Plane(point, normal, Material.parse(params[2]))
        }
    }

}