import kotlin.math.sqrt

class Sphere(
    private val center : Point3d,
    private val radius : Double,
    material: Material
) : Entity(material) {

    override fun hit(r : Ray, hitRecord : HitRecord, minT : Double, maxT : Double) : Boolean {
        val oc = r.origin - center
        val a = dot(r.direction, r.direction)
        val b = 2 * dot(r.direction, oc)
        val c = dot(oc, oc) - radius * radius
        val delta = b * b - 4 * a * c
        if (delta < 0) return false
        val t = (-b - sqrt(delta)) / (2 * a)
        if (t < minT || t > maxT) return false
        hitRecord.hitBy = r
        hitRecord.t = t
        val outwardNormal = (r.at(t) - center) / radius
        hitRecord.material = material
        hitRecord.setHitSideNormal(outwardNormal)
        return true
    }

    companion object {
        fun parse(data: String): Sphere {
            val params = data.split(";", limit = 3).onEach { it.trim() }
            val center = Point3d.parse(params[0])
            val radius = params[1].toDouble()
            return Sphere(center, radius, Material.parse(params[2]))
        }
    }

}