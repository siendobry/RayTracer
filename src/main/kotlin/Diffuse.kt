class Diffuse(
    albedo : Colour,
    reflectance : Double
) : Material(albedo) {

    override fun scatter(hr: HitRecord, scatteredRay : Ray): Boolean {
        val hitPoint = hr.hitBy.at(hr.t)
        val scatterDirection = hr.normal + getRandomUnit()
        scatteredRay.origin = hitPoint
        scatteredRay.direction = scatterDirection
        if (scatterDirection.nearZero()) scatteredRay.direction = hr.normal
        return true
    }

}