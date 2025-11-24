package com.wewine.wewine.Repository;

import com.wewine.wewine.Entity.VinhoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VinhoRepository extends JpaRepository<VinhoEntity, Long> {
}
