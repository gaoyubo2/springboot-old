package cn.cestc.os.desktop.utils;

public class AjaxResult<T>
{

    public static final Integer SUCCESS = 1;
    public static final Integer FAILURE = 0;

    private Integer code = 1;

    private String msg;

    private T result;

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getResult()
    {
        return result;
    }

    public void setResult(T result)
    {
        this.result = result;
    }


}
