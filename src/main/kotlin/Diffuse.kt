class Diffuse(
    albedo : Colour
) : Material(albedo, 0.0) {

    override fun scatter(hr: HitRecord, scatteredRay : Ray): Boolean {
        val hitPoint = hr.hitBy.at(hr.t)
        val scatterDirection = hr.normal + getRandomUnit()
        scatteredRay.origin = hitPoint
        scatteredRay.direction = scatterDirection
        if (scatterDirection.nearZero()) scatteredRay.direction = hr.normal
        return true
    }

    companion object {
        fun parse(data : String) : Material {
            return Diffuse(Colour.parse(data))
        }
    }

}