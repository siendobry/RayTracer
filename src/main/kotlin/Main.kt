import java.io.File
import kotlin.math.round
import kotlin.random.Random

import kotlinx.coroutines.*

fun colourRay(r : Ray, world : EntityList, maxDepth : Int) : Colour {
    if (maxDepth <= 0) {
        return Colour(0.0, 0.0, 0.0)
    }
    val closestHit = world.getClosestHit(r)
    if (closestHit != null) {
        val scatteredRay = Ray()
        if (closestHit.material.scatter(closestHit, scatteredRay)) {
            return mix(closestHit.material.albedo, colourRay(scatteredRay, world, maxDepth - 1))
        }
        return Colour(0.0, 0.0, 0.0)
    }
    val unitVector = r.direction.normalized()
    val t = 0.5 * (unitVector.y + 1)
    return (1 - t) * Colour(1.0, 1.0, 1.0) + t * Colour(0.5, 0.7, 1.0)
}

fun main() {
    try {
        val camera = Camera(Point3d(0.0, 0.0, 0.0), Point3d(0.0, 0.0, -1.0), Vector3d(0.0, 1.0, 0.0), 90, 16.0 / 9, 0.01, 1.0)
        val width = 1280
        val height = (width / camera.aspectRatio).toInt()
        val samplesCount = 1000
        val maxDepth = 50
        val world = EntityList()

        val filename = "result.ppm"

        val materialGround = Diffuse(Colour(0.8, 0.8, 0.0), 0.1)
        val materialCenter = Diffuse(Colour(0.1, 0.2, 0.5), 0.1)
        val materialLeft = Dielectric(Colour(1.0, 0.7, 0.7), 1.0, 1.5, 1.0)
        val materialRight = Metal(Colour(0.8, 0.6, 0.2), 0.3, 1.0, 0.0)
        world.add(Sphere(Point3d(0.0, 0.0, -1.0), 0.5, materialCenter))
        world.add(Sphere(Point3d(1.0, 0.0, -1.0), 0.5, materialRight))
        world.add(Sphere(Point3d(-1.0, 0.0, -1.0), 0.5, materialLeft))
//        world.add(Sphere(Point3d(-1.0, 0.0, -1.0), -0.45, materialLeft))
        world.add(Sphere(Point3d(0.0, -100.5, -1.0), 100.0, materialGround))

        val file = File(filename)
        file.writeText("P3\n$width $height\n255\n\n")
//        println("P3\n$width $height\n255\n")
        for (j in height - 1 downTo 0) {
            for (i in 0 until width) {
                var pixelColour = Colour(0.0, 0.0, 0.0)
                for (k in 0 until samplesCount) {
                    val u = (i.toDouble() + Random.nextDouble()) / (width - 1)
                    val v = (j.toDouble() + Random.nextDouble()) / (height - 1)
                    val r = camera.getRay(u, v)
                    pixelColour += colourRay(r, world, maxDepth)
                }
                file.appendText("${pixelColour.write(255, samplesCount)}\n")
//                println(pixelColour.write(255, samplesCount))
            }
            println("${round((height - 1 - j) * 10000.0 / height) / 100}%")
        }
    }
    catch (e : Exception) {
        e.printStackTrace()
        throw RuntimeException(e.message)
    }
}