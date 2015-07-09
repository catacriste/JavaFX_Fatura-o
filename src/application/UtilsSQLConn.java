package application;

/* UtilsSqlConnection - Possui métodos static para ligação a bases de dados 
 * 	- mySqlTeste()- Testa ligação a um SGBD MySQL, abre uma BD e fecha-a .
 * 	- mySqlQwery(String query) - Cria uma ligação à BD e executa uma query, passada por parametro
 *  - mySqlDml(String dml) - - Cria uma ligação à BD e executa uma dml, passada por parametro
 *  - SQLSerrverTeste()- Testa ligação a um SGBD SQLServer.
 * 	- shutdownConnection() - Fecha a ligação de BD
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class UtilsSQLConn {
	
	static private Connection conn = null;								// Objeto de Licação
	
	static String MYSQL_JDBC_DRIVER  = "com.mysql.jdbc.Driver";			// Connector para o MYSQL
	static String MYSQL_DB_URL = "jdbc:mysql://localhost/faturacao";		// url e nome da bd em MYSQL
	static String MYSQL_DB_USER = "root";								// BD user name MYSQL
	static String MYSQL_DB_PASS = "123";								// BD password MYSQL
	
	static String SQLSERVER_JDBC_DRIVER  = "com.microsoft.sqlserver.jdbc.SQLServerDriver";		// Connector para o SQLSERVER
	static String SQLSERVER_DB_URL = "jdbc:sqlserver://LX\\SQLEXPRESS;database=Escola";			// url e nome da bd em SQLSERVER
	//static String SQLSERVER_DB_URL = "jdbc:sqlserver://LX\\SQLEXPRESS;database=Escola;integratedSecurity=true";	// url e nome da bd em SQLSERVER
	static String SQLSERVER_DB_USER = "sa";								// BD user name SQLSERVER
	static String SQLSERVER_DB_PASS = "123";							// BD password SQLSERVER
	
	static boolean msgON = true;										// Ativa Mensagens de controlo
	
	/* mySqlTeste()- Cria e testa uma ligação a um SGBD MYSQL.*/
	/*public static void mySqlTeste(){
		try{
			//Tenta ligar-se ao SGBD e à base de dados
			
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				Utils.alertBox("layoutLeft", "Base dados aberta");
			}
		}
		catch(SQLException ex){							// Apanha Erro da connection ou DML
			Utils.alertBox("layoutLeft", "Erro na ligação");
		}
		catch(ClassNotFoundException ex){				// Apanha Erro da Class.forName()
			Utils.alertBox("layoutLeft", "Erro no Driver");
		}
		catch(Exception ex){								// Apanha todas as restantes Exceções
			Utils.alertBox("layoutLeft", "Erro genérico na ligação");
			ex.printStackTrace();
		}
		finally{
			// Se ligação com sucesso, fecha-a
			shutdownConnection();			
		}
	}*/
	//SELECT * FROM `fatura` WHERE 1
	// Executa uma query à base de dados de um SGBD MySQL
	public static ObservableList<Faturacao> mySqlQweryFaturacao(String query){
		ObservableList<Faturacao> listaFatura = FXCollections.observableArrayList();
		try{
			//Tenta ligar-se ao SGBD e à base de dados
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				Utils.alertBox("layoutLeft", "Base dados aberta");
			}
		}
		catch(SQLException ex){							// Apanha Erro da connection ou DML
			Utils.alertBox("layoutLeft", "Erro na ligação");
		}
		catch(ClassNotFoundException ex){				// Apanha Erro da Class.forName()
			Utils.alertBox("layoutLeft", "Erro no Driver");
		}
		catch(Exception ex){								// Apanha todas as restantes Exceções
			Utils.alertBox("layoutLeft", "Erro genérico na ligação");
			ex.printStackTrace();
		}
		finally{
			try{
				// Se ligação com sucesso, executa a query
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
						if(msgON){
							Utils.alertBox("DB", "" + query +"\n CodFatura : " + temp.getCodFatura());
						}
					}

				}
				shutdownConnection();
				return listaFatura;
				
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				Utils.alertBox("Finally", "Erro na ligação");
				shutdownConnection();
			}				
		}
		return listaFatura;
	}
	
	
	public static ObservableList<Produto> mySqlQweryProduto(String query){
		ObservableList<Produto> listaProduto = FXCollections.observableArrayList();
		try{
			//Tenta ligar-se ao SGBD e à base de dados
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				Utils.alertBox("layoutLeft", "Base dados aberta");
			}
		}
		catch(SQLException ex){							// Apanha Erro da connection ou DML
			Utils.alertBox("layoutLeft", "Erro na ligação");
		}
		catch(ClassNotFoundException ex){				// Apanha Erro da Class.forName()
			Utils.alertBox("layoutLeft", "Erro no Driver");
		}
		catch(Exception ex){								// Apanha todas as restantes Exceções
			Utils.alertBox("layoutLeft", "Erro genérico na ligação");
			ex.printStackTrace();
		}
		finally{
			try{
				// Se ligação com sucesso, executa a query
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
						if(msgON){
							Utils.alertBox("DB", "" + query +"\n CodProduto : " + temp.getCodProduto());
						}
					}

				}
				shutdownConnection();
				return listaProduto;
				
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				Utils.alertBox("Finally", "Erro na ligação");
				shutdownConnection();
			}				
		}
		return listaProduto;
	}
	
	public static ObservableList<Cliente> mySqlQweryCliente(String query){
		ObservableList<Cliente> listaCliente = FXCollections.observableArrayList();
		try{
			//Tenta ligar-se ao SGBD e à base de dados
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				Utils.alertBox("layoutLeft", "Base dados aberta");
			}
		}
		catch(SQLException ex){							// Apanha Erro da connection ou DML
			Utils.alertBox("layoutLeft", "Erro na ligação");
		}
		catch(ClassNotFoundException ex){				// Apanha Erro da Class.forName()
			Utils.alertBox("layoutLeft", "Erro no Driver");
		}
		catch(Exception ex){								// Apanha todas as restantes Exceções
			Utils.alertBox("layoutLeft", "Erro genérico na ligação");
			ex.printStackTrace();
		}
		finally{
			try{
				// Se ligação com sucesso, executa a query
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
						if(msgON){
							Utils.alertBox("DB", "" + query +"\n CodCivil : " + temp.getCodCivil());
						}
					}

				}
				shutdownConnection();
				return listaCliente;
				
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				Utils.alertBox("Finally", "Erro na ligação");
				shutdownConnection();
			}				
		}
		return listaCliente;
	}
	/* Executa uma query à base de dados de um SGBD MySQL, para verificar a existencia de uma PK
	 * Recebe a qwery
	 * Ddevolve 1 se encontrou e 0 se não.
	 */
	public static boolean mySqlQweryCheckPK(String query){
		boolean foundPK = false;		
		
		try{
			//Tenta ligar-se ao SGBD e à base de dados
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				Utils.alertBox("layoutLeft", "Base dados aberta");
			}
		}
		catch(SQLException ex){							// Apanha Erro da connection ou DML
			Utils.alertBox("layoutLeft", "Erro na ligação");
		}
		catch(ClassNotFoundException ex){				// Apanha Erro da Class.forName()
			Utils.alertBox("layoutLeft", "Erro no Driver");
		}
		catch(Exception ex){								// Apanha todas as restantes Exceções
			Utils.alertBox("layoutLeft", "Erro genérico na ligação");
			ex.printStackTrace();
		}
		finally{
			try{
				// Se ligação com sucesso, executa a query
				if(!query.isEmpty()){		// Se a query tiver comando sql
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					foundPK = rs.wasNull();
				}		
				shutdownConnection();						// fecha a ligação
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				Utils.alertBox("Finally", "Erro na ligação");
				shutdownConnection();
			}				
		}
		return foundPK;
	}
	
	
	// Executa um insert ou update para SGBD MySql.
	public static void mySqlDml(String dml){
		try{
			//Tenta ligar-se ao SGBD e à base de dados
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				Utils.alertBox("layoutLeft", "Base dados aberta");
			}
		}
		catch(SQLException ex){								// Apanha Erro da connection ou DML
			Utils.alertBox("layoutLeft", "Erro na ligação");
		}
		catch(ClassNotFoundException ex){					// Apanha Erro da Class.forName()
			Utils.alertBox("layoutLeft", "Erro no Driver");
		}
		catch(Exception ex){								// Apanha todas as restantes Exceções
			Utils.alertBox("layoutLeft", "Erro genérico na ligação");
			ex.printStackTrace();
		}
		finally{
			try{
				// Se ligação com sucesso, executa a dml
				if(!dml.isEmpty()){		// Se a dml tiver comando sql, executa-o
					
					Statement stmt = conn.createStatement();		// Cria um obj comando sql
					int dmlResult = stmt.executeUpdate(dml);		// Executa-o. Devolve o nº de registos tratados
					if (dmlResult > 0 && msgON){					// Devolve inteiro > 0 se ok
						Utils.alertBox("DB","Comando DML OK");		// 0 ou menor, se ERRO.
					}
					else{
						if(msgON){
							Utils.alertBox("DB","ERRO Comando DML");
						}
					}
				}		
				shutdownConnection();
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				Utils.alertBox("Finally", "Erro na ligação");
				shutdownConnection();
			}				
		}
	}
	
	/*************************************************************************************************
	 * Métodos para carregamento das Listas de alimentação das TableViews. 
	 * São executados pelo botão EDITAR, eliminar, alterar ou eliminar de cada entidade
	 * Popular uma ObservableList com os dados da BD e desvolvemr à TableView
	 *************************************************************************************************/
    public static ObservableList<Aluno> carregaListaAlunos(){
    	
    	ObservableList<Aluno> listaAlunos = FXCollections.observableArrayList();

    	/*TODO: Lista para preencher com os dados da tabela
    	 * 	Executa uma query à tabela Aluno e para cada registo, 
    	 * 		1 Extrai os 3 atributos: nProc, NAluno e nome
    	 *  	2 Adiciona à lista
    	 *  Devolve a lista à TableView para desenhar a lista de Alunos
    	 */

    	try{
			//Tenta ligar-se ao SGBD e à base de dados
			Class.forName(MYSQL_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_DB_USER, MYSQL_DB_PASS );
			if(msgON){
				Utils.alertBox("carregaListaAlunos", "Base dados aberta");
			}
		}
		catch(SQLException ex){							// Apanha Erro da connection ou DML
			Utils.alertBox("carregaListaAlunos", "Erro na ligação");
			return null;
		}
		catch(ClassNotFoundException ex){				// Apanha Erro da Class.forName()
			Utils.alertBox("carregaListaAlunos", "Erro no Driver");
			return null;
		}
		catch(Exception ex){								// Apanha todas as restantes Exceções
			Utils.alertBox("carregaListaAlunos", "Erro genérico na ligação");
			ex.printStackTrace();
			return null;
		}
		finally{
			try{
				// Se ligação com sucesso, executa a query
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from Aluno");
				
				// Para cada registo existente na Query rs,
				while(rs.next()){					
				//	Aluno a = new Aluno();			// Cria um novo aluno
					//a.setNProc(rs.getInt(1));		// Copia o dado da coluna 1 (nProc) para a
				//	a.setNTurma(rs.getInt(2));		// Extrai o dado da colina 2 (NAluno) para a
				//	a.setNome(rs.getString(4));		// Extrai o dado da coluna 4 (Nome) para a
				//	listaAlunos.add(a);				// Adiciona-o à lista.
					
					//Alternativa: uma unica linha, usando o contrutor de Aluno
					//listaAlunos.add(new Aluno(rs.getInt(1), rs.getInt(2), rs.getString(4)));
				}
				if(msgON){
					Utils.alertBox("carregaListaAlunos", "Lista Construida");
				}
				shutdownConnection();
			}
			catch(SQLException ex){							// Apanha Erro da connection ou DML
				Utils.alertBox("carregaListaAlunos", "Finally - Erro na ligação");
				shutdownConnection();
				return null;
			}	
		}
    	return listaAlunos;
    }

	
	
	/******************************************************************************************
	 * SQLserver
	 * */
	

	public static void connectToSQLSerrver(){
		//Connection conn = null;
		try{
			// Ligação ao SGBD e à BD.
			Class.forName(SQLSERVER_JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(SQLSERVER_DB_URL);
			if(msgON){
				Utils.alertBox("layoutLeft", "Base dados aberta");
			}
		}
		catch(SQLException ex){							// Apanha Erro da connection ou DML
			Utils.alertBox("layoutLeft", "Erro na ligação");
		}
		catch(ClassNotFoundException ex){				// Apanha Erro da Class.forName()
			Utils.alertBox("layoutLeft", "Erro no Driver");
		}
		catch(Exception ex){								// Apanha todas as restantes Exceções
			Utils.alertBox("layoutLeft", "Erro genérico na ligação");
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
				Utils.alertBox("layoutLeft", "Erro na ligação");
			}				
		}
	}
	
	
	
	/*SHUTDOWNCONNECTION() - Fecha a ligação de BD*/
	public static void shutdownConnection(){
		try{
			if (conn != null) { conn.close();}	// apenas se estiver aberta
			if(msgON){
				Utils.alertBox("SQLshutDown", "Base dados fechada");
			}
		}
		catch(SQLException e){
			Utils.alertBox("SQLshutDown", "Erro no fecho da ligação à BD");
		}
		catch(Exception e){
			Utils.alertBox("SQLshutDown", "Erro genérico no fecho da ligação à BD");
		}
    }
	
	
	
}
