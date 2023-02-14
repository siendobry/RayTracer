class Metal(
    albedo : Colour,
    reflectance : Double
) : Material(albedo, reflectance) {

    init {
        if (reflectance < 0 || reflectance > 1) {
            throw IllegalArgumentException("Diffusion factor has to be in range 0 to 1")
        }
    }

    override fun scatter(hr: HitRecord, scatteredRay: Ray): Boolean {
        val scatteredRayData = hr.hitBy.getReflection(hr)
        scatteredRay.origin = scatteredRayData.origin
        scatteredRay.direction = applyDiffusion(scatteredRayData.direction)
        return (dot(scatteredRay.direction, hr.normal) > 0)
    }

    private fun applyDiffusion(v : Vector3d) = v + (1 - reflectance) * getRandomUnit()

    companion object {
        fun parse(data : String) : Material {
            val (colour, reflectance) = data.split(";")
            return Metal(Colour.parse(colour), reflectance.toDouble())
        }
    }

}