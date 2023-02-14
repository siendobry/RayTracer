import kotlin.math.tan

class Camera(
    lookfrom : Point3d,
    lookat : Point3d,
    vup : Vector3d,
    vfov : Int,
    aspectRatio : Double,
    aperture : Double,
    focusDist : Double
) {

    private val theta = degToRad(vfov)
    private val h = tan(theta / 2)
    private val viewportHeight = 2.0 * h
    private val viewportWidth = viewportHeight * aspectRatio

    private val w = (lookfrom - lookat).normalized()
    private val u = cross(vup, w).normalized()
    private val v = cross(w, u)

    private val origin = lookfrom
    private val horizontal = focusDist * viewportWidth * u
    private val vertical = focusDist * viewportHeight * v
    private val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - focusDist * w

    private val lensRadius = aperture / 2

    fun getRay(s : Double, t : Double) : Ray {
        val rand = lensRadius * getRandomDisk()
        val offset = u * rand.x + v * rand.y
        val offsetOrigin = origin + offset
        return Ray(offsetOrigin, (lowerLeftCorner + s * horizontal + t * vertical - offsetOrigin).normalized())
    }

}