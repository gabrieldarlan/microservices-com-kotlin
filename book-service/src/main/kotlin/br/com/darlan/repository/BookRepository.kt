package br.com.darlan.repository

import br.com.darlan.model.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long?> {
}