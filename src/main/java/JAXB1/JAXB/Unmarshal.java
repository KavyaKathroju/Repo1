package JAXB1.JAXB;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class Unmarshal {
	public static void main(String[] args) throws JAXBException, ClassNotFoundException, SQLException, IllegalArgumentException, IllegalAccessException {
		
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
				"8991");

		File file = new File("C:\\Users\\admin\\Desktop\\Workspace\\JAXB\\JAXBfile.xml");
		JAXBContext context = JAXBContext.newInstance(UnMarshalStudents.class);
		Unmarshaller unmarsh = context.createUnmarshaller();
		UnMarshalStudents students = (UnMarshalStudents) unmarsh.unmarshal(file);
		ArrayList<Student> array = students.getList();
		
		for(Student studentdetails : array) {
			int i=1;
			String query="insert into studentdetails values(?,?,?)";
			PreparedStatement pstmt=connection.prepareStatement(query);
			Class<?> classofclass=Student.class;
			for (Field field: classofclass.getDeclaredFields()) {
				field.setAccessible(true);
				Class<?> type=field.getType();
				if(type.equals(int.class)) {
					pstmt.setInt(i, (int)field.get(studentdetails));
				}else if(type.equals(String.class)) {
					pstmt.setString(i, (String)field.get(studentdetails));
				}else if(type.equals(int.class)) {
					pstmt.setInt(i, (int)field.getInt(studentdetails));
				}
					i=i+1;	
					}
			pstmt.executeUpdate();
			pstmt.close();
				}
		connection.close();
			}
		
		
}
	

