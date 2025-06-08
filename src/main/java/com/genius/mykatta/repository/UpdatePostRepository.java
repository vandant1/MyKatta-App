package com.genius.mykatta.repository;

import java.util.List;

import com.genius.mykatta.model.enums.UpdateType;

public interface UpdatePostRepository extends JpaRepository<UpdatePost, Integer> {

    Page<UpdatePost> findBySenderId(Integer senderId, Pageable pageable);

    Page<UpdatePost> findByUpdateType(UpdateType updateType, Pageable pageable);

    List<UpdatePost> findByIsPinnedTrue();

    @Query("SELECT u FROM UpdatePost u WHERE u.parent IS NULL ORDER BY u.createdAt DESC")
    Page<UpdatePost> findTopLevelUpdates(Pageable pageable);

    @Query("SELECT u FROM UpdatePost u WHERE u.parent.id = :parentId ORDER BY u.createdAt ASC")
    List<UpdatePost> findRepliesByParentId(Integer parentId);

    @Query("SELECT u FROM UpdatePost u WHERE " +
           "u.updateType = 'ANNOUNCEMENT' AND " +
           "u.isPinned = true AND " +
           "u.createdAt >= CURRENT_DATE - 7")
    List<UpdatePost> findRecentPinnedAnnouncements();
}