data class HitRecord(
    var hitBy : Ray = Ray(),
    var t : Double = 0.0,
    var normal : Vector3d = Vector3d(),
    var outerFace : Boolean = true,
    var material : Material
) {

    fun setHitSideNormal(outwardNormal : Vector3d) {
        outerFace = dot(hitBy.direction, outwardNormal) < 0
        normal = if (outerFace) outwardNormal else -outwardNormal
    }

}