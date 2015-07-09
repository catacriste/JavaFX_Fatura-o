package application;

/* UtilsSqlConnection - Possui m�todos static para liga��o a bases de dados 
 * 	- mySqlTeste()- Testa liga��o a um SGBD MySQL, abre uma BD e fecha-a .
 * 	- mySqlQwery(String query) - Cria uma liga��o � BD e executa uma query, passada por parametro
 *  - mySqlDml(String dml) - - Cria uma liga��o � BD e executa uma dml, passada por parametro
 *  - SQLSerrverTeste()- Testa liga��o a um SGBD SQLServer.
 * 	- shutdownConnection() - Fecha a liga��o de BD
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class UtilsSQLConn {
	
	static private Connection conn = null;								// Objeto de Lica��o
	
	static String MYSQL_JDBC_DRIVER  = "com.mysql.jdbc.Driver";			// Connector para o MYSQL
	static String MYSQL_DB_URL = "jdbc:mysql://localhost/faturacao";		// url e nome da bd em MYSQL
	static String MYSQL_DB_USER = "root";								// BD user name MYSQL
	static String MYSQL_DB_PASS = "123";								// BD password MYSQL
	
	static String SQLSERVER_JDBC_DRIVER  = "com.microsoft.sqlserver.jdbc.SQLServerDriver";		// Connector para o SQLSERVER
	static String SQLSERVER_DB_URL = "jdbc:sqlserver://LX\\SQLEXPRESS;database=Escola";			// url e nome da bd em SQLSERVER
	//static String SQLSERVER_DB_URL = "jdbc:sqlserver://LX\\SQLEXPRESS;database=Escola;integratedSecurity=true";	// url e nome da bd em SQLSERVER
	static String SQLSERVER_DB_USER = "sa";								// BD user name SQLSERVER
	static String SQLSERVER_DB_PASS = "123";							// BD password SQLSERVER
	
	static boolean msgON = false;										// Ativa Mensagens de controlo
	static Alert alert = new Alert(AlertType.ERROR);
	static Alert alertInfo = new Alert(AlertType.INFORMATION);
	/* mySqlTeste()- Cria e testa uma liga��o a um SGBD MYSQL.*/
	/*public static void mySqlTeste(){
		try{
			//Tenta ligar-se ao SGBD e � base de dados
			
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				Utils.alertBox("layoutLeft", "Base dados aberta");
			}
		}
		catch(SQLException ex){							// Apanha Erro da connection ou DML
			Utils.alertBox("layoutLeft", "Erro na liga��o");
		}
		catch(ClassNotFoundException ex){				// Apanha Erro da Class.forName()
			Utils.alertBox("layoutLeft", "Erro no Driver");
		}
		catch(Exception ex){								// Apanha todas as restantes Exce��es
			Utils.alertBox("layoutLeft", "Erro gen�rico na liga��o");
			ex.printStackTrace();
		}
		finally{
			// Se liga��o com sucesso, fecha-a
			shutdownConnection();			
		}
	}*/
	//SELECT * FROM `fatura` WHERE 1
	// Executa uma query � base de dados de um SGBD MySQL
	public static ObservableList<Faturacao> mySqlQweryFaturacao(String query){
		ObservableList<Faturacao> listaFatura = FXCollections.observableArrayList();
		try{
			//Tenta ligar-se ao SGBD e � base de dados
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				alertInfo.setTitle("SQL INFO");
				alertInfo.setHeaderText("Base dados aberta");
				alertInfo.setContentText("");

				alertInfo.showAndWait();
			}
		}
		catch(SQLException ex){								// Apanha Erro da connection ou DML
			//Utils.alertBox("layoutLeft", "Erro na liga��o");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro na liga��o");
			alert.setContentText("");

			alert.showAndWait();
		}
		catch(ClassNotFoundException ex){					// Apanha Erro da Class.forName()
			//Utils.alertBox("layoutLeft", "Erro no Driver");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro na liga��o");
			alert.setContentText("");

			alert.showAndWait();
			
		}
		catch(Exception ex){								// Apanha todas as restantes Exce��es
		//	Utils.alertBox("layoutLeft", "Erro gen�rico na liga��o");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro gen�rico na liga��o");
			alert.setContentText("");

			alert.showAndWait();
			ex.printStackTrace();
		}
		finally{
			try{
				// Se liga��o com sucesso, executa a query
				if(!query.isEmpty()){		// Se a query tiver comando sql
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						Faturacao temp = new Faturacao();
						temp.setCodFatura(rs.getInt(1));
						temp.setClienteCodCivil(rs.getInt(2));
						temp.setDataDaFatura(rs.getString(3));
						temp.setGarantia(rs.getString(4));
						temp.setTotal(rs.getDouble(5));
						listaFatura.add(temp);
					
					}
					if(msgON){
						alertInfo.setTitle("SQL INFO");
						alertInfo.setHeaderText("DB");
						alertInfo.setContentText("Sucesso");

						alertInfo.showAndWait();
						//Utils.alertBox("DB", "" + query +"\n CodFatura : " + temp.getCodFatura());
					}
				}
				shutdownConnection();
				return listaFatura;
				
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				Utils.alertBox("Finally", "Erro na liga��o");
				shutdownConnection();
			}				
		}
		return listaFatura;
	}
	
	
	public static ObservableList<Produto> mySqlQweryProduto(String query){
		ObservableList<Produto> listaProduto = FXCollections.observableArrayList();
		try{
			//Tenta ligar-se ao SGBD e � base de dados
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				alertInfo.setTitle("SQL INFO");
				alertInfo.setHeaderText("Base dados aberta");
				alertInfo.setContentText("");

				alertInfo.showAndWait();
			}
		}
		catch(SQLException ex){								// Apanha Erro da connection ou DML
			//Utils.alertBox("layoutLeft", "Erro na liga��o");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro na liga��o");
			alert.setContentText("");

			alert.showAndWait();
		}
		catch(ClassNotFoundException ex){					// Apanha Erro da Class.forName()
			//Utils.alertBox("layoutLeft", "Erro no Driver");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro na liga��o");
			alert.setContentText("");

			alert.showAndWait();
			
		}
		catch(Exception ex){								// Apanha todas as restantes Exce��es
		//	Utils.alertBox("layoutLeft", "Erro gen�rico na liga��o");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro gen�rico na liga��o");
			alert.setContentText("");

			alert.showAndWait();
			ex.printStackTrace();
		}
		finally{
			try{
				// Se liga��o com sucesso, executa a query
				if(!query.isEmpty()){		// Se a query tiver comando sql
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						Produto temp = new Produto();
						temp.setCodProduto(rs.getInt(1));
						temp.setNomeProduto(rs.getString(2));
						temp.setMarca(rs.getString(3));
						temp.setDataValidade(rs.getString(4));
						temp.setPreco(rs.getString(5));
						temp.setStock(rs.getString(6));
						listaProduto.add(temp);
						
					}
					if(msgON){
						alertInfo.setTitle("SQL INFO");
						alertInfo.setHeaderText("DB");
						alertInfo.setContentText("Sucesso");

						alertInfo.showAndWait();

						//Utils.alertBox("DB", );
					}

				}
				shutdownConnection();
				return listaProduto;
				
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				alert.setTitle("SQL INFO");
				alert.setHeaderText("Erro na liga��o");
				alert.setContentText("FINALLY");

				alert.showAndWait();
				shutdownConnection();
			}				
		}
		return listaProduto;
	}
	
	public static ObservableList<Cliente> mySqlQweryCliente(String query){
		ObservableList<Cliente> listaCliente = FXCollections.observableArrayList();
		try{
			//Tenta ligar-se ao SGBD e � base de dados
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				alertInfo.setTitle("SQL INFO");
				alertInfo.setHeaderText("Base dados aberta");
				alertInfo.setContentText("");

				alertInfo.showAndWait();
			}
		}
		catch(SQLException ex){								// Apanha Erro da connection ou DML
			//Utils.alertBox("layoutLeft", "Erro na liga��o");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro na liga��o");
			alert.setContentText("");

			alert.showAndWait();
		}
		catch(ClassNotFoundException ex){					// Apanha Erro da Class.forName()
			//Utils.alertBox("layoutLeft", "Erro no Driver");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro na liga��o");
			alert.setContentText("");

			alert.showAndWait();
			
		}
		catch(Exception ex){								// Apanha todas as restantes Exce��es
		//	Utils.alertBox("layoutLeft", "Erro gen�rico na liga��o");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro gen�rico na liga��o");
			alert.setContentText("");

			alert.showAndWait();
			ex.printStackTrace();
		}
		finally{
			try{
				// Se liga��o com sucesso, executa a query
				if(!query.isEmpty()){		// Se a query tiver comando sql
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						Cliente temp = new Cliente();
						temp.setCodCivil(rs.getInt(1));
						temp.setNome(rs.getString(2));
						temp.setNIF(rs.getInt(3));
						temp.setMorada(rs.getString(4));
						temp.setNIB(rs.getInt(5));
						temp.setNISS(rs.getInt(6));
						listaCliente.add(temp);
						
					}
					if(msgON){
						//Utils.alertBox("DB", "" + query +"\n CodCivil : " + temp.getCodCivil());
						alertInfo.setTitle("SQL INFO");
						alertInfo.setHeaderText("DB");
						alertInfo.setContentText("Sucesso");

						alertInfo.showAndWait();
					}

				}
				shutdownConnection();
				return listaCliente;
				
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				alert.setTitle("SQL INFO");
				alert.setHeaderText("Erro na liga��o");
				alert.setContentText("FINALLY");

				alert.showAndWait();
				shutdownConnection();
			}				
		}
		return listaCliente;
	}
	/* Executa uma query � base de dados de um SGBD MySQL, para verificar a existencia de uma PK
	 * Recebe a qwery
	 * Ddevolve 1 se encontrou e 0 se n�o.
	 */
	public static boolean mySqlQweryCheckPK(String query){
		boolean foundPK = false;		
		
		try{
			//Tenta ligar-se ao SGBD e � base de dados
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				alertInfo.setTitle("SQL INFO");
				alertInfo.setHeaderText("Base dados aberta");
				alertInfo.setContentText("");

				alertInfo.showAndWait();
			}
		}
		catch(SQLException ex){								// Apanha Erro da connection ou DML
			//Utils.alertBox("layoutLeft", "Erro na liga��o");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro na liga��o");
			alert.setContentText("");

			alert.showAndWait();
		}
		catch(ClassNotFoundException ex){					// Apanha Erro da Class.forName()
			//Utils.alertBox("layoutLeft", "Erro no Driver");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro na liga��o");
			alert.setContentText("");

			alert.showAndWait();
			
		}
		catch(Exception ex){								// Apanha todas as restantes Exce��es
		//	Utils.alertBox("layoutLeft", "Erro gen�rico na liga��o");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro gen�rico na liga��o");
			alert.setContentText("");

			alert.showAndWait();
			ex.printStackTrace();
		}
		finally{
			try{
				// Se liga��o com sucesso, executa a query
				if(!query.isEmpty()){		// Se a query tiver comando sql
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					foundPK = rs.wasNull();
				}		
				shutdownConnection();						// fecha a liga��o
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				alert.setTitle("SQL INFO");
				alert.setHeaderText("Erro na liga��o");
				alert.setContentText("FINALLY");

				alert.showAndWait();
				shutdownConnection();
			}				
		}
		return foundPK;
	}
	
	
	// Executa um insert ou update para SGBD MySql.
	public static void mySqlDml(String dml){
		try{
			//Tenta ligar-se ao SGBD e � base de dados
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				alertInfo.setTitle("SQL INFO");
				alertInfo.setHeaderText("Base dados aberta");
				alertInfo.setContentText("");
				alertInfo.showAndWait();
			}
		}
		catch(SQLException ex){								// Apanha Erro da connection ou DML
			//Utils.alertBox("layoutLeft", "Erro na liga��o");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro na liga��o");
			alert.setContentText("");
			alert.showAndWait();
		}
		catch(ClassNotFoundException ex){					// Apanha Erro da Class.forName()
			//Utils.alertBox("layoutLeft", "Erro no Driver");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro na liga��o");
			alert.setContentText("");

			alert.showAndWait();
			
		}
		catch(Exception ex){								// Apanha todas as restantes Exce��es
		//	Utils.alertBox("layoutLeft", "Erro gen�rico na liga��o");
			alert.setTitle("SQL INFO");
			alert.setHeaderText("Erro gen�rico na liga��o");
			alert.setContentText("");
			alert.showAndWait();
			ex.printStackTrace();
		}
		finally{
			try{
				// Se liga��o com sucesso, executa a dml
				if(!dml.isEmpty()){		// Se a dml tiver comando sql, executa-o
					
					Statement stmt = conn.createStatement();		// Cria um obj comando sql
					int dmlResult = stmt.executeUpdate(dml);		// Executa-o. Devolve o n� de registos tratados
					if (dmlResult > 0 && msgON){					// Devolve inteiro > 0 se ok
						alertInfo.setTitle("SQL INFO");
						alertInfo.setHeaderText("Comando Insert, Update ou Delete OK !");
						alertInfo.setContentText("Sucesso");
						alertInfo.showAndWait();
					}
					else{
						if(msgON){
							alert.setTitle("SQL INFO");
							alert.setHeaderText("Ocorreu um erro ao executar o comando DELETE !");
							alert.setContentText("Verificar a conex�o ao servidor ou o comando inserido");
							
							alert.showAndWait();
						}
					}
				}		
				shutdownConnection();
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				alert.setTitle("SQL INFO");
				alert.setHeaderText("Erro na liga��o");
				alert.setContentText("FINALLY");

				alert.showAndWait();
				shutdownConnection();
			}				
		}
	}
	
	
	/******************************************************************************************
	 * SQLserver
	 * */
	
