package ysh.com.db.base.biz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import ysh.com.db.base.biz.entity.BaseEntity;

/**
 * @author yangsh
 * @since 2021/4/16 11:03 上午
 */
public interface BaseDao<T extends BaseEntity> extends BaseMapper<T> {}
