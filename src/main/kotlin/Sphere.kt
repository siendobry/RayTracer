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
        val t = (-b - sqrt(delta)) / (2 * a)
        if (delta < 0 || t < minT || t > maxT) {
            return false
        }
        hitRecord.t = t
        hitRecord.normal = (r.at(t) - center) / radius
        hitRecord.material = material
        hitRecord.setHitSide(r)
        return true
    }

}