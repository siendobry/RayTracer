data class Ray(
    var origin : Point3d = Point3d(0.0, 0.0, 0.0),
    var direction : Vector3d = Vector3d(0.0, 0.0, 0.0)
) {

    fun at(t : Double) = origin + direction * t

    fun getReflection(hr : HitRecord) : Ray {
        return Ray(at(hr.t), direction - 2 * dot(direction, hr.normal) * hr.normal)
    }

}