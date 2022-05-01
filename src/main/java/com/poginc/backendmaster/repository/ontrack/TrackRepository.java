package com.poginc.backendmaster.repository.ontrack;

import com.poginc.backendmaster.entity.ontrack.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

public interface TrackRepository extends JpaRepository <Track, Long> {
    /*Return last N rows of tracked records for given chat id*/
    @Nullable
    List<Track> findByrefchatid(long refchatid);

    @Nullable
    List<Track> findTop30Byrefchatid(long refchatid);

    @Query(value = "SELECT pid FROM ot_track WHERE ref_chat_id = :refchatid AND MONTH(t_date) = MONTH(CURRENT_DATE()) ORDER BY pid DESC", nativeQuery = true)
    List<Integer> getLatestPid(@Param(value = "refchatid") long refchatid);

    Track findBypid(int pid);

    //For easy map generation, selected Key (Identifying column) first then Value (amount column)
    @Query(value = "SELECT cat, sum(amount) FROM ot_track WHERE track_type = 'expense' AND ref_chat_id = :refchatid AND MONTH(t_date) = :month AND YEAR(t_date) = :year GROUP BY cat", nativeQuery = true)
    List<String> getMonthlyExpenseByrefchatid(@Param(value = "refchatid") long refchatid, @Param(value = "month") int month, @Param(value = "year") int year);

    @Query(value = "SELECT track_type, sum(amount) FROM ot_track WHERE ref_chat_id = :refchatid AND MONTH(t_date) = :month AND YEAR(t_date) = :year GROUP BY track_type", nativeQuery = true)
    List<String> getMonthlyOverviewByrefchatid(@Param(value = "refchatid") long refchatid, @Param(value = "month") int month, @Param(value = "year") int year);


}