/*
	public static void connectToSQLSerrver(){
		//Connection conn = null;
		try{
			// Liga��o ao SGBD e � BD.
			Class.forName(SQLSERVER_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(SQLSERVER_DB_URL);
			if(msgON){
				Utils.alertBox("layoutLeft", "Base dados aberta");
			}
		}
		catch(SQLException ex){							// Apanha Erro da connection ou DML
			Utils.alertBox("layoutLeft", "Erro na liga��o");
		}
		catch(ClassNotFoundException ex){				// Apanha Erro da Class.forName()
			Utils.alertBox("layoutLeft", "Erro no Driver");
		}
		catch(Exception ex){								// Apanha todas as restantes Exce��es
			Utils.alertBox("layoutLeft", "Erro gen�rico na liga��o");
			ex.printStackTrace();
		}
		finally{
			try{
				if(conn != null){
					conn.close();
					if(msgON){
						Utils.alertBox("SQLSERVER", "Base dados fechada");
					}
				}
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				Utils.alertBox("layoutLeft", "Erro na liga��o");
			}				
		}
	}
	
	
	*/
	/*SHUTDOWNCONNECTION() - Fecha a liga��o de BD*/
	public static void shutdownConnection(){
		try{
			if (conn != null) { conn.close();}	// apenas se estiver aberta
			if(msgON){
				//Utils.alertBox("SQLshutDown", "Base dados fechada");
				alertInfo.setTitle("SQLshutDown");
				alertInfo.setHeaderText("Base dados fechada");
				alertInfo.setContentText("");
				alertInfo.showAndWait();
			}
		}
		catch(SQLException e){
		//	Utils.alertBox("SQLshutDown", "Erro no fecho da liga��o � BD");
			alert.setTitle("SQLshutDown");
			alert.setHeaderText("Erro no fecho da liga��o � BD");
			alert.setContentText("");
			alert.showAndWait();
		}
		catch(Exception e){
		//	Utils.alertBox("SQLshutDown", "Erro gen�rico no fecho da liga��o � BD");
			alert.setTitle("SQLshutDown");
			alert.setHeaderText("Erro gen�rico no fecho da liga��o � BD");
			alert.setContentText("");
			alert.showAndWait();
		}
    }
	
	
	
}
