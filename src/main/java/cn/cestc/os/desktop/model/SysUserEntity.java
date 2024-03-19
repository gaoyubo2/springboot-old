package cn.cestc.os.desktop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统用户
 *
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserEntity implements Serializable {

    /**
     * 用户ID
     */
    public Integer userId;

    /**
     * 用户名
     */
    public String username;


    public String name;


}
