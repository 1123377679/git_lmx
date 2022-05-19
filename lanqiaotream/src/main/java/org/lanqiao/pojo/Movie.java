package org.lanqiao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_movie")
public class Movie {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String title;
    private double Price;
    private Integer typeId;
    private String actor;
    private String img;
    private String descrption;
    private Integer del=0;//默认值
}
