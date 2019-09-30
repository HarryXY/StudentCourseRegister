package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import entity.Student;

public class StudentDao {
	
	public static void insertStudent(Student student) {
		Transaction tx = null;
		try{
			Configuration config=new Configuration().configure("hibernate.cfg.xml");
			SessionFactory sessionfactory=config.buildSessionFactory();			
			Session session=sessionfactory.openSession();
			tx=session.beginTransaction();
	        session.save(student);
	        tx.commit();
	        session.close();
	     } catch (HibernateException e) {
	    	 e.printStackTrace();
	    	 tx.rollback();
	     }	

    }
    
    
    public static void deleteStudent(int studentId) {
        Transaction tx = null;
        try {
            Student student = getStudent(studentId);
            Configuration config=new Configuration().configure("hibernate.cfg.xml");
			SessionFactory sessionfactory=config.buildSessionFactory();			
			Session session=sessionfactory.openSession();
			tx=session.beginTransaction();
            session.delete(student);
            tx.commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }
    
    public static Student getStudent(int studentId) {
        Transaction tx = null;
        Student student = null;
        try {
        	Configuration config=new Configuration().configure("hibernate.cfg.xml");
			SessionFactory sessionfactory=config.buildSessionFactory();			
			Session session=sessionfactory.openSession();
            student = (Student) session.get(Student.class, studentId);
            tx.commit();
            session.close();

        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }
        return student;
    }
    
    public static List<Student> getAllStudents(String courseName) {
    	Transaction tx = null;
    	List<Student> studentList = new ArrayList<>(0);
    	String sql = "select id, name from student where course_id in (select id from course where name = " + courseName + ") order by name";
    	try {
    		Configuration config=new Configuration().configure("hibernate.cfg.xml");
    		SessionFactory sessionfactory=config.buildSessionFactory();			
    		Session session=sessionfactory.openSession();
    		SQLQuery query = session.createSQLQuery(sql);
    		List<Object[]> rows = query.list();
    		for(Object[] row : rows){
    			int id = (int)row[0];
    			Student student = getStudent(id);
    			studentList.add(student);
    		}    		
    	}catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }
    	return studentList;
    }

}
