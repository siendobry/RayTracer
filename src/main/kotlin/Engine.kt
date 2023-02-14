import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.random.Random


class Engine(
    private val width : Int,
    private val height : Int,
    private val pixelWidth : Int,
    private val pixelHeight : Int,
    private val samplesPerPixel : Int,
    private val maxRecurDepth : Int
) {

    private val world = EntityList()

    fun colourRay(r : Ray, world : EntityList, lightSources : ArrayList<LightSource>, maxDepth : Int) : Colour {
        if (maxDepth <= 0) {
            return Colour(0.0, 0.0, 0.0)
        }
        val closestHit = world.getClosestHit(r, 0.001, Double.MAX_VALUE)
        if (closestHit != null) {
            var finalColour = Colour(0.0, 0.0, 0.0)
            for (lightSource in lightSources) {
                finalColour = finalColour + lightSource.enlighten(closestHit, world)
            }
            val scatteredRay = Ray()
            if (closestHit.material.scatter(closestHit, scatteredRay)) {
                finalColour = finalColour + mix(closestHit.material.albedo, colourRay(scatteredRay, world, lightSources, maxDepth - 1))
            }
            return finalColour.getClamped()
        }
        return Colour(0.0, 0.0, 0.0)
//    val unitVector = r.direction.normalized()
//    val t = 0.5 * (unitVector.y + 1)
//    return (1 - t) * Colour(1.0, 1.0, 1.0) + t * Colour(0.5, 0.7, 1.0)
    }

    @Synchronized fun addColour(colours : ArrayList<Colour>, idx : Int, colour : Colour) {
        colours[idx] = colours[idx] + colour
    }

    fun run() {
        try {
            val imageWidth = width / pixelWidth
            val imageHeight = height / pixelHeight

            val camera = Camera(Point3d(-1.5, 0.75, 1.0), Point3d(-0.3, 0.0, -1.0), Vector3d(0.0, 1.0, 0.0), 50, imageWidth / imageHeight.toDouble(), 0.0, 1.0)
            val lightSources = ArrayList<LightSource>()

            val filename = "result.ppm"

            val materialGround = Diffuse(Colour(0.8, 0.8, 0.0))
            val materialCenter = Diffuse(Colour(0.1, 0.2, 0.5))
            val materialLeft = Dielectric(Colour(1.0, 0.7, 0.7), 1.5)
            val materialRight = Metal(Colour(0.8, 0.6, 0.2), 1.0)
            val metal = Metal(Colour(0.8, 0.8, 0.8), 1.0)
//        world.add(Sphere(Point3d(0.0, 0.0, -1.0), 0.5, materialCenter))

            // metal cube
            world.add(Triangle(Point3d(-0.5, -0.5, -0.5), Point3d(-0.5, 0.5, -0.5), Point3d(0.5, -0.5, -0.5), metal))
            world.add(Triangle(Point3d(0.5, -0.5, -0.5), Point3d(-0.5, 0.5, -0.5), Point3d(0.5, 0.5, -0.5), metal))
            world.add(Triangle(Point3d(-0.5, 0.5, -0.5), Point3d(-0.5, 0.5, -1.5), Point3d(0.5, 0.5, -0.5), metal))
            world.add(Triangle(Point3d(0.5, 0.5, -0.5), Point3d(-0.5, 0.5, -1.5), Point3d(0.5, 0.5, -1.5), metal))
            world.add(Triangle(Point3d(-0.5, -0.5, -1.5), Point3d(-0.5, 0.5, -1.5), Point3d(-0.5, -0.5, -0.5), metal))
            world.add(Triangle(Point3d(-0.5, -0.5, -0.5), Point3d(-0.5, 0.5, -1.5), Point3d(-0.5, 0.5, -0.5), metal))
            world.add(Triangle(Point3d(0.5, -0.5, -0.5), Point3d(0.5, 0.5, -0.5), Point3d(0.5, -0.5, -1.5), metal))
            world.add(Triangle(Point3d(0.5, -0.5, -1.5), Point3d(0.5, 0.5, -0.5), Point3d(0.5, 0.5, -1.5), metal))
            world.add(Triangle(Point3d(0.5, -0.5, -1.5), Point3d(0.5, 0.5, -1.5), Point3d(-0.5, -0.5, -1.5), metal))
            world.add(Triangle(Point3d(-0.5, -0.5, -1.5), Point3d(0.5, 0.5, -1.5), Point3d(-0.5, 0.5, -1.5), metal))

            world.add(Sphere(Point3d(1.0, 0.0, -1.0), 0.5, materialCenter))
            world.add(Sphere(Point3d(-1.0, 0.0, -1.0), 0.5, materialLeft))
            world.add(Sphere(Point3d(-1.0, 0.0, -1.0), -0.45, materialLeft))
            world.add(Sphere(Point3d(0.0, -100.5, -1.0), 100.0, materialGround))
            world.add(Sphere(Point3d(0.0, 0.0, 0.0), 0.25, metal))
//        world.add(Plane(Point3d(0.0, -0.5, -1.0), Vector3d(0.0, -1.0, 0.0), materialGround))

            lightSources.add(LightSource(Point3d(3.0, 10.0, 5.0), Colour(1.0, 1.0, 1.0)))
//        lightSources.add(LightSource(Point3d(0.0, 10.0, -3.0), Colour(1.0, 1.0, 1.0)))

            val file = File(filename)


//        file.writeText("P3\n$width $height\n255\n\n")
////        println("P3\n$width $height\n255\n")
//        for (j in height - 1 downTo 0) {
//            for (i in 0 until width) {
//                val pixelColour = Colour(0.0, 0.0, 0.0)
//                for (k in 0 until samplesCount) {
//                    val u = (i.toDouble() + Random.nextDouble()) / (width - 1)
//                    val v = (j.toDouble() + Random.nextDouble()) / (height - 1)
//                    val r = camera.getRay(u, v)
//                    pixelColour += colourRay(r, world, lightSources, maxDepth)
//                }
//                file.appendText("${pixelColour.write(255, samplesCount)}\n")
////                println(pixelColour.write(255, samplesCount))
//            }
//            println("${round((height - 1 - j) * 10000.0 / height) / 100}%")
//        }


            val pixels = ArrayList<Colour>()
            for (j in imageHeight - 1 downTo 0) {
                for (i in 0 until imageWidth) {
                    pixels.add(Colour(0.0, 0.0, 0.0))
                }
            }
            val executor = Executors.newCachedThreadPool()
            val tasks = ArrayList<Future<*>>()

            file.writeText("P3\n$imageWidth $imageHeight\n255\n\n")
            for (k in 0 until samplesPerPixel) {
                tasks.add(executor.submit {
                    for (j in imageHeight - 1 downTo 0) {
                        for (i in 0 until imageWidth) {
                            val u = (i.toDouble() + Random.nextDouble()) / (imageWidth - 1)
                            val v = (j.toDouble() + Random.nextDouble()) / (imageHeight - 1)
                            val r = camera.getRay(u, v)
                            // atomic task
                            addColour(pixels, (imageHeight - 1 - j) * imageWidth + i, colourRay(r, world, lightSources, maxRecurDepth))
                        }
                    }
                })
            }
            // wait for all threads to end
            tasks.forEach {
                it.get()
//                println("Done")
            }
            executor.shutdown()
            pixels.forEach { file.appendText("${it.write(255, samplesPerPixel)}\n") }
        }
        catch (e : Exception) {
            e.printStackTrace()
            throw RuntimeException(e.message)
        }
    }

}