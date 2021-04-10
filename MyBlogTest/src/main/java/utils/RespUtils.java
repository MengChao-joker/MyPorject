package utils;



import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class RespUtils {
    public static void Resp(HttpServletResponse response, HashMap<String,Object> resp) throws IOException {
        //返回结果给后端
        response.setCharacterEncoding("utf-8");
        response.setContentType("applicant/json");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.println(mapper.writeValueAsString(resp));
    }
}
