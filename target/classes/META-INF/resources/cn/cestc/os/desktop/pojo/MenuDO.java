package cn.cestc.os.desktop.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @ClassName MenuDO
 * @Author CYY
 * Date 2021/12/16 11:11
 * @Version 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDO
{

    /**
     * 权限菜单ID
     */
    private Integer id;

    /**
     * 权限菜单名称
     */
    private String menuName;

    /**
     * 权限菜单父ID
     */
    private Integer menuParentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除,0否,1是
     */
    private Integer delFlag;

    /*
     * 树状
     * */
    private List<MenuDO> resourceList;


    public MenuDO(List<MenuDO> resourceList)
    {
        this.resourceList = resourceList;
    }
}
