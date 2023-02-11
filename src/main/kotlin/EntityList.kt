data class EntityList(
    val entities : ArrayList<Entity> = ArrayList()
) {

    fun add(entity : Entity) {
        entities.add(entity)
    }

    fun getClosestHit(ray : Ray) : HitRecord? {
        var closestHit : HitRecord? = null
        for (entity in entities) {
            val hitRecord = HitRecord(material = entity.material)
            val hit = entity.hit(ray, hitRecord, 0.001, Double.MAX_VALUE)
            if (hit && (closestHit == null || hitRecord.t < closestHit.t)) {
                closestHit = hitRecord
            }
        }
        return closestHit
    }

}