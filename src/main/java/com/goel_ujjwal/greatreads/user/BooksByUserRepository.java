package com.goel_ujjwal.greatreads.user;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

// Repository class to read/write "books_by_userid" table

public interface BooksByUserRepository extends CassandraRepository<BooksByUser, String> {

    // 'Slice' is basically a bunch of BooksByUser objects
    // 'Pageable' is used to limit the number of rows fetched from the Cassandra database
    Slice<BooksByUser> findAllById(String id, Pageable pageable);
        
}
