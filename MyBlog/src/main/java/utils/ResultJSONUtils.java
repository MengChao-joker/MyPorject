package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ResultJSONUtils {
    public static void resp(HttpServletResponse response, Map<String,Object> map) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("application/json");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.println(mapper.writeValueAsString(map));
    }
}
