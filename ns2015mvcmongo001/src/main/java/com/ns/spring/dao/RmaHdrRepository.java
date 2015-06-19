package com.ns.spring.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;
import com.ns.spring.model.RMA_HDR;

 
@Repository
public interface RmaHdrRepository extends MongoRepository<RMA_HDR, String>{
	
	@Query("{rma_num : ?0}")
	List<RMA_HDR> findByRma_num(String rma_num);

	@Query("{rma_hdr_sts_cd : ?0}")
	List<RMA_HDR> findByRma_hdr_sts_cd(String findByRma_hdr_sts_cd);
	
	@Query("{'rma_hdr_sts_cd' : {$ne : ?0}}")
	List<RMA_HDR> findByRma_exclude(String findByRma_hdr_sts_cd);

}
