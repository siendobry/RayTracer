import kotlin.random.Random

fun colourRay(r : Ray, world : EntityList, maxDepth : Int) : Colour {
    if (maxDepth <= 0) {
        return Colour(0.0, 0.0, 0.0)
    }
    val closestHit = world.getClosestHit(r)
    if (closestHit != null) {
        val scatteredRay = Ray()
        if (closestHit.material.scatter(r, closestHit, scatteredRay)) {
            return mix(closestHit.material.getAlbedo(), colourRay(scatteredRay, world, maxDepth - 1))
        }
        return Colour(0.0, 0.0, 0.0)
    }
    val unitVector = r.direction.normalized()
    val t = 0.5 * (unitVector.y + 1)
    return (1 - t) * Colour(1.0, 1.0, 1.0) + t * Colour(0.5, 0.7, 1.0)
}

fun main() {
    try {
        val camera = Camera()
        val width = 1280
        val height = (width / camera.aspectRatio).toInt()
        val samplesCount = 100
        val maxDepth = 50
        val world = EntityList()

        val materialGround = Diffuse(Colour(0.8, 0.8, 0.0))
        val materialCenter = Diffuse(Colour(0.7, 0.3, 0.3))
        val materialLeft = Metal(Colour(0.6, 0.6, 0.6), 0.1)
        val materialRight = Metal(Colour(0.8, 0.6, 0.2), 0.3)
        world.add(Sphere(Point3d(0.0, 0.0, -1.0), 0.5, materialCenter))
        world.add(Sphere(Point3d(1.0, 0.0, -1.0), 0.5, materialRight))
        world.add(Sphere(Point3d(-1.0, 0.0, -1.0), 0.5, materialLeft))
        world.add(Sphere(Point3d(0.0, -100.5, -1.0), 100.0, materialGround))

        println("P3\n$width $height\n255\n")
        for (j in height - 1 downTo 0) {
            for (i in 0 until width) {
                var pixelColour = Colour(0.0, 0.0, 0.0)
                for (k in 0 until samplesCount) {
                    val u = (i.toDouble() + Random.nextDouble()) / (width - 1)
                    val v = (j.toDouble() + Random.nextDouble()) / (height - 1)
                    val r = camera.getRay(u, v)
                    pixelColour += colourRay(r, world, maxDepth)
                }
                println(pixelColour.write(255, samplesCount))
            }
        }
    }
    catch (e : Exception) {
        e.printStackTrace()
        throw RuntimeException(e.message)
    }
}