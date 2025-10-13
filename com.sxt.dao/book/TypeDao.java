package book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sxt.web.servlet.entity.TypeDB;
import com.sxt.web.servlet.entity.UserDB;

import utils.C3p0Tool;
import utils.PageTool;
//图书分类
public class TypeDao {
	private QueryRunner queryRunner = new QueryRunner(C3p0Tool.getDataSource());
	   //开启驼峰自动转换
	  BeanProcessor bean=new GenerousBeanProcessor();
	 RowProcessor processor=new BasicRowProcessor(bean);
	 //分页查询
	 public PageTool<TypeDB>listByPage(String currentPage,String pageSize){
	    	
	    	try {
	    		StringBuffer listSql=new StringBuffer("select * ");
	    		StringBuffer countSql=new StringBuffer("select count(*)");
	    		StringBuffer sql=new StringBuffer("from t_type");
	    		//获取总记录数
	    		Long total=queryRunner.query(countSql.append(sql).toString(), new ScalarHandler<Long>());
	    		//初始化分页工具
	    		PageTool<TypeDB> pageTools=new PageTool<TypeDB>(total.intValue(),currentPage,pageSize);
	    		sql.append(" limit ?,?");
	    		//当前页的数据
	    		List<TypeDB>list=queryRunner.query(listSql.append(sql).toString(), new BeanListHandler<TypeDB>(TypeDB.class,processor),pageTools.getStartIndex(),pageTools.getPageSize());
	    	 pageTools.setRows(list);
	    	 System.out.println(pageTools);
	    	 return pageTools;
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
			return new PageTool<TypeDB>(0,currentPage,pageSize);
	    	}
	 //多条件查询
	 public List<TypeDB>list(String tid,String typeName){
		 StringBuffer sql=new StringBuffer("select * from t_type where 1=1 ");
		 List<Object>params=new ArrayList<Object>();
		 if(tid!=null&&tid!="") {
			 sql.append("and tid=? ");
			 params.add(tid);
		 }
		 if(typeName!=null&&typeName!="") {
			 sql.append("and type_name=?");
			 params.add(typeName);
		 }
		 try {
			return queryRunner.query(sql.toString(), new BeanListHandler<TypeDB>(TypeDB.class,processor), params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	 }
	//添加图书类别
	 public Integer addType(String typeName) {
	    	String sql="insert into t_type(type_name)values(?)";
	    	Object[]params= {typeName};
	    	try {
				return queryRunner.update(sql,params);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return null;
	    	
	    }
	//管理员修改图书分类
	    public Integer updType(TypeDB typeDB) {
	    	String sql="update t_type set type_name=? where tid=?";
	    	Object[]params= {typeDB.getTypeName(),typeDB.getTid()};
	    	try {
	  		 return  queryRunner.update(sql,params);
	  	
	  	}catch(SQLException e) {
	  		e.printStackTrace();
	  	}
	    	return null;
	    }
	 // 删除类
	    public int delType(Integer tid) {
	        String sql = "delete from t_type where tid = ?";
	        Object[]params= {tid};
	        try {
	            int i=queryRunner.update(sql,params); 
	            return i;
	           // 使用参数化查询，避免SQL注入
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return -1;//删除失败
	    }
}
