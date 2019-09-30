package dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import entity.Student;

public class RegisterDao {
	
	public static void updateCourseScore(int studentId, int courseId, int score) {
		Transaction tx = null;
		try{
			String sql = String.format("update register set score = %d where student_id = %d and course_id = %d", score, studentId, courseId);
			Configuration config=new Configuration().configure("hibernate.cfg.xml");
			SessionFactory sessionfactory=config.buildSessionFactory();			
			Session session=sessionfactory.openSession();
			SQLQuery query = session.createSQLQuery(sql);
	    	query.executeUpdate();
    	}catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }

    }  
	
	public static Set<Integer> findStudentsNotRegistered(int courseId) {
		Transaction tx = null;
		Set<Integer> notRegisterStudentIdSet = new HashSet<>();
		try{
			String sql = String.format("select id from student where id not in "
					+ "(select student_id from register where course_id = %d)", courseId);
			Configuration config=new Configuration().configure("hibernate.cfg.xml");
			SessionFactory sessionfactory=config.buildSessionFactory();			
			Session session=sessionfactory.openSession();
			SQLQuery query = session.createSQLQuery(sql);
			List<Object[]> rows = query.list();
			for(Object[] row : rows){
				int studentId = (int)row[0];
				notRegisterStudentIdSet.add(studentId);
			}
    	}catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }
		return notRegisterStudentIdSet;
	}
    
   
}
