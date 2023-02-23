package com.goel_ujjwal.greatreads.book;

import org.springframework.data.cassandra.repository.CassandraRepository;

// Repository class to read/write "book_by_id" table

public interface BookRepository extends CassandraRepository<Book, String> {
    
}
