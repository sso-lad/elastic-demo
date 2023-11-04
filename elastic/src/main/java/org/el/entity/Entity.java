package org.el.entity;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "entity_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entity implements Serializable {

    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Date,pattern = "yyyy-MM-dd HH:mm:ss")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", message = "日期格式必须为yyyy-MM-dd HH:mm:ss")
    private String date;
    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Integer)
    private Integer count;

    @Field(type = FieldType.Long)
    private Long value;

}
