class Diffuse(
    albedo : Colour
) : Material(albedo) {

    override fun scatter(ray: Ray, hr: HitRecord, scatteredRay : Ray): Boolean {
        val hitPoint = ray.at(hr.t)
        val scatterDirection = hitPoint + hr.normal + getRandomUnit()
        scatteredRay.origin = hitPoint
        scatteredRay.direction = scatterDirection
        if (scatterDirection.nearZero()) scatteredRay.direction = hr.normal
        return true
    }

}