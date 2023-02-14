data class EntityList(
    val entities : ArrayList<Entity> = ArrayList()
) {

    fun add(entity : Entity) {
        entities.add(entity)
    }

    fun remove(entity : Entity) {
        entities.remove(entity)
    }

    fun getClosestHit(ray : Ray, minT : Double, maxT : Double) : HitRecord? {
        var closestHit : HitRecord? = null
        for (entity in entities) {
            val hitRecord = HitRecord(material = entity.material)
            val hit = entity.hit(ray, hitRecord, minT, maxT)
            if (hit && (closestHit == null || hitRecord.t < closestHit.t)) {
                closestHit = hitRecord
            }
        }
        return closestHit
    }

}