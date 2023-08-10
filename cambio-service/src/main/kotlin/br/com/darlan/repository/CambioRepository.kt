package br.com.darlan.repository

import br.com.darlan.model.Cambio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CambioRepository : JpaRepository<Cambio, Long> {

    fun findByFromAndTo(from: String, to: String): Cambio?
}