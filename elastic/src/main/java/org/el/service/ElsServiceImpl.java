package org.el.service;


import co.elastic.clients.elasticsearch._types.aggregations.AggregateBuilders;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.el.entity.Entity;
import org.el.mapper.ElsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class ElsServiceImpl implements ElsService {

    @Autowired
    private ElsMapper elsMapper;


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Entity> getSometing(){
        List<Entity> entities = elsMapper.findEntitiesByDateBetween("2023-10-01 05:00:00", "2023-11-01 13:00:00");
        System.out.println(entities.size());
        return entities;
    }

    @Override
    public String save() {
        List<Entity> entities = new ArrayList<>();
        for(int i = 0;i<10000;i++){
            Entity entity = new Entity();
            entity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            LocalDate now = LocalDate.now();
            LocalDate localDate = now.minusDays(i);
            Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(date);
            entity.setDate(format);
            entity.setName(UUID.randomUUID().toString().replaceAll("-", "").substring(0,5));
            entity.setAge(i);
            entity.setCount(i);
            entity.setValue((long)(Math.random() * 100000));
            if(i<3000){
                entity.setType("btc");
            } else if (i<6000) {
                entity.setType("etc");
            } else {
                entity.setType("dash");
            }
            entities.add(entity);
        }
        elsMapper.saveAll(entities);
        return "success";
    }

    @Override
    public String findGroupTypByDateAndSum(String start, String end){
        return null;
    }

    @Override
    public String findDataByApi(){
        //根据类型分组
        //对数据分组后合并聚合
        Query date = Query.of(item -> item.range(r -> r.field("date")
                .gte(JsonData.of("2000-01-01 05:00:00"))
                .lte(JsonData.of("2023-11-01 13:00:00"))));
        Aggregation count = Aggregation.of(item -> item.sum(s -> s.field("count")));
        Aggregation type = Aggregation.of(item -> item.terms(v -> v.field("type")).aggregations("sum_count", count));
        NativeQuery build = NativeQuery.builder().withQuery(date).withAggregation("byType", type).build();
        SearchHits<Entity> search = elasticsearchTemplate.search(build, Entity.class);
        return search.getAggregations().toString();
    }
}
