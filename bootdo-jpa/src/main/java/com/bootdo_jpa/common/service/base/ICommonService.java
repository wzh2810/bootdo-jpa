package com.bootdo_jpa.common.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.bootdo_jpa.common.utils.PageUtils;

public interface ICommonService<T> {
	
	Example<T> listExample(Map<String, Object> params, Class<T> beanClass) throws Exception ;
	
	Sort listSort(Map<String, Object> params, String sortField);
	
	Pageable listPageable(Map<String, Object> params, Sort sort);
	
	Pageable listPageable(Map<String, Object> params, String sortField);

	/**
     * 查询全部
     * @return
     */
     List<T> findAll();
     
     List<T> findAllById(List<Long> ids);
     
     List<T> findAll(Specification<T> spec);
     
     List<T> findAll(Example<T> example);
     
     List<T> findAll(Map<String, Object> params, Class<T> beanClass) throws Exception;
     
     List<T> findAll(Sort sort);
     
     Page<T> findAll(Pageable pageable);
     
     Page<T> findAll(Specification<T> spec, Pageable pageable);
     
     Page<T> findAll(Example<T> example, Pageable pageable);
     
     Page<T> findAll(Map<String, Object> params, Class<T> beanClass, String sortField) throws Exception;
     
     PageUtils findAllByPage(Map<String, Object> params, Class<T> beanClass, String sortField) throws Exception;
     
     List<T> findAll(Specification<T> spec, Sort sort);
     
     List<T> findAll(Example<T> example, Sort sort);
     
     long count(Specification<T> spec);
     
     long count(Example<T> example);

     /**
      * 分页查询
      * @param pageNum
      * @param pageSize
      * @return
     */
     Page<T> find(int pageNum, int pageSize);

     /**
      * 根据ID查询
      * @param id
      * @return
     */
     T findById(Long id);
     
     boolean existsById(Long id);
     
     T findOne(Specification<T> spec);
     
     T findOne(Example<T> example);
     
     void deleteAll(List<T> list);

     /**
      * 根据ID删除
      * @param id
      */
     void deleteById(Long id);
     
     void deleteByIds(Long... ids);
     
     void delete(T t);


     /**
      * 新增和修改(根据ID来判断)
      * @param t
      * @return
     */
     T save(T t);
     
     List<T> saveAll(List<T> list);

}
