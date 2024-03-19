package cn.cestc.os.desktop.service.impl;

import cn.cestc.os.desktop.pojo.SkinVO;
import cn.cestc.os.desktop.service.SkinService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class SkinServiceImpl implements SkinService
{


    public List<SkinVO> selectAllSkinVOs(File dir)
    {
        List<SkinVO> skinlist = new ArrayList<SkinVO>();
        //查询dir下面第一层的所有文件
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++)
        {
            if (!fs[i].isDirectory())
            {
                String[] namesplit = fs[i].getName().split("\\.");
                //得到所有css文件格局约定将所有皮肤信息得到
                if (namesplit[1].toLowerCase().equals("css"))
                {
                    SkinVO skinVO = new SkinVO();
                    skinVO.setName(namesplit[0]);
                    skinVO.setImg("static/img/skins/" + namesplit[0] + "/preview.png");
                    skinlist.add(skinVO);
                }
            }
        }
        return skinlist;
    }

}
