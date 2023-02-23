package com.goel_ujjwal.greatreads.userbooks;

import org.springframework.data.cassandra.repository.CassandraRepository;

// Repository class to read/write "book_by_userid_and_bookid" table

public interface UserBooksRepository extends CassandraRepository<UserBooks, UserBooksPrimaryKey> {
    
}
