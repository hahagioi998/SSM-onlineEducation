package com.online.college.common.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.online.college.common.util.BeanUtil;

public abstract class AbstractPage<E> implements Page<E> {

    public static final int DEFAULT_FIRST_PAGE_NUM = 1;//第一页
    public static final int DEFAULT_PAGE_SIZE = 10;//每页10条记录

    //赋值
    protected int pageSize = DEFAULT_PAGE_SIZE;
    protected int pageNum = DEFAULT_FIRST_PAGE_NUM;

    protected int itemsTotalCount = 0;//总记录数
    protected int pageTotalCount = 0;//总页数
    protected List<E> items; //每页数据
    protected boolean firstPage;//是否是第一页
    protected boolean lastPage;//是否是最后一页
    protected int startIndex;//开始页数

    private String sortField="update_time";//排序
    private String sortDirection = "DESC";//排序方向

    /**
     * 返回为第一页
     * @return
     */
    @Override
    public int getFirstPageNum() {
        return DEFAULT_FIRST_PAGE_NUM;
    }

    /**
     * 返回分页大小
     * @return
     */
    @Override
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置分页大小
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 返回当页页号
     * @return
     */
    @Override
    public int getPageNum() {
        return pageNum;
    }

    /**
     * 设置当页页号
     * @param pageNum
     */
    public void setPageNum(int pageNum) {
        if (pageNum < DEFAULT_FIRST_PAGE_NUM) pageNum = DEFAULT_FIRST_PAGE_NUM;
        this.pageNum = pageNum;
    }

    /**
     * 返回每页数据
     * @return
     */
    @Override
    public List<E> getItems() {
        return items;
    }

    /**
     * 设置每页数据，根据传入的集合
     * @param items
     */
    public void setItems(Collection<E> items) {
        if (items == null) items = Collections.emptyList();//如果为空传入一个空的list集合
        this.items = new ArrayList<E>(items);//新建list集合传入数据
        this.lastPage = this.pageNum == this.pageTotalCount;//最后一页
        this.firstPage = this.pageNum == DEFAULT_FIRST_PAGE_NUM;//第一页
    }

    /**
     * 判断是否为首页
     * @return
     */
    @Override
    public boolean isFirstPage() {
        firstPage = (getPageNum() <= getFirstPageNum());
        return firstPage;
    }

    /**
     * 判断是否为最后一页
     * @return
     */
    @Override
    public boolean isLastPage() {
        return lastPage;
    }

    /**
     *获取前一页页码
     * @return
     */
    public int getPrePageNum() {
        return isFirstPage() ? getFirstPageNum() : getPageNum() - 1;
    }

    /**
     * 获取下一页页码
     * @return
     */
    public int getNextPageNum() {
        return isLastPage() ? getPageNum() : getPageNum() + 1;
    }

    /**
     * 每页的集合数据
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return this.items.iterator();
    }

    /**
     * 每页数据是否为空
     * @return
     */
    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * 设置总记录数
     * @param itemsTotalCount
     */
    public void setItemsTotalCount(int itemsTotalCount) {
        this.itemsTotalCount = itemsTotalCount;//总记录数
        if(itemsTotalCount % this.pageSize == 0){
            this.pageTotalCount = itemsTotalCount/this.pageSize;
        }else{
            this.pageTotalCount = itemsTotalCount/this.pageSize + 1;
        }
        if(this.pageNum > this.pageTotalCount){
            this.pageNum = DEFAULT_FIRST_PAGE_NUM;
        }
        if(this.itemsTotalCount <= this.pageSize){
            this.firstPage = true;
            this.lastPage = true;
        }
    }

    /**
     * 获取总记录数
     * @return
     */
    @Override
    public int getItemsTotalCount() {
        return itemsTotalCount;
    }

    /**
     * 获取最后一页号码
     * @return
     */
    @Override
    public int getLastPageNum() {
        return itemsTotalCount;
    }

    /**
     * 获取开始页数
     * @return
     */
    public int getStartIndex() {
        this.startIndex = (this.pageNum - 1) * this.pageSize;
        if(this.startIndex <= 0){
            this.startIndex = 0;
        }
        return this.startIndex;
    }

    /**
     * 按照sortField升序
     * @param sortField：指java bean中的属性
     */
    public void ascSortField(String sortField) {
        if(StringUtils.isNotEmpty(sortField)){
            this.sortField = BeanUtil.fieldToColumn(sortField);
            this.sortDirection = " ASC ";
        }
    }

    /**
     * 按照sortField降序
     * @param sortField ：指java bean中的属性
     */
    public void descSortField(String sortField) {
        if(StringUtils.isNotEmpty(sortField)){
            this.sortField = BeanUtil.fieldToColumn(sortField);
            this.sortDirection = " DESC ";
        }
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    @Override
    public String toString() {
        return "Page[" + this.getPageNum() + "]:" + items.toString();
    }


}
