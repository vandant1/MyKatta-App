package com.genius.mykatta.repository;

import com.genius.mykatta.model.UpdatePost;
import com.genius.mykatta.model.enums.UpdateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UpdatePostRepository extends JpaRepository<UpdatePost, Integer> {

    Page<UpdatePost> findBySenderId(Integer senderId, Pageable pageable);

    Page<UpdatePost> findByUpdateType(UpdateType updateType, Pageable pageable);

    List<UpdatePost> findByIsPinnedTrue();

    @Query("SELECT u FROM UpdatePost u WHERE u.parent IS NULL ORDER BY u.createdAt DESC")
    Page<UpdatePost> findTopLevelUpdates(Pageable pageable);

    @Query("SELECT u FROM UpdatePost u WHERE u.parent.id = :parentId ORDER BY u.createdAt ASC")
    List<UpdatePost> findRepliesByParentId(@Param("parentId") Integer parentId);

    @Query("SELECT u FROM UpdatePost u WHERE u.updateType = 'ANNOUNCEMENT' AND u.isPinned = true AND u.createdAt >= :sinceDate")
    List<UpdatePost> findRecentPinnedAnnouncements(@Param("sinceDate") LocalDateTime sinceDate);
}
