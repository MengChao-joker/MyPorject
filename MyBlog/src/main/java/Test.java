import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        System.out.println(true?3:4);
    }
    public static void main1(String[] args) throws JsonProcessingException {
        Map<String,Object> map = new HashMap<>();
        map.put("msg","操作成功");
        map.put("state",200);
        List<UserInfo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setPassword("test"+i);
            userInfo.setUsename("test"+i);
            list.add(userInfo);
        }
        //Jackson的使用
        //创建jackson对象
        ObjectMapper mapper = new ObjectMapper();
        String res = mapper.writeValueAsString(list);
        System.out.println(res);
    }
}
