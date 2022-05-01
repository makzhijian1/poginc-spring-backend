package com.poginc.backendmaster.repository.ontrack;

import com.poginc.backendmaster.entity.ontrack.UserHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface UserHistRepository extends JpaRepository <UserHist, Long>{

    @Nullable
    UserHist findBychatid(long chatid);

}