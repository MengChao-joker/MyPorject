package org.example.service;


import org.example.exception.AppException;
import org.example.mapper.SettingMapper;
import org.example.mapper.UserMapper;
import org.example.model.Setting;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SettingMapper settingMapper;
    @Value("${user.head.local-path}")
    private String headLocalPath;

    @Value("${user.head.remote-path}")
    private String headRemotePath;
    public User queryByUsername(String username) {
        return userMapper.queryByUsername(username);
    }

    @Transactional//开启事务
    public void register(User user, MultipartFile headFile) {
        try {
            if(headFile!=null) {
                //存储到本地唯一目录下
                String uuid = UUID.randomUUID().toString();
                //相对路径
                String uri = "/" + uuid +"/"+ headFile.getOriginalFilename();
                //绝对路径
                String localPath = headLocalPath + uri;
                File head = new File(localPath);
                //判断上级目录是否存在，不存在则创建
                File parent = head.getParentFile();
                if(!parent.exists()) parent.mkdirs();
                headFile.transferTo(head);
                user.setHead(headRemotePath+uri);
            }
            //插入新用户
            userMapper.insertSelective(user);
            //初始化setting表
            Setting setting = new Setting();
            setting.setUserId(user.getId());
            setting.setBatchNumber(8);
            settingMapper.insertSelective(setting);
        } catch (IOException e) {
            throw new AppException("URR111", "头像插入异常",e);
        }
    }
}
