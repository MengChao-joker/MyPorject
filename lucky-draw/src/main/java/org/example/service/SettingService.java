package org.example.service;

import org.example.mapper.AwardMapper;
import org.example.mapper.MemberMapper;
import org.example.mapper.SettingMapper;
import org.example.model.Award;
import org.example.model.Member;
import org.example.model.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {
    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private AwardMapper awardMapper;

    @Autowired
    private MemberMapper memberMapper;

    public Setting query(Integer id) {
        //通过用户id查找setting
        Setting setting = settingMapper.queryById(id);

        //查询对应的Award，member
        List<Award> awards = awardMapper.queryById(setting.getId());
        setting.setAwards(awards);
        List<Member> members = memberMapper.queryById(setting.getId());
        setting.setMembers(members);
        return setting;
    }

    public void update(Setting setting) {
        settingMapper.updateByPrimaryKeySelective(setting);
    }

}
