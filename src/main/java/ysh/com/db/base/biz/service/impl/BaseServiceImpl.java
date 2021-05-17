package ysh.com.db.base.biz.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import ysh.com.db.base.biz.dao.BaseDao;
import ysh.com.db.base.biz.entity.BaseEntity;
import ysh.com.db.base.biz.service.BaseService;

/**
 * @author yangsh
 * @since 2021/4/16 11:09 上午
 */
@Transactional
public class BaseServiceImpl<T extends BaseEntity> extends ServiceImpl<BaseDao<T>, T> implements BaseService<T> {
    @Override
    public T searchOne(T t) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<T> search(T t) {

        QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
        return this.list(queryWrapper);
    }

    @Override
    public boolean delete(T t) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
        return this.remove(queryWrapper);
    }
}
