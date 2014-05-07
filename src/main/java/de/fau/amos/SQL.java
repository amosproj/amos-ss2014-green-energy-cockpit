/*
 * Copyright (c) 2014 by Sven Huprich, Dimitry Abb, Jakob H�bler, Cindy Wiebe, Ferdinand Niedermayer, Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Green Energy Cockpit for the AMOS Project.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */



package de.fau.amos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.sql.DataSource;


public class SQL extends Energiedaten{

	//	public static String out="";

	//	public static void out(String msg){
	//		System.out.println(msg);
	//		out+=msg+"<br>";
	//	}

	public static void main(String args[]) {

		//		out("hallo");
		//		dosth(null);
		System.out.println("hi");
		
		ArrayList<ArrayList<String>> data=querry("SELECT name FROM plants,controlpoints GROUP BY plants.id;");
		
		for(int i=0;i<data.size();i++){
			for(int j=0;j<data.get(i).size();j++){
				System.out.print(data.get(i).get(j)+"; ");
			}
			System.out.println();
		}
		

	}


	public static void wasDimiGemachtHat(ServletConfig servletConfig){

		ArrayList<ArrayList<String>> data=new ArrayList<ArrayList<String>>();
		String fileName = "Auswertung-Tabelle.csv";

		File file=new File(servletConfig.getServletContext().getRealPath("/WEB-INF"),fileName);
		//		out("search for file at "+file.getAbsolutePath());


		String inputArray[] = null;
		FileReader fr = null;

		try {
			String lines;
			String inputFile = ""; 
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			while((lines = br.readLine()) != null) {



				inputFile += lines;           
			}
			br.close();

			inputArray = inputFile.split(";");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			


		SQL Anlage1 = new SQL();
		Anlage1.setStandort("Dresden");
		Anlage1.setColumns(inputArray);
		Anlage1.setValues(inputArray);



		InitialContext cxt;
		try {
			Class.forName("org.postgresql.Driver");

			cxt = new InitialContext();
			//			if ( cxt == null ) {
			//				try {
			//					throw new Exception("Uh oh -- no context!");
			//				} catch (Exception e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				}
			//			}

			DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );






			Connection c = null;
			//			c=ds.getConnection();
			Statement stmt = null;
			try {
				c = DriverManager
						.getConnection("jdbc:postgresql://faui2o2j.informatik.uni-erlangen.de:5432/ss14-proj5",
								"ss14-proj5", "quaeF4pigheNgahz");
				//				System.out.println("Opened database successfully");
				stmt = c.createStatement();
				ResultSet rs;


				//				stmt.executeUpdate(Anlage1.createTable(Anlage1.getStandort(), Anlage1.getColumns()));
				//				for(int i = 0; i < Anlage1.insertValues(Anlage1.getStandort(), Anlage1.getColumns(), Anlage1.getValues()).length; i++){
				//					stmt.executeUpdate(Anlage1.insertValues(Anlage1.getStandort(), Anlage1.getColumns(), Anlage1.getValues())[i]);
				//				}


				String querryCommand="SELECT " + Anlage1.getColumns()[0] + " FROM " + Anlage1.getStandort() + ";";


				data=querry(querryCommand);




				c.close();	         
			} catch (Exception e) {
				e.printStackTrace();
				//				System.err.println(e.getClass().getName()+": "+e.getMessage());
				//				System.exit(0);
			}
			//			System.out.println("Operation done successfully");










		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			//		} catch (SQLException e1) {
			//			// TODO Auto-generated catch block
			//			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}

	public static void execute(String command){

		Connection c=null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://faui2o2j.informatik.uni-erlangen.de:5432/ss14-proj5",
							"ss14-proj5", "quaeF4pigheNgahz");
			stmt = c.createStatement();
			stmt.execute(command);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<ArrayList<String>> querry(String command){
		ArrayList<ArrayList<String>> data=new ArrayList<ArrayList<String>>();

		Connection c=null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://faui2o2j.informatik.uni-erlangen.de:5432/ss14-proj5",
							"ss14-proj5", "quaeF4pigheNgahz");
			stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery(command);
			ResultSetMetaData rsmd = rs.getMetaData();


			ArrayList<String> row=new ArrayList<String>();
			for(int i=1;i<=rsmd.getColumnCount();i++){
				row.add(rsmd.getColumnName(i));
			}

			data.add(row);
			
			while ( rs.next() ) {	

				row=new ArrayList<String>();
				for(int i=1;i<=rsmd.getColumnCount();i++){
					row.add(rs.getString(i));
				}

				data.add(row);
			} 


			rs.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			//			ArrayList<ArrayList<String>>err=new ArrayList<ArrayList<String>>();
			//			ArrayList<String> error=new ArrayList<String>();
			//			error.add(e.getMessage());
			//			err.add(error);
			//			return err;
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}


		return data;
	}

	public static void createTable(String tableName,String[] columns){
		Connection c=null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://faui2o2j.informatik.uni-erlangen.de:5432/ss14-proj5",
							"ss14-proj5", "quaeF4pigheNgahz");
			stmt = c.createStatement();

			String commandPart = tableName+"_ID serial primary key";
			//			String commandPart = "ID INT NOT NULL AUTO_INCREMENT";
			for(int i=0; i < columns.length; i++){
				commandPart += ", ";
				commandPart += columns[i] + " TEXT NOT NULL";            
				//				if(columns[i] != columns[columns.length - 1]){
				//					commandPart += ", ";
				//				}
			}
			String command="CREATE TABLE "+tableName.toUpperCase()+" ("+commandPart+");";                     
			stmt.executeUpdate(command);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getColumns(String tableName){
		Connection c=null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://faui2o2j.informatik.uni-erlangen.de:5432/ss14-proj5",
							"ss14-proj5", "quaeF4pigheNgahz");
			stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM "+tableName+" WHERE "+tableName+"_ID=1");
			ResultSetMetaData rsmd = rs.getMetaData();

