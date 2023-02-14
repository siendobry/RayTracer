abstract class Entity(
    val material : Material
) {

    companion object {
        fun parse(data : String) : Entity {
            val (entity, params) = data.split(":")
            entity.lowercase().trim()
            return when (entity) {
                "sphere" -> Sphere.parse(params)
                "plane" -> Plane.parse(params)
                "triangle" -> Triangle.parse(params)
                else -> throw IllegalArgumentException("Incorrect entity type")
            }
        }
    }

    abstract fun hit(r : Ray, hitRecord: HitRecord, minT : Double, maxT : Double) : Boolean

}