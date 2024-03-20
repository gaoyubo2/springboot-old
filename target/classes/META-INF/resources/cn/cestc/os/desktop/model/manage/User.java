package cn.cestc.os.desktop.model.manage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer tbid;

    private String username;

    private String password;

    private Integer roleId;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;
}
