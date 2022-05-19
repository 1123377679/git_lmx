package org.lanqiao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_user")
public class User {
  @TableId(value = "id", type = IdType.AUTO)
  private String id;
  @TableField(value = "userName")
  private String userName;
  private String password;
  private String state;


}
