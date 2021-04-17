package org.example.models;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MV {
    //MV表模型
    private int id;
    private String title;
    private String singer;
    private String upload_time;
    private String url;
    private int uid;
}
