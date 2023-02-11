data class HitRecord(
    var t : Double = 0.0,
    var normal : Vector3d = Vector3d(0.0, 0.0, 0.0),
    var outerFace : Boolean = true,
    var material : Material
) {

    fun setHitSide(r : Ray) {
        outerFace = dot(r.direction, normal) < 0
    }

}