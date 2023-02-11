abstract class Material(
    private val albedo : Colour
) {

    abstract fun scatter(ray: Ray, hr : HitRecord, scatteredRay : Ray) : Boolean

    fun getAlbedo() : Colour {
        return albedo
    }

}