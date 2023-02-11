class Metal(
    albedo : Colour,
    private val fuzz : Double
) : Material(albedo) {

    init {
        if (fuzz < 0 || fuzz > 1) {
            throw IllegalArgumentException("Fuzz has to be in range 0 to 1")
        }
    }

    override fun scatter(ray: Ray, hr: HitRecord, scatteredRay: Ray): Boolean {
        val scatteredRayData = ray.getReflection(hr)
        scatteredRay.origin = scatteredRayData.origin
        scatteredRay.direction = scatteredRayData.direction + fuzz * getRandomUnit()
        return (dot(scatteredRay.direction, hr.normal) > 0)
    }

}