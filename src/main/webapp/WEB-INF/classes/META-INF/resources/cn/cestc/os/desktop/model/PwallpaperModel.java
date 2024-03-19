package cn.cestc.os.desktop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description:tb_pwallpaper表
 *
 * @author bo.xu
 * 2015年7月20日 下午2:28:16
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PwallpaperModel implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Integer tbid;//主键

    private String url;//壁纸地址

    private Integer width;//壁纸宽度

    private Integer height;//壁纸高度

    private Integer memberId;//用户id

}