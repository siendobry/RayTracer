abstract class Material(
    val albedo : Colour,
    val reflectance : Double
) {

    companion object {
        fun parse(data : String) : Material {
            val (material, params) = data.split(",", limit = 2)
            material.lowercase().trim()
            return when (material) {
                "diffuse" -> Diffuse.parse(params)
                "metal" -> Metal.parse(params)
                "dielectric" -> Dielectric.parse(params)
                else -> throw IllegalArgumentException("Incorrect material type")
            }
        }
    }

    abstract fun scatter(hr : HitRecord, scatteredRay : Ray) : Boolean

}
