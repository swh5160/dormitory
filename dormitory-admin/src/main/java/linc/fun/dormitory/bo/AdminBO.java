package linc.fun.dormitory.bo;

import linc.fun.dormitory.po.Admin;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yqlin
 * @date 2022/4/14 22:16
 * @description
 * query就是写接口接收数据对象的时候用到的类
 * bo是用来封装对象的，他可以有多个vo的集合，可以包含list类型的vo的抽象实体类
 * po对应数据库表字段
 * vo是在代码流程中处理的数据，一般与po相似 但不相同，一般从数据库中查出来的数据是po类型的，接口返回的时候是vo类型的，最后返回之前会有po转vo的操作
 * dto跟query一样，是传输过程中用到的类
 * dao用来封装对数据库的访问，通常结合po一起对数据库进行操作
 * pojo是简单的java对象，它包含 po vo dto query bo dao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminBO extends Admin {
}
