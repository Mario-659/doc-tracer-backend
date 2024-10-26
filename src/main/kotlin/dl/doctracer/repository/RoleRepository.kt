package dl.doctracer.repository

import dl.doctracer.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Int> {
    fun findByRoleName(roleName: String): Role?
}
