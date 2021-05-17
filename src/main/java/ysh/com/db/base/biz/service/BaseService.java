package ysh.com.db.base.biz.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import ysh.com.db.base.biz.entity.BaseEntity;

/**
 * @author yangsh
 * @since 2021/4/16 11:08 上午
 */
public interface BaseService<T extends BaseEntity> extends IService<T> {

    /**
     * 查一个数据库对象
     *
     * @param t
     *            查询条件
     * @return 返回值
     */
    T searchOne(T t);

    /**
     * 查出对象集合
     *
     * @param t
     *            查询条件
     * @return 返回值
     */
    List<T> search(T t);

    /**
     *
     * 删除对象
     * 
     * @param t
     *            对象
     * @return
     */
    boolean delete(T t);
}
