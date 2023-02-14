class Triangle(
    private val a : Point3d,
    private val b : Point3d,
    private val c : Point3d,
    material : Material
) : Plane(a, cross(b - a, c - a), material) {

    override fun hit(r: Ray, hitRecord: HitRecord, minT: Double, maxT: Double): Boolean {
        val intersectPlane = super.hit(r, hitRecord, minT, maxT)
        if (!intersectPlane) return false
        val ab = b - a
        val bc = c - b
        val ca = a - c
        val ahp = r.at(hitRecord.t) - a
        val bhp = r.at(hitRecord.t) - b
        val chp = r.at(hitRecord.t) - c
        if (dot(normal, cross(ab, ahp)) > 0 &&
            dot(normal, cross(bc, bhp)) > 0 &&
            dot(normal, cross(ca, chp)) > 0) return true
        return false
    }

    companion object {
        fun parse(data : String) : Triangle {
            val params = data.split(";", limit = 4).onEach { it.trim() }
            val a = Point3d.parse(params[0])
            val b = Point3d.parse(params[1])
            val c = Point3d.parse(params[2])
            return Triangle(a, b, c, Material.parse(params[3]))
        }
    }

}