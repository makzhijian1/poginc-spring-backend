package com.poginc.backendmaster.repository.ontrack;

import com.poginc.backendmaster.entity.ontrack.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

public interface UserRepository extends JpaRepository <User, Long>{

    @Nullable
    User findBychatid(long chatid);

}