			ArrayList<String> row=new ArrayList<String>();

			for(int i=1;i<=rsmd.getColumnCount();i++){
				row.add(rsmd.getColumnName(i));
			}


			return row;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void addColumn(String tableName,ArrayList<String> columns,ArrayList<String> values){
		if(columns==null||values==null||columns.size()!=values.size()){
			return;
		}
		Connection c=null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://faui2o2j.informatik.uni-erlangen.de:5432/ss14-proj5",
							"ss14-proj5", "quaeF4pigheNgahz");
			stmt = c.createStatement();

			String commandColumnPart = "";
			for(int i=0; i < columns.size(); i++){
				if(columns.get(i).equals(tableName+"_id")){
					columns.remove(i);
					values.remove(i);
				}
			}
			for(int i=0; i < columns.size(); i++){
				commandColumnPart += '"'+columns.get(i) + '"';            
				if(i!=columns.size()-1){
					commandColumnPart += ", ";
				}
			}
			String commandValuePart = "";
			for(int i=0; i < values.size(); i++){
				commandValuePart += "'"+values.get(i) + "'";           
				if(i != values.size()-1){
					commandValuePart += ", ";
				}
			}

			String command="INSERT INTO "+tableName.toUpperCase()+
					" ("+commandColumnPart+")"+
					" VALUES ("+commandValuePart+");";
			System.out.println(command);
			stmt.execute(command);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<String> dosth(ServletConfig servletConfig){

		ArrayList<String> data=new ArrayList<String>();
		String fileName = "Auswertung-Tabelle.csv";

		File file=new File(servletConfig.getServletContext().getRealPath("/WEB-INF"),fileName);
		//		out("search for file at "+file.getAbsolutePath());


		String inputArray[] = null;
		FileReader fr = null;

		try {
			String lines;
			String inputFile = ""; 
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			while((lines = br.readLine()) != null) {



				inputFile += lines;           
			}
			br.close();

			inputArray = inputFile.split(";");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			


		SQL Anlage1 = new SQL();
		Anlage1.setStandort("Dresden");
		Anlage1.setColumns(inputArray);
		Anlage1.setValues(inputArray);



		InitialContext cxt;
		try {
			Class.forName("org.postgresql.Driver");

			cxt = new InitialContext();
			//			if ( cxt == null ) {
			//				try {
			//					throw new Exception("Uh oh -- no context!");
			//				} catch (Exception e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				}
			//			}

			DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );






			Connection c = null;
			//			c=ds.getConnection();
			Statement stmt = null;
			try {
				c = DriverManager
						.getConnection("jdbc:postgresql://faui2o2j.informatik.uni-erlangen.de:5432/ss14-proj5",
								"ss14-proj5", "quaeF4pigheNgahz");
				//				System.out.println("Opened database successfully");
				stmt = c.createStatement();
				ResultSet rs;


				//				stmt.executeUpdate(Anlage1.createTable(Anlage1.getStandort(), Anlage1.getColumns()));
				//				for(int i = 0; i < Anlage1.insertValues(Anlage1.getStandort(), Anlage1.getColumns(), Anlage1.getValues()).length; i++){
				//					stmt.executeUpdate(Anlage1.insertValues(Anlage1.getStandort(), Anlage1.getColumns(), Anlage1.getValues())[i]);
				//				}
				rs = stmt.executeQuery("SELECT " + Anlage1.getColumns()[0] + " FROM " + Anlage1.getStandort() + ";");

				while ( rs.next() ) {		    	 		    	 
					String a = rs.getString(Anlage1.getColumns()[0]);
					/* String b = rs.getString(Anlage1.getColumns()[1]);
			            String c1 = rs.getString(Anlage1.getColumns()[2]);
			            String d = rs.getString(Anlage1.getColumns()[3]);
			            String e = rs.getString(Anlage1.getColumns()[4]);
			            String f = rs.getString(Anlage1.getColumns()[5]);
			            String g = rs.getString(Anlage1.getColumns()[6]);
			            String h = rs.getString(Anlage1.getColumns()[7]);
			            String i = rs.getString(Anlage1.getColumns()[8]); */
					//					System.out.println(Anlage1.getColumns()[0] + " : " + a );
					/*  out(Anlage1.getColumns()[1] + " : " + b );
			            out(Anlage1.getColumns()[2] + " : " + c1 );
			            out(Anlage1.getColumns()[3] + " : " + d );
			            out(Anlage1.getColumns()[4] + " : " + e );
			            out(Anlage1.getColumns()[5] + " : " + f );
			            out(Anlage1.getColumns()[6] + " : " + g );
			            out(Anlage1.getColumns()[7] + " : " + h );
			            out(Anlage1.getColumns()[8] + " : " + i );		*/            
					//					System.out.println("");
				} 



				rs.close();
				stmt.close();
				c.close();	         
			} catch (Exception e) {
				e.printStackTrace();
				//				System.err.println(e.getClass().getName()+": "+e.getMessage());
				//				System.exit(0);
			}
			//			System.out.println("Operation done successfully");










		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			//		} catch (SQLException e1) {
			//			// TODO Auto-generated catch block
			//			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



		return data;


	}

}
