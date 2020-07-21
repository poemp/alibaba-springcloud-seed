package org.poem.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author sangfor
 */

@ApiModel(description = "分页结果")
public class PageableResult<T> {

    /**
     * 总数
     */
    @ApiModelProperty("总数")
    private long total;
    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty("每页显示条数")
    private long size;
    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private long current;

    @ApiModelProperty("数据")
    private List<T> voList;

    public PageableResult() {
    }

    public PageableResult(long total, long size, long current, List<T> voList) {
        this.total = total;
        this.size = size;
        this.current = current;
        this.voList = voList;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public List<T> getVoList() {
        return voList;
    }

    public void setVoList(List<T> voList) {
        this.voList = voList;
    }
}