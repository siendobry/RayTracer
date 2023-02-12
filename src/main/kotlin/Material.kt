abstract class Material(
    val albedo : Colour
) {

    abstract fun scatter(hr : HitRecord, scatteredRay : Ray) : Boolean

}