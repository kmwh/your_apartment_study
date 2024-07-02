package com.yamuzinfriends.yourapartment.repositories;

import com.yamuzinfriends.yourapartment.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
