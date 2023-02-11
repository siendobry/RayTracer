abstract class Entity(
    val material : Material
) {

    abstract fun hit(r : Ray, hitRecord: HitRecord, minT : Double, maxT : Double) : Boolean

}