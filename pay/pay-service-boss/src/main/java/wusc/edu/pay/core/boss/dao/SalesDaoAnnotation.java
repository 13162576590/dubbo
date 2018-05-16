package wusc.edu.pay.core.boss.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import wusc.edu.pay.facade.boss.entity.Sales;

/**
 *类描述：
 *@author: chenyouhong MyBaits注解测试
 */
public interface SalesDaoAnnotation {

  //使用@Insert注解指明add方法要执行的SQL (VERSION, CREATETIME, SALESNO, SALESNAME, MOBILE, STATUS, DESCRIPTION)
//    values (0, #{createTime}, #{salesNo}, #{salesName}, #{mobile}, #{status}, #{description})
    @Insert("INSERT INTO TBL_BOSS_SALES1(VERSION, CREATETIME, SALESNO, SALESNAME, MOBILE, STATUS, DESCRIPTION) VALUES(0, #{createTime}, #{salesNo}, #{salesName}, #{mobile}, #{status}, #{description})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    long add(Sales sales);


}
