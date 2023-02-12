class Metal(
    albedo : Colour,
    reflectance : Double,
    refractiveIndex : Double,
    diffusionFactor : Double
) : Diffusive(albedo, reflectance, refractiveIndex, diffusionFactor) {

    override fun scatter(hr: HitRecord, scatteredRay: Ray): Boolean {
        val scatteredRayData = hr.hitBy.getReflection(hr)
        scatteredRay.origin = scatteredRayData.origin
        scatteredRay.direction = super.applyDiffusion(scatteredRayData.direction)
        return (dot(scatteredRay.direction, hr.normal) > 0)
    }

}