package org.el.mapper;

import org.el.entity.Entity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ElsMapper extends ElasticsearchRepository<Entity,String> {
    public List<Entity> findEntitiesByDateBetween(String start ,String end);
}
