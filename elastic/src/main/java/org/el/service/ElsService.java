package org.el.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.el.entity.Entity;

import java.util.List;

public interface ElsService {

    public List<Entity> getSometing();

    public String save();

    String findGroupTypByDateAndSum(String start, String end) throws JsonProcessingException;

    String findDataByApi() throws JsonProcessingException;
}
