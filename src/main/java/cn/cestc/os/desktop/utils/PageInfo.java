package cn.cestc.os.desktop.utils;

import java.util.List;

/**
 * <p>简单的分页信息</p>
 *
 * @author BFD_73
 */
public class PageInfo<T>
{

    private List<T> lists;            //集合
    private int totalCount;           //总行数
    private int pageSize = 10;       //每页数目 默认10
    private int currentPage;       //当前页

    public int getTotalPage()
    {
        if (pageSize == 0)
        {
            pageSize = 10;
        }
        if (this.totalCount % this.pageSize == 0)
        {
            return (this.totalCount / this.pageSize);
        }
        return (this.totalCount / this.pageSize + 1);
    }

    public List<T> getLists()
    {
        return lists;
    }

    public void setLists(List<T> lists)
    {
        this.lists = lists;
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getCurrentPage()
    {
        if (currentPage <= 0)
        {
            this.currentPage = 1;
        }
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }

}
