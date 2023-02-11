class Camera {

    val aspectRatio = 16.0 / 9
    val viewportHeight = 2.0
    val viewportWidth = viewportHeight * aspectRatio
    val focalLength = 1.0
    val origin = Point3d(0.0, 0.0, 0.0)
    val horizontal = Vector3d(viewportWidth, 0.0, 0.0)
    val vertical = Vector3d(0.0, viewportHeight, 0.0)
    val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - Vector3d(0.0, 0.0, focalLength)

    fun getRay(u : Double, v : Double) : Ray {
        return Ray(origin, lowerLeftCorner + u * horizontal + v * vertical)
    }

}