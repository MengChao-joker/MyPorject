package org.example.service;

import org.example.mapper.AwardMapper;
import org.example.model.Award;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AwardService {
    @Autowired
    private AwardMapper awardMapper;
    public int add(Award award) {
        return awardMapper.insertSelective(award);
    }

    public int delete(Integer id) {
        return awardMapper.deleteByPrimaryKey(id);

    }
}
