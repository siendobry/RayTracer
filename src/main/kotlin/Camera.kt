import kotlin.math.tan

class Camera(
    val lookfrom : Point3d,
    val lookat : Point3d,
    val vup : Vector3d,
    vfov : Int,
    val aspectRatio : Double,
    aperture : Double,
    val focusDist : Double
) {

    val theta = degToRad(vfov)
    val h = tan(theta / 2)
    val viewportHeight = 2.0 * h
    val viewportWidth = viewportHeight * aspectRatio

    val w = (lookfrom - lookat).normalized()
    val u = cross(vup, w).normalized()
    val v = cross(w, u)

    val origin = lookfrom
    val horizontal = focusDist * viewportWidth * u
    val vertical = focusDist * viewportHeight * v
    val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - focusDist * w

    val lensRadius = aperture / 2

    fun getRay(s : Double, t : Double) : Ray {
        val rand = lensRadius * getRandomDisk()
        val offset = u * rand.x + v * rand.y
        val offsetOrigin = origin + offset
        return Ray(offsetOrigin, (lowerLeftCorner + s * horizontal + t * vertical - offsetOrigin).normalized())
    }

}