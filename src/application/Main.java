package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
//--------------------------SUMÁRIO--------------------------------------
/*Aplicação destinada ao projeto de Faturação , para registar , alterar e eliminar faturas e produtos
 *Com acesso a uma base de dados , serão usados comandos SQL para inserir dados na base de dados eliminar e alterar
 *A fatura tem campos como Data, garantia e loja  e necessita de dados do cliente que são : Nome ,NIF ,Morada, NIB, NISS e codCivil
 *Também necessita de alguns campos de Produto , pois sem produto não há fatura
 */

public class Main extends Application {
	
//Login
//	String userName = "";				//Define o utilizador 
//	String password = "";				//Define a password
	String checkUser, checkPw;				//2 Strings para verificar o login posteriormente
	ObservableList<Faturacao> 	tabelaFaturas = FXCollections.observableArrayList();
	ObservableList<Produto> 	tabelaProduto = FXCollections.observableArrayList();
	ObservableList<Cliente> 	tabelaCliente = FXCollections.observableArrayList();
	boolean msgOn = false;
	//Outros
	//Objeto para receber o indice selecionado na tabela
	static Faturacao faturaSelecionada = null;
	static Produto produtoSelecionado = null;
	static Cliente clienteSelecionado = null;
	//----------------------ALERT DIALOG---------------------
	Alert alert = new Alert(AlertType.ERROR);
	Alert alertInfo = new Alert(AlertType.INFORMATION);
	
	
	//Todos os layouts do projeto
	GridPane gridLogin = new GridPane();
	BorderPane layoutLogin = new BorderPane();
	GridPane layoutRegistar = new GridPane();
	//Layout da Faturacao
	BorderPane layoutFatura = new BorderPane();
	GridPane layoutFormInserir = new GridPane();
	GridPane layoutFormAlterar = new GridPane();
	//Layouts do Produto
	BorderPane layoutProduto = new BorderPane();
	GridPane layoutFormInserirProduto = new GridPane();
	GridPane layoutFormAlterarProduto = new GridPane();
	//Layouts do Cliente
	BorderPane layoutCliente = new BorderPane();
	GridPane layoutFormInserirCliente = new GridPane();
	GridPane layoutFormAlterarCliente = new GridPane();
	
	
	
	BorderPane layoutRoot = new BorderPane();
	
	//Tabelas
	TableView<Faturacao> tableFatura = new TableView<>();
	TableView<Produto> tableProdutos = new TableView<>();
	TableView<Cliente> tableCliente = new TableView<>();
	
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			
			/*---------------------------------------MENU-------------------------------*/
			//TODO : Fazer o menu
			//MENU FATURACAO
			Menu menuFatura = new Menu("Faturação");
			MenuItem menuFaturaMostrar  = new MenuItem("Mostrar Dados");
			menuFatura.getItems().addAll(menuFaturaMostrar);
			//MENU PRODUTO
			Menu menuProduto = new Menu("Produtos");
			MenuItem menuProdutoMostrar = new MenuItem("Mostrar Dados");
			menuProduto.getItems().addAll(menuProdutoMostrar);
			//MENU CLIENTE
			Menu menuCliente = new Menu("Clientes");
			MenuItem menuClienteMostrar = new MenuItem("Mostrar Dados");
			menuCliente.getItems().addAll(menuClienteMostrar);
			
			//Radio menu para desactivar as mensagens ou nao
			
			Menu msg = new Menu("Mensagens");
			RadioMenuItem on = new RadioMenuItem("ON");
			RadioMenuItem off = new RadioMenuItem("OFF");
			msg.getItems().addAll(on,off);
			//Toggle para deselecionar quando nao necessário
			ToggleGroup onOffToogle = new ToggleGroup();
			on.setToggleGroup(onOffToogle);
			off.setToggleGroup(onOffToogle);
			on.setSelected(msgOn);
			
			
			//Condição que ativa e desativa as mensagens
			on.setOnAction(e->{
				if(on.isSelected())
				{
					UtilsSQLConn.msgON = true;
					msgOn = true;
				}	
			});
			off.setOnAction(e->{
				if (off.isSelected()) {
					UtilsSQLConn.msgON = false;
					msgOn = false;
				}
			});
			
			
			
			MenuBar menuBar = new MenuBar();
			menuBar.setStyle("-fx-background-color: #0099FF");
	        //Adiciona os menus ao menuBar
	        menuBar.getMenus().addAll(menuFatura, menuProduto,menuCliente,msg);
			//------------------------------JANELA E LAYOUT-----------------------------------------------------
			// TODO : Configurar os layouts e janela
//--------------------------------------------------TABLE DAS FATURAS----------------------------------------------------
	        //TableViews - Criação das tabelas
	       
	        //Coluna Do Indice
	        TableColumn<Faturacao, Integer> colunaIDFaturacao = new TableColumn<>("Índice");
	       // colunaIDFaturacao.setMinWidth(0);		//largura em pixeis da coluna
	        colunaIDFaturacao.setCellValueFactory(new PropertyValueFactory<>("codFatura"));
	        TableColumn<Faturacao, Integer> colunaClientecodCivil = new TableColumn<>("Cliente");
		       // colunaIDFaturacao.setMinWidth(0);		//largura em pixeis da coluna
	        colunaClientecodCivil.setCellValueFactory(new PropertyValueFactory<>("clienteCodCivil"));
	        
	        //Coluna dataFatura
	        TableColumn<Faturacao, String> colunaDataFatura = new TableColumn<>("Data da Fatura");
	        colunaDataFatura.setMinWidth(100);		//largura em pixeis da coluna
	        colunaDataFatura.setCellValueFactory(new PropertyValueFactory<>("dataDaFatura"));
	        
	        //Coluna garantia
	        TableColumn<Faturacao, String> colunaGarantia = new TableColumn<>("Validade da Garantia");
	        colunaGarantia.setMinWidth(200);		//largura em pixeis da coluna
	        colunaGarantia.setCellValueFactory(new PropertyValueFactory<>("garantia"));
	        
	        //Coluna total
	        TableColumn<Faturacao, Double> colunaTotal = new TableColumn<>("Total");
	        //colunaIDFaturacao.setMinWidth(100);		//largura em pixeis da coluna
	        colunaTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
	        
	        tableFatura.getColumns().addAll(colunaIDFaturacao,colunaClientecodCivil,colunaDataFatura,colunaGarantia,colunaTotal);
	        tableFatura.setItems(tabelaFaturas);
	        
	        //HBOX PARA OS BUTOES
	        
	        HBox hBoxFatura = new HBox(10);
	        hBoxFatura.setPadding(new Insets(10,0,10,250));
	        hBoxFatura.setStyle("-fx-background-color: #005CB8");
	        
	        //Os 3 Butões , inserir ,alterar e eliminar
	        Button btnInserirFatura = new Button("Inserir");
	        //btnInserirFatura.setPadding(value);
	        Button btnAlterarFatura = new Button("Alterar");
	        Button btnEliminarFatura = new Button("Eliminar");
	       
	        //Adiciona os butoes a HBOX e Hbox ao Buttom do Border Pane
	        hBoxFatura.getChildren().addAll(btnInserirFatura,btnAlterarFatura,btnEliminarFatura);
			
	        
	      //layoutFatura.getChildren().add(tableFatura);
			layoutFatura.setCenter(tableFatura);
			layoutFatura.setBottom(hBoxFatura);
			
 //--------------------------------------------------TABLE DOS PRODUTOS----------------------------------------------------
	      //TableViews - Criação das tabelas
	      
	        //Coluna Do Indice
	        TableColumn<Produto, Integer> colunaIDProduto = new TableColumn<>("Índice");
	       // colunaIDFaturacao.setMinWidth(0);		//largura em pixeis da coluna
	        colunaIDProduto.setCellValueFactory(new PropertyValueFactory<>("codProduto"));
	        
	        //Coluna Produto
	        TableColumn<Produto, String> colunaNomeProduto = new TableColumn<>("Produto");
	        colunaNomeProduto.setMinWidth(100);		//largura em pixeis da coluna
	        colunaNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
	        
	        //Coluna Marca
	        TableColumn<Produto, String> colunaMarca = new TableColumn<>("Marca");
	       // colunaMarca.setMinWidth(200);		//largura em pixeis da coluna
	        colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
	        
	        //Coluna Validade
	        TableColumn<Produto, String> colunaDataValidade = new TableColumn<>("Data de Validade");
	        colunaDataValidade.setMinWidth(150);		//largura em pixeis da coluna
	        colunaDataValidade.setCellValueFactory(new PropertyValueFactory<>("dataValidade"));
	        
	        TableColumn<Produto, String> colunaPreco = new TableColumn<>("Preço");
	        //colunaIDFaturacao.setMinWidth(100);		//largura em pixeis da coluna
	        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
	        
	        TableColumn<Produto, String> colunaStock = new TableColumn<>("Stock");
	        //colunaIDFaturacao.setMinWidth(100);		//largura em pixeis da coluna
	        colunaStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
	        
	        
	        
	        
	        tableProdutos.getColumns().addAll(colunaIDProduto,colunaNomeProduto,colunaMarca,colunaDataValidade, colunaPreco , colunaStock);
	        tableProdutos.setItems(tabelaProduto);
			
	        HBox hBoxProduto = new HBox(10);
	        hBoxProduto.setPadding(new Insets(10,0,10,250));
	        hBoxProduto.setStyle("-fx-background-color: #005CB8");
	        
	        //Os 3 Butões , inserir ,alterar e eliminar
	        Button btnInserirProduto = new Button("Inserir");
	        //btnInserirFatura.setPadding(value);
	        Button btnAlterarProduto = new Button("Alterar");
	        Button btnEliminarProduto = new Button("Eliminar");
	       
	        //Adiciona os butoes a HBOX e Hbox ao Buttom do Border Pane
	        hBoxProduto.getChildren().addAll(btnInserirProduto,btnAlterarProduto,btnEliminarProduto);
			
	        
	        
			//layoutProduto.getChildren().add(tableProdutos);
			layoutProduto.setCenter(tableProdutos);;
			layoutProduto.setBottom(hBoxProduto);
			
			 //--------------------------------------------------TABLE DOS Cliente----------------------------------------------------
		      //TableViews - Criação das tabelas
		     
		        //Coluna 
		        TableColumn<Cliente, Integer> colunaIDCliente = new TableColumn<>("Código Civil");
		       // colunaIDFaturacao.setMinWidth(0);		//largura em pixeis da coluna
		        colunaIDCliente.setCellValueFactory(new PropertyValueFactory<>("codCivil"));
		        
		        //Coluna 
		        TableColumn<Cliente, String> colunaNome = new TableColumn<>("Nome");
		        colunaNome.setMinWidth(100);		//largura em pixeis da coluna
		        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		        
		        //Coluna
		        TableColumn<Cliente, Integer> colunaNIF = new TableColumn<>("NIF");
		       // colunaMarca.setMinWidth(200);		//largura em pixeis da coluna
		        colunaNIF.setCellValueFactory(new PropertyValueFactory<>("NIF"));
		        
		        // Coluna
		        TableColumn<Cliente, String> colunaMorada = new TableColumn<>("Morada");
		        colunaMorada.setMinWidth(0);		//largura em pixeis da coluna
		        colunaMorada.setCellValueFactory(new PropertyValueFactory<>("morada"));
		        
		        // Coluna
		        TableColumn<Cliente, Integer> colunaNIB = new TableColumn<>("NIB");
		        //colunaIDFaturacao.setMinWidth(100);		//largura em pixeis da coluna
		        colunaNIB.setCellValueFactory(new PropertyValueFactory<>("NIB"));
		        
		        // Coluna
		        TableColumn<Cliente, Integer> colunaNISS = new TableColumn<>("NISS");
		        //colunaIDFaturacao.setMinWidth(100);		//largura em pixeis da coluna
		        colunaNISS.setCellValueFactory(new PropertyValueFactory<>("NISS"));
		        
		        
		        
		        tableCliente.getColumns().addAll(colunaIDCliente,colunaNome,colunaNIF,colunaMorada, colunaNIB , colunaNISS);
		        tableCliente.setItems(tabelaCliente);
			
		        
				//layoutCliente.getChildren().add(tableCliente);
				
				
				HBox hBoxCliente = new HBox(10);
				hBoxCliente.setPadding(new Insets(10,0,10,250));
				hBoxCliente.setStyle("-fx-background-color: #005CB8");
		        
		        //Os 3 Butões , inserir ,alterar e eliminar
		        Button btnInserirCliente = new Button("Inserir");
		        //btnInserirFatura.setPadding(value);
		        Button btnAlterarCliente = new Button("Alterar");
		        Button btnEliminarCliente = new Button("Eliminar");
		       
		        //Adiciona os butoes a HBOX e Hbox ao Buttom do Border Pane
		        hBoxCliente.getChildren().addAll(btnInserirCliente,btnAlterarCliente,btnEliminarCliente);
				layoutCliente.setCenter(tableCliente);
				layoutCliente.setBottom(hBoxCliente);
			
			
			//Layout root e outros layouts ----
	        
	        //VBOX CENTRAL
	       /* VBox layoutCenter = new VBox();
	        layoutCenter.setPadding(new Insets(150,100,100,150));
	        Button btnEntrar = new Button("Entrar");
	        Button btnQuit = new Button("Sair");
	        
	        layoutCenter.getChildren().addAll(btnEntrar, btnQuit);*/
//-------------------------------------------------------------LOGIN----------------------------------------------------------
					Text loginText = new Text();
					loginText.setText("Log-In");
					loginText.setFont(Font.font("Tahoma", FontWeight.MEDIUM, 20));
				//	BorderPane layoutLogin = new BorderPane();
			        
			        layoutLogin.setCenter(gridLogin);
			        gridLogin.setAlignment(Pos.CENTER);
			        gridLogin.setHgap(10);
			        gridLogin.setVgap(10);
			        gridLogin.setPadding(new Insets(25, 25, 25, 25));

			        gridLogin.add(loginText, 0, 0, 2, 1);

			        Label username = new Label();
			        username.setText("Username");
			        gridLogin.add(username, 0, 1);

			        TextField textFieldUserName = new TextField();
			        gridLogin.add(textFieldUserName, 1, 1);

			        Label password = new Label();
			        password.setText("Password");
			        gridLogin.add(password, 0, 2);

			        PasswordField passwordFieldPassword = new PasswordField();
			        gridLogin.add(passwordFieldPassword, 1, 2);

			        
			        Button btnOk = new Button("Sign In"); 
			        btnOk.setDefaultButton(true);
			        HBox hbox = new HBox(8); // spacing = 8
			        hbox.getChildren().add(btnOk);
			        hbox.setAlignment(Pos.BOTTOM_RIGHT);
			        gridLogin.add(hbox, 1, 3);
			        //gridLogin.setGridLinesVisible(true); //Uncomment to see the actual layout
			        final Text pressedText = new Text();
			        pressedText.setFill(Color.FIREBRICK);
			        gridLogin.add(pressedText, 1, 5);
			        
			        HBox hBoxRegistar = new HBox();
			        hBoxRegistar.setPadding(new Insets(0,0,90,95));
			        Button registar = new Button("Registar");
			        hBoxRegistar.getChildren().addAll(registar);
			        gridLogin.add(hBoxRegistar, 1,6);
			      //  layoutLogin.setBottom(hBoxRegistar);
			        
			        //hBoxRegistar.setAlignment(Pos.TOP_CENTER);
			       
	        //Layout gridLogin
			
/*			
			HBox hbLoginText = new HBox(50);
			hbLoginText.setPadding(new Insets(0,0,0,100));
		//	hbLoginText.setStyle("");
			
			Text txtLogin = new Text("Log-In");
			txtLogin.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
			hbLoginText.getChildren().add(txtLogin);
	        GridPane layoutCenter = new GridPane(); 
	        layoutLogin.setCenter(layoutCenter);
	        layoutLogin.setTop(hbLoginText);
	        //layoutLogin.setTop(hbLoginText);
			layoutCenter.setPadding(new Insets(10, 50, 50, 50));
			
			
	      //Labels
			Label labelUserName = new Label("Username: ");
			Label labelPassword = new Label("Password: ");
			//TextFields
			TextField textFieldUserName = new TextField();
			PasswordField passwordFieldPassword = new PasswordField();
			
		//	layoutCenter.add(txtLogin, 2, 0);
			layoutCenter.add(labelUserName, 1, 1);
			layoutCenter.add(textFieldUserName, 2, 1);
			layoutCenter.add(labelPassword, 1, 3);
			layoutCenter.add(passwordFieldPassword, 2,3);
			layoutCenter.add(btnOk, 2, 4);
			
			btnOk.setDefaultButton(true);*/
			
			//--------------------------------------------------SCENE E LAYOUT -------------------------------------------
	        //Border Pane
			
			layoutRoot.setTop(menuBar);
			//layoutRoot.setBottom(btnInserir);
			//Scene ( Janela) Principal------
			Scene principal = new Scene(layoutRoot,700,500);
			Scene login = new Scene(layoutLogin,300,250);
		//	Scene fatura = new Scene(layoutFatura, 400,400);
			
			
			//-------------------MENUS and BUTTONS EVENT HANDLERS ----------------------
			menuFaturaMostrar.setOnAction(e->{
	        	layoutRoot.setCenter(layoutFatura);
	        	primaryStage.setTitle("Faturação");
	        	try {
	        		tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
				
	        	} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Erro de Ligação à BD");
					alert.setContentText("Ponteiro Nulo");
					alert.showAndWait();
				}
	        	
	        	
	        });
			menuProdutoMostrar.setOnAction(e->{
	        	layoutRoot.setCenter(layoutProduto);
	        	primaryStage.setTitle("Produtos");
	        	try {
	        		tabelaProduto.setAll(UtilsSQLConn.mySqlQweryProduto("SELECT * FROM `produto`"));
				} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Erro de Ligação à BD");
					alert.setContentText("Ponteiro Nulo");
					alert.showAndWait();
				}
	        	
	        	
	        });
			
			menuClienteMostrar.setOnAction(e->{
	        layoutRoot.setCenter(layoutCliente);
	        primaryStage.setTitle("Clientes");
	        try {
	        	tabelaCliente.setAll(UtilsSQLConn.mySqlQweryCliente("SELECT * FROM `cliente`"));
			} catch (NullPointerException e2) {
				alert.setTitle("Exception ");
				alert.setHeaderText("Erro de Ligação à BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
	        	
	        });
			
//----------------------------------------------Eventos de Inserir , Alterar e Eliminar Faturas-----------------------------------------------------
			btnInserirFatura.setOnAction(e->{
				layoutRoot.setCenter(FormFaturacaoInserir());
			});
			
			
			
			btnAlterarFatura.setOnAction(e->{
				try {
					layoutRoot.setCenter(FormAlterarFaturacao());
				} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Não selecionou nenhum dado da tabela");
					alert.setContentText("");
					alert.showAndWait();
				}
				
			});
			
			btnEliminarFatura.setOnAction(e->{
				//Efetua a eliminação de dados numa tabela
				int codFatura; 				//Variável para usar no comando

				ObservableList<Faturacao> itemSelecionado = tableFatura.getSelectionModel().getSelectedItems();
				faturaSelecionada = itemSelecionado.get(0);
				
				try {
					codFatura = faturaSelecionada.getCodFatura();
					UtilsSQLConn.mySqlDml("Delete from fatura where codFatura = "+codFatura+" ");
				} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Não selecionou nenhum dado da tabela");
					alert.setContentText("");
					alert.showAndWait();
					
				}
				layoutRoot.setCenter(layoutFatura);
				tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
				faturaSelecionada = null; // volta a meter o objeto vazio (null)
				  
			});
			
//--------------------------------------------Inserir, Alterar e Eliminar Produtos-------------------------------------------------
			//Metodo setOnAction Inserir
			btnInserirProduto.setOnAction(e->{
				layoutRoot.setCenter(FormInserirProduto());
			});
			
			//Meteodo setOnAction Alterar
			btnAlterarProduto.setOnAction(e-> {
				try {
					layoutRoot.setCenter(FormAlterarProduto());
				} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Não selecionou nenhum dado da tabela");
					alert.setContentText("");
					alert.showAndWait();
				}
				
			});
			
			//Metodo setOnAction Eliminar
			btnEliminarProduto.setOnAction(e->{
				int codProduto;
				
				ObservableList<Produto> itemSelecionado = tableProdutos.getSelectionModel().getSelectedItems();
				produtoSelecionado = itemSelecionado.get(0);
				
				try {
				
					codProduto = produtoSelecionado.getCodProduto();
					UtilsSQLConn.mySqlDml("Delete from produto where codProduto = "+codProduto+" ");
					
				} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Não selecionou nenhum dado da tabela");
					alert.setContentText("");
					alert.showAndWait();
				}
				tabelaProduto.setAll(UtilsSQLConn.mySqlQweryProduto("SELECT * FROM `produto`"));
			});
//--------------------------------------------Inserir, Alterar e Eliminar Clientes-------------------------------------------------	
			//Método para chamar o layout de Inserir Clientes
			btnInserirCliente.setOnAction(e->{
				layoutRoot.setCenter(FormInserirClientes());
			});
			
			//Método para chamar o layout de Alterar Clientes
			btnAlterarCliente.setOnAction(e->{
				try {
					layoutRoot.setCenter(FormAlterarClientes());
				}	
				catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Não selecionou nenhum dado da tabela");
					alert.setContentText("");
					alert.showAndWait();
				}
				
				
			});
			
			//Metodo para Eliminar Clientes da Tabela
			btnEliminarCliente.setOnAction(e->{
				int codCivil;
				
				ObservableList<Cliente> itemSelecionado = tableCliente.getSelectionModel().getSelectedItems();
				clienteSelecionado = itemSelecionado.get(0);
				
				try {
					
					codCivil = clienteSelecionado.getCodCivil();
					UtilsSQLConn.mySqlDml("Delete from cliente where codCivil = "+codCivil+" ");
					
				} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Não selecionou nenhum dado da tabela");
					alert.setContentText("");
					alert.showAndWait();
				}
				tabelaCliente.setAll(UtilsSQLConn.mySqlQweryCliente("SELECT * FROM `cliente`"));
			});
//---------------------------------------------------------------------------------------------------------------
			//Método do botão OK - LOGIN
			btnOk.setOnAction(e->{
				pressedText.setText("Entrando...");
				
				//Necessário para receber o que está dentro das text field e passwordfield
				checkUser = textFieldUserName.getText().toString();
				checkPw = passwordFieldPassword.getText().toString();
				
				
				//Compara (if) se a string checkUser equals(é igual) ao username e o mesmo para a password
				//Se sim entra , muda de stage
				
				if(UtilsSQLConn.mySqlQueryVerificarLogin(checkUser, checkPw)){
					primaryStage.setMinHeight(500);
					primaryStage.setMinWidth(700);
					primaryStage.setMaxHeight(564213);
					primaryStage.setMaxWidth(415623);
					primaryStage.setScene(principal);
				}
				//Senão dá um aviso
				else{
					alert.setTitle("Aviso !");
					alert.setHeaderText("Ocorreu um erro ao efectuar o login");
					alert.setContentText("Senha ou Password Incorretos");
					alert.showAndWait();
				}
				try {
					
					tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
					layoutRoot.setCenter(layoutFatura);
				} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Erro de ligação à BD ");
					alert.setContentText("O ponteiro apontou para um NULL");
					alert.showAndWait();
				}
				
			});
			registar.setOnAction(e->{
				layoutLogin.setCenter(FormLoginRegistar());
				
			});
	/*		primaryStage.setMinHeight(180);
			primaryStage.setMinWidth(330);
			primaryStage.setMaxHeight(180);
			primaryStage.setMaxWidth(330);*/
			primaryStage.setScene(login);
			primaryStage.setTitle("Faturação");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/********************************************
	 * Metodo que faz a criação da form de registar com 3 labels e text fields e 2 botões
	 * @return
	 */
	private GridPane FormLoginRegistar(){
		
		
		layoutRegistar.setAlignment(Pos.TOP_LEFT);
		layoutRegistar.setHgap(5);
		layoutRegistar.setVgap(5);
//		layoutRegistar.setPadding(new Insets(25, 25, 25, 25));
		Text registarText = new Text();
		registarText.setText("Registar");
		registarText.setFont(Font.font("Tahoma", FontWeight.MEDIUM, 20));
		
		Label lbUserName = new Label("Username");
		TextField txtUserName = new TextField();
		txtUserName.setPromptText("Introduza Username");
		
		Label lbPassword = new Label("Password");
		PasswordField txtPassword = new PasswordField();
		txtPassword.setPromptText("Introduza Password");
		
		Label lbEmail = new Label("Email");
		TextField txtEmail = new TextField();
		txtEmail.setPromptText("Introduza Email");
		
		layoutRegistar.add(registarText, 2, 0);
		layoutRegistar.add(lbUserName, 2, 2);
		layoutRegistar.add(lbPassword, 2, 3);
		layoutRegistar.add(lbEmail, 2, 4);
		
		layoutRegistar.add(txtUserName, 3, 2);
		layoutRegistar.add(txtPassword, 3, 3);
		layoutRegistar.add(txtEmail, 3, 4);
		
		HBox hbox = new HBox(60); // spacing = 60
        
       // hbox.setAlignment(Pos.BOTTOM_RIGHT);
		Button btnOk = new Button("OK");
		Button btnCancel = new Button("Cancel");
		
		hbox.getChildren().addAll(btnOk, btnCancel);
		
		layoutRegistar.add(hbox, 3,5);
		
		btnOk.setOnAction(e->{
			
			if(txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty() || txtEmail.getText().isEmpty()) {
				alert.setTitle("Erro ao Registar!");
				alert.setHeaderText("Preencha os Campos!");
				alert.showAndWait();
			}
			else{
				String user;
				String password;
				String email;
				
				user = txtUserName.getText();
				password = txtPassword.getText();
				email = txtEmail.getText();
				
				UtilsSQLConn.mySqlQueryRegistarLogin(user, password, email);
				
				alertInfo.setTitle("Sucesso!");
				alertInfo.setHeaderText("Conta criada com sucesso");
				alertInfo.setContentText("Adicionado à Base de Dados");
				alertInfo.showAndWait();
				layoutLogin.setCenter(gridLogin);
			}
		});
		
		btnCancel.setOnAction(e->{
			layoutLogin.setCenter(gridLogin);
		});
		return layoutRegistar;
	}
	/*********************************************************************************************************************
	 * Esta função é apresentada ao criar uma nova Insersão para a tabela
	 * Apresenta os dados e  campos necessários e faz a conexão com a base de dados para inserir os dados
	 * @return um Layout gridLogin Pane
	 */
	private GridPane FormFaturacaoInserir() {
		
		//Formulário Inserir
		// LABEL e textField info fatura
		//Label que fica no topo informativo
		Label lbTopo = new Label("Informações da Fatura");
		lbTopo.setFont(Font.font("Arial",FontWeight.BOLD, 12));
		//-------------
		//Label e text field do codCliente, com tooltip , se deixar o rato em cima mostra uma informação para ajuda no preenchimento
		Label lbCodCliente = new Label("Cod Cliente");
		TextField txtCodCliente = new TextField();
		txtCodCliente.setTooltip(new Tooltip("Introduza o Cliente já existente na tabela clientes, se não crie um antes de criar a fatura"));
		txtCodCliente.setPromptText("ID do cliente");
		
		//Data da Fatura com as mesmas definições de acima
		Label lbDataFatura = new Label("Data da Fatura");
		TextField txtDataFatura = new TextField();
		txtDataFatura.setPromptText("Data de Fatura");
		txtDataFatura.setTooltip(new Tooltip("Pode conter numeros, letras e caracteres alfanuméricos"));
		
		//Garantia  com as mesmas definições de acima
		Label lbGarantia = new Label("Garantia");
		TextField txtGarantia = new TextField();
		txtGarantia.setPromptText("Duração da Garantia");
		txtGarantia.setTooltip(new Tooltip("Duração da garantia, por exemplo : 2 Anos"));
		
		//Total com as mesmas definições de acima
		Label lbTotal = new Label("Total");
		TextField txtTotal = new TextField();
		txtTotal.setPromptText("Total da fatura");
		txtTotal.setTooltip(new Tooltip("Total da fatura apresentado em euros"));
		
		
		
		
		Button btnOKFatura = new  Button("OK");
		Button btnCancelFatura = new Button("Cancelar");
		//Cria um hbox por baixo do formulario para o OK e Cancelar
		HBox layoutOkCancelFatura = new HBox(50);
		layoutOkCancelFatura.getChildren().addAll(btnOKFatura, btnCancelFatura);
		
	
		
		
		layoutFormInserir.setAlignment(Pos.TOP_CENTER);
		layoutFormInserir.setHgap(10);
		layoutFormInserir.setVgap(10);
		layoutFormInserir.setPadding(new Insets(10, 20, 20, 20));
		
		//Adiciona ao layout os objetos
		layoutFormInserir.add(lbTopo, 1, 1);
		layoutFormInserir.add(lbCodCliente, 1, 2);
		layoutFormInserir.add(lbDataFatura,1,3);
		layoutFormInserir.add(lbGarantia,1,4);
		layoutFormInserir.add(lbTotal,1,5);
		
		layoutFormInserir.add(txtCodCliente, 2, 2);
		layoutFormInserir.add(txtDataFatura, 2, 3);
		layoutFormInserir.add(txtGarantia, 2, 4);
		layoutFormInserir.add(txtTotal, 2, 5);
		layoutFormInserir.add(layoutOkCancelFatura, 2, 6);
		
		btnOKFatura.setOnAction(e->{
			if(txtDataFatura.getText().isEmpty() || txtCodCliente.getText().isEmpty() || txtGarantia.getText().isEmpty() || txtTotal.getText().isEmpty())
			{
				alert.setTitle("Erro ao criar!");
				alert.setHeaderText("Preencha os Campos!");
				alert.showAndWait();
			}
			
			int ClientecodCivil;
			String dataDaFatura;
			String garantia;
			Double total;
			try {
				ClientecodCivil = Integer.parseInt(txtCodCliente.getText());
			
			
			dataDaFatura = txtDataFatura.getText();
			garantia = txtGarantia.getText();
			total = Double.parseDouble(txtTotal.getText());
			
			//Chama a função de outra classe para executar o comando Insert para a base de dados do forumlário
			UtilsSQLConn.mySqlDml("Insert into fatura"				
					+" (ClientecodCivil, DataDaFatura, Garantia,Total)"						
					+" Values('"+ClientecodCivil+"', '"+dataDaFatura+"', '"+garantia+"', '"+total+"')");
			
			//Trata da exeção de tentar converter uma string vazia em um inteiro
			} catch (NumberFormatException e2) {
				alert.setTitle("Exeption! ");
				alert.setHeaderText("Tentou converter para um numero um string vazia");
				alert.showAndWait();
			}
			tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
			layoutRoot.setCenter(layoutFatura);
		});
		
		//Cancela o preenchimento da form mas volta a atualizar a tabela
		btnCancelFatura.setOnAction(e->{
			layoutRoot.setCenter(layoutFatura);
		});
		return layoutFormInserir;
		
	
	}
	
	/*****************************************************
	 * Ação de alterar um dado  na tabela para a faturação
	 * @return
	 */
	
	private GridPane FormAlterarFaturacao() {
		
		//Formulário Inserir
				// LABEL e textField info fatura
				//Label que fica no topo informativo
				Label lbTopo = new Label("Informações da Fatura");
				lbTopo.setFont(Font.font("Arial",FontWeight.BOLD, 12));
				//-------------
				//Label e text field do codCliente, com tooltip , se deixar o rato em cima mostra uma informação para ajuda no preenchimento
				Label lbCodCliente = new Label("Cod Cliente");
				TextField txtCodCliente = new TextField();
				txtCodCliente.setTooltip(new Tooltip("Introduza o Cliente já existente na tabela clientes, se não crie um antes de criar a fatura"));
				txtCodCliente.setPromptText("ID do cliente");
				
				//Data da Fatura com as mesmas definições de acima
				Label lbDataFatura = new Label("Data da Fatura");
				TextField txtDataFatura = new TextField();
				txtDataFatura.setPromptText("Data de Fatura");
				txtDataFatura.setTooltip(new Tooltip("Pode conter numeros, letras e caracteres alfanuméricos"));
				
				//Garantia  com as mesmas definições de acima
				Label lbGarantia = new Label("Garantia");
				TextField txtGarantia = new TextField();
				txtGarantia.setPromptText("Duração da Garantia");
				txtGarantia.setTooltip(new Tooltip("Duração da garantia, por exemplo : 2 Anos"));
				
				//Total com as mesmas definições de acima
				Label lbTotal = new Label("Total");
				TextField txtTotal = new TextField();
				txtTotal.setPromptText("Total da fatura");
				txtTotal.setTooltip(new Tooltip("Total da fatura apresentado em euros"));
				
				
				Button btnOKFatura = new  Button("OK");
				Button btnCancelFatura = new Button("Cancelar");
				//Cria um hbox por baixo do formulario para o OK e Cancelar
				HBox layoutOkCancelFatura = new HBox(50);
				layoutOkCancelFatura.getChildren().addAll(btnOKFatura, btnCancelFatura);

				layoutFormAlterar.setAlignment(Pos.TOP_CENTER);
				layoutFormAlterar.setHgap(10);
				layoutFormAlterar.setVgap(10);
				layoutFormAlterar.setPadding(new Insets(10, 20, 20, 20));
				
				//Adiciona ao layout os objetos
				layoutFormAlterar.add(lbTopo, 1, 1);
				layoutFormAlterar.add(lbCodCliente, 1, 2);
				layoutFormAlterar.add(lbDataFatura,1,3);
				layoutFormAlterar.add(lbGarantia,1,4);
				layoutFormAlterar.add(lbTotal,1,5);
				
				layoutFormAlterar.add(txtCodCliente, 2, 2);
				layoutFormAlterar.add(txtDataFatura, 2, 3);
				layoutFormAlterar.add(txtGarantia, 2, 4);
				layoutFormAlterar.add(txtTotal, 2, 5);
				layoutFormAlterar.add(layoutOkCancelFatura, 2, 6);
				
				ObservableList<Faturacao> itemSelecionado = tableFatura.getSelectionModel().getSelectedItems();
				
				faturaSelecionada = itemSelecionado.get(0);
				
				
				txtCodCliente.setText(String.valueOf(faturaSelecionada.getClienteCodCivil()));
				txtDataFatura.setText(faturaSelecionada.getDataDaFatura());
				txtGarantia.setText(faturaSelecionada.getGarantia());
				txtTotal.setText(String.valueOf(faturaSelecionada.getTotal()));
				
				
				//Metodo ok executa o código SQL
				btnOKFatura.setOnAction(e->{
					//Recebe os dados contidos nas textFields
					int codFatura;					
					int ClientecodCivil;
					String dataDaFatura;
					String garantia;
					Double total;
					//O utilizador não pode alterar o código de Fatura , porém ela é reentroduzida a já existente sem alterar
					codFatura = faturaSelecionada.getCodFatura();
					//Try catch para exeções
					try {
						ClientecodCivil = Integer.parseInt(txtCodCliente.getText());
					
					
					dataDaFatura = txtDataFatura.getText();
					garantia = txtGarantia.getText();
					total = Double.parseDouble(txtTotal.getText());
					//Chama  a função de outra classe e executa o comando UDADATE na base de dados MySql
					UtilsSQLConn.mySqlDml("UPDATE `fatura` SET `codFatura`= "+codFatura+",`ClientecodCivil`= " +ClientecodCivil+ ",`DataDaFatura`= '"+dataDaFatura+"',`Garantia`= '"+garantia+"' ,`Total`= "+total+" WHERE codFatura = " +codFatura);	
					
					
					//Trata a exeção ao tentar converter uma string vazia para um numero
					} catch (NumberFormatException e2) {
						alert.setTitle("Exeption! ");
						alert.setHeaderText("Tentou converter para um numero um string vazia");
						alert.showAndWait();
					}
					//Por fim volta a apresentar a tabela com os dados atualizados e alterados
					tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
					layoutRoot.setCenter(layoutFatura);
					faturaSelecionada = null;
				});
				//Butão de cancelar no formulário volta para a Tabela voltando a fazer uma query à DB
				btnCancelFatura.setOnAction(e->{
					layoutRoot.setCenter(layoutFatura);
					//tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
				});
				
				
				
		return layoutFormAlterar;
		
	} 
	
	/************************************
	 * Form para inserir um novo Produto na BD
	 * Cria-se a form para preenchimento dos dados e depois faz-se um insert à BD
	 * @return
	 */
	private GridPane FormInserirProduto() {
		
		
		//Label do topo informativa
		Label lbTopo = new Label("Informações do Produto");
		lbTopo.setFont(Font.font("Arial",FontWeight.BOLD, 12));
		
		//Label Nome do Produto + TextField
		Label lbNomeProduto = new Label("Nome Produto");
		TextField txtNomeProduto = new TextField();
		txtNomeProduto.setPromptText("Nome do Produto");
		
		//Label Marca + Text Field
		Label lbMarca = new Label("Marca");
		TextField txtMarca = new TextField();
		txtMarca.setPromptText("Marca do Produto");
		//Label Data de Validade + Text Field
		Label lbDataValidade = new Label("Data de Validade");
		TextField txtDataDeValidade = new TextField();
		txtDataDeValidade.setPromptText("Data de Validade");
		
		//Label Preço + TextField
		Label lbPreco = new Label("Preço");
		TextField txtPreco = new TextField();
		txtPreco.setPromptText("Preço do Produto");
		
		
		//Label Stock + TextField
		Label lbStock = new Label("Stock");
		TextField txtStock = new TextField();
		txtStock.setPromptText("Quantidade em Stock");
		
		//Botões OK e Cancelar
		Button btnOkFormProduto = new Button("OK");
		Button btnCancelFormProduto = new Button("Cancelar");
		btnCancelFormProduto.setCancelButton(true);
		
		//Layout horizontal para alinhar os 2 botoes
		HBox layoutOkCancelProduto = new HBox(55);
		layoutOkCancelProduto.getChildren().addAll(btnOkFormProduto, btnCancelFormProduto);
		
		layoutFormInserirProduto.setAlignment(Pos.TOP_LEFT);
		layoutFormInserirProduto.setHgap(10);
		layoutFormInserirProduto.setVgap(10);
		layoutFormInserirProduto.setPadding(new Insets(10, 20, 20, 20));
		
		layoutFormInserirProduto.add(lbTopo, 1, 1);
		layoutFormInserirProduto.add(lbNomeProduto, 1, 2);
		layoutFormInserirProduto.add(lbMarca,1,3);
		layoutFormInserirProduto.add(lbDataValidade,1,4);
		layoutFormInserirProduto.add(lbPreco,1,5);
		layoutFormInserirProduto.add(lbStock,1,6);
		
		layoutFormInserirProduto.add(txtNomeProduto, 2, 2);
		layoutFormInserirProduto.add(txtMarca, 2,3);
		layoutFormInserirProduto.add(txtDataDeValidade,2,4);
		layoutFormInserirProduto.add(txtPreco,2,5);
		layoutFormInserirProduto.add(txtStock,2,6);
		layoutFormInserirProduto.add(layoutOkCancelProduto, 2, 7);
		
		btnOkFormProduto.setOnAction(e->{
			
			//Protege os Dados
			if(txtNomeProduto.getText().isEmpty() || txtMarca.getText().isEmpty() || txtDataDeValidade.getText().isEmpty() || txtPreco.getText().isEmpty() || txtStock.getText().isEmpty())
			{
				alert.setTitle("Erro ao criar!");
				alert.setHeaderText("Preencha os Campos!");
				alert.showAndWait();
			}
			else{
			//Strings para receberem os dados das Text Field
			String nomeProduto;
			String marca;
			String dataValidade;
			String preco;
			String stock;
			
			//Recebe os dados
			nomeProduto = txtNomeProduto.getText();
			marca = txtMarca.getText();
			dataValidade = txtDataDeValidade.getText();
			preco = txtPreco.getText();
			stock = txtStock.getText();
			try {
				UtilsSQLConn.mySqlDml("INSERT INTO `produto`(`codProduto`, `NomeProduto`, `Marca`, `DataValidade`, `Preco`, `Stock`) VALUES (Null,'"+nomeProduto+"','"+marca+"','"+dataValidade+"','"+preco+"','"+stock+"')");
			} catch (NullPointerException e2) {
				alert.setTitle("Exception ");
				alert.setHeaderText("Erro de Ligação à BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
			
			layoutRoot.setCenter(layoutProduto);
			
			try {
				tabelaProduto.setAll(UtilsSQLConn.mySqlQweryProduto("SELECT * FROM `produto`"));
			} catch (NullPointerException e2) {
				alert.setTitle("Exception ");
				alert.setHeaderText("Erro de Ligação à BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
        	
			}
		});
		
		btnCancelFormProduto.setOnAction(e->{
			layoutRoot.setCenter(layoutProduto);
		});
		
		
		return layoutFormInserirProduto;
	}
	
	private GridPane FormAlterarProduto() {
		//Label do topo informativa
		Label lbTopo = new Label("Informações do Produto");
		lbTopo.setFont(Font.font("Arial",FontWeight.BOLD, 12));
		
		//Label Nome do Produto + TextField
		Label lbNomeProduto = new Label("Nome Produto");
		TextField txtNomeProduto = new TextField();
		txtNomeProduto.setPromptText("Nome do Produto");
		
		//Label Marca + Text Field
		Label lbMarca = new Label("Marca");
		TextField txtMarca = new TextField();
		txtMarca.setPromptText("Marca do Produto");
		//Label Data de Validade + Text Field
		Label lbDataValidade = new Label("Data de Validade");
		TextField txtDataDeValidade = new TextField();
		txtDataDeValidade.setPromptText("Data de Validade");
		
		//Label Preço + TextField
		Label lbPreco = new Label("Preço");
		TextField txtPreco = new TextField();
		txtPreco.setPromptText("Preço do Produto");
		
		
		//Label Stock + TextField
		Label lbStock = new Label("Stock");
		TextField txtStock = new TextField();
		txtStock.setPromptText("Quantidade em Stock");
		
		//Botões OK e Cancelar
		Button btnOkFormProduto = new Button("OK");
		Button btnCancelFormProduto = new Button("Cancelar");
		btnCancelFormProduto.setCancelButton(true);
		btnCancelFormProduto.setCancelButton(true);
		
		//Layout horizontal para alinhar os 2 botoes
		HBox layoutOkCancelProduto = new HBox(55);
		layoutOkCancelProduto.getChildren().addAll(btnOkFormProduto, btnCancelFormProduto);
		
		layoutFormAlterarProduto.setAlignment(Pos.TOP_LEFT);
		layoutFormAlterarProduto.setHgap(10);
		layoutFormAlterarProduto.setVgap(10);
		layoutFormAlterarProduto.setPadding(new Insets(10, 20, 20, 20));
		
		layoutFormAlterarProduto.add(lbTopo, 1, 1);
		layoutFormAlterarProduto.add(lbNomeProduto, 1, 2);
		layoutFormAlterarProduto.add(lbMarca,1,3);
		layoutFormAlterarProduto.add(lbDataValidade,1,4);
		layoutFormAlterarProduto.add(lbPreco,1,5);
		layoutFormAlterarProduto.add(lbStock,1,6);
		
		layoutFormAlterarProduto.add(txtNomeProduto, 2, 2);
		layoutFormAlterarProduto.add(txtMarca, 2,3);
		layoutFormAlterarProduto.add(txtDataDeValidade,2,4);
		layoutFormAlterarProduto.add(txtPreco,2,5);
		layoutFormAlterarProduto.add(txtStock,2,6);
		layoutFormAlterarProduto.add(layoutOkCancelProduto, 2, 7);
		

		ObservableList<Produto> itemSelecionado = tableProdutos.getSelectionModel().getSelectedItems();
		
		produtoSelecionado = itemSelecionado.get(0);
		
		txtNomeProduto.setText(produtoSelecionado.getNomeProduto());
		txtMarca.setText(produtoSelecionado.getMarca());
		txtDataDeValidade.setText(produtoSelecionado.getDataValidade());
		txtPreco.setText(produtoSelecionado.getPreco());
		txtStock.setText(produtoSelecionado.getStock());
		
		btnOkFormProduto.setOnAction(e->{
			int codProduto;
			String nomeProduto;
			String marca;
			String dataValidade;
			String preco;
			String stock;
			
			codProduto = produtoSelecionado.getCodProduto();
			nomeProduto = txtNomeProduto.getText();
			marca = txtMarca.getText();
			dataValidade = txtDataDeValidade.getText();
			preco = txtPreco.getText();
			stock = txtStock.getText();
			
			
			UtilsSQLConn.mySqlDml("UPDATE `produto` SET `codProduto`= "+codProduto+",`NomeProduto`= '" +nomeProduto+ "',`Marca`= '"+marca+"',`DataValidade`= '"+dataValidade+"' ,`Preco`= '"+preco+"' ,`Stock`= '"+stock+"' WHERE codProduto = " +codProduto);
			layoutRoot.setCenter(layoutProduto);
        	tabelaProduto.setAll(UtilsSQLConn.mySqlQweryProduto("SELECT * FROM `produto`"));
        	produtoSelecionado = null;
		});
		
		btnCancelFormProduto.setOnAction(e->{
			layoutRoot.setCenter(layoutProduto);
		});
		return layoutFormAlterarProduto;
		
	}
	
	/**********************************************************************
	 * Método que faz  o Insert na tabela Clientes
	 * O metodo retorna um Layout grid com um formulário 
	 * O formulário tem  as Labels e Text Field e 2 botões, aceitar e Cancelar
	 * Ok - Fará o acesso à bd com o Comando INSERT
	 * Cancelar - Voltar ao Layout principal com a tabela dos Clientes
	 * @return
	 */
	private GridPane FormInserirClientes() {
		
		//Label do topo informativa
		Label lbTopo = new Label("Informações do Cliente");
		lbTopo.setFont(Font.font("Arial",FontWeight.BOLD, 12));
		
		//Label Nome + TextField
		Label lbNome = new Label("Nome");
		TextField txtNome = new TextField();
		txtNome.setPromptText("Nome do Cliente");
		
		//Label NIF + Text Field
		Label lbNIF = new Label("NIF");
		TextField txtNIF = new TextField();
		txtNIF.setPromptText("Número de Indentificação Físcal");
		//Label Morada + Text Field
		Label lbMorada = new Label("Morada");
		TextField txtMorada = new TextField();
		txtMorada.setPromptText("Ex: Rua das Flores nº3 4ºEsq");
		
		//Label Preço + TextField
		Label lbNIB = new Label("NIB");
		TextField txtNIB = new TextField();
		txtNIB.setPromptText("Número de Indentificação Bancário");
		
		
		//Label Stock + TextField
		Label lbNISS = new Label("NISS");
		TextField txtNISS = new TextField();
		txtNISS.setPromptText("Número de Identificação de Segurança Social");
		
		//Botões OK e Cancelar
		Button btnOkFormCliente = new Button("OK");
		Button btnCancelFormCliente = new Button("Cancelar");
		btnCancelFormCliente.setCancelButton(true);
		HBox layoutOkCancelCliente = new HBox(55);
		layoutOkCancelCliente.getChildren().addAll(btnOkFormCliente, btnCancelFormCliente);
		//Tratamento e Adição ao layout
		layoutFormInserirCliente.setAlignment(Pos.TOP_LEFT);
		layoutFormInserirCliente.setHgap(10);
		layoutFormInserirCliente.setVgap(10);
		layoutFormInserirCliente.setPadding(new Insets(10, 20, 20, 20));
		
		//Labels
		layoutFormInserirCliente.add(lbTopo, 1, 1);
		layoutFormInserirCliente.add(lbNome, 1, 2);
		layoutFormInserirCliente.add(lbNIF, 1, 3);
		layoutFormInserirCliente.add(lbMorada, 1, 4);
		layoutFormInserirCliente.add(lbNIB, 1, 5);
		layoutFormInserirCliente.add(lbNISS, 1, 6);
		//TextFields
		layoutFormInserirCliente.add(txtNome, 2, 2);
		layoutFormInserirCliente.add(txtNIF, 2, 3);
		layoutFormInserirCliente.add(txtMorada, 2, 4);
		layoutFormInserirCliente.add(txtNIB, 2, 5);
		layoutFormInserirCliente.add(txtNISS, 2, 6);
		
		//HBOX
		layoutFormInserirCliente.add(layoutOkCancelCliente, 2, 7);
		
		//Metodo que faz a insersão na BD
		btnOkFormCliente.setOnAction(e->{
			//Protege os campos para serem preenchidos
			if(txtNome.getText().isEmpty() || txtNIF.getText().isEmpty() || txtMorada.getText().isEmpty() || txtNIB.getText().isEmpty() || txtNISS.getText().isEmpty())
			{
				alert.setTitle("Erro ao criar!");
				alert.setHeaderText("Preencha os Campos!");
				alert.showAndWait();
			}
			
			//Variaveis para receberem o que está dentro das TextFields da Form
			String nome;
			int nif;
			String morada;
			int nib;
			int niss;
			
			//Recebe os dados das text fields
			nome = txtNome.getText();
			morada = txtMorada.getText();
			/*Try com 2 Catch 
				O Primeiro trata se o numero de uma textfield for muito grande ou 
				se tentou converter uma string que está vazia para um numero
				O Segundo apanha caso houver um erro de ligação
			*/ 
			try {
				nif = Integer.parseInt(txtNIF.getText());
				nib = Integer.parseInt(txtNIB.getText());
				niss = Integer.parseInt(txtNISS.getText());
				UtilsSQLConn.mySqlDml("INSERT INTO `cliente`(`codCivil`, `Nome`, `NIF`, `Morada`, `NIB`, `NISS`) VALUES (Null,'"+nome+"',"+nif+",'"+morada+"',"+nib+","+niss+")");
			} catch (NumberFormatException e2) {
				alert.setTitle("Exeption! ");
				alert.setHeaderText("Tentou converter para um numero um string vazia ou demasiado grande");
				alert.showAndWait();
				
			}
			 catch (NullPointerException e2) {
				alert.setTitle("Exception ");
				alert.setHeaderText("Erro de Ligação à BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
			
			//Apanha caso houver um ponteiro nulo
			try {
				tabelaCliente.setAll(UtilsSQLConn.mySqlQweryCliente("SELECT * FROM `cliente`"));
				layoutRoot.setCenter(layoutCliente);
			} catch (NullPointerException e2) {
				alert.setTitle("Exception ");
				alert.setHeaderText("Erro de Ligação à BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
			
			
			
			
		});
		btnCancelFormCliente.setOnAction(e->{
			layoutRoot.setCenter(layoutCliente);
		});
		return layoutFormInserirCliente;
		
		
	}
	
	private GridPane FormAlterarClientes() {

		//Label do topo informativa
		Label lbTopo = new Label("Informações do Cliente");
		lbTopo.setFont(Font.font("Arial",FontWeight.BOLD, 12));
		
		//Label Nome + TextField
		Label lbNome = new Label("Nome");
		TextField txtNome = new TextField();
		txtNome.setPromptText("Nome do Cliente");
		
		//Label NIF + Text Field
		Label lbNIF = new Label("NIF");
		TextField txtNIF = new TextField();
		txtNIF.setPromptText("Número de Indentificação Físcal");
		//Label Morada + Text Field
		Label lbMorada = new Label("Morada");
		TextField txtMorada = new TextField();
		txtMorada.setPromptText("Ex: Rua das Flores nº3 4ºEsq");
		
		//Label Preço + TextField
		Label lbNIB = new Label("NIB");
		TextField txtNIB = new TextField();
		txtNIB.setPromptText("Número de Indentificação Bancário");
		
		
		//Label Stock + TextField
		Label lbNISS = new Label("NISS");
		TextField txtNISS = new TextField();
		txtNISS.setPromptText("Número de Identificação de Segurança Social");
		
		//Botões OK e Cancelar
		Button btnOkFormCliente = new Button("OK");
		Button btnCancelFormCliente = new Button("Cancelar");
		btnCancelFormCliente.setCancelButton(true);
		HBox layoutOkCancelCliente = new HBox(55);
		layoutOkCancelCliente.getChildren().addAll(btnOkFormCliente, btnCancelFormCliente);
		//Tratamento e Adição ao layout
		layoutFormAlterarCliente.setAlignment(Pos.TOP_LEFT);
		layoutFormAlterarCliente.setHgap(10);
		layoutFormAlterarCliente.setVgap(10);
		layoutFormAlterarCliente.setPadding(new Insets(10, 20, 20, 20));
		
		//Labels
		layoutFormAlterarCliente.add(lbTopo, 1, 1);
		layoutFormAlterarCliente.add(lbNome, 1, 2);
		layoutFormAlterarCliente.add(lbNIF, 1, 3);
		layoutFormAlterarCliente.add(lbMorada, 1, 4);
		layoutFormAlterarCliente.add(lbNIB, 1, 5);
		layoutFormAlterarCliente.add(lbNISS, 1, 6);
		//TextFields
		layoutFormAlterarCliente.add(txtNome, 2, 2);
		layoutFormAlterarCliente.add(txtNIF, 2, 3);
		layoutFormAlterarCliente.add(txtMorada, 2, 4);
		layoutFormAlterarCliente.add(txtNIB, 2, 5);
		layoutFormAlterarCliente.add(txtNISS, 2, 6);
		
		//HBOX
		layoutFormAlterarCliente.add(layoutOkCancelCliente, 2, 7);
		
		ObservableList<Cliente> itemSelecionado = tableCliente.getSelectionModel().getSelectedItems();
		
		clienteSelecionado = itemSelecionado.get(0);
		
		
		//Variaveis que recebem os dados do indice selecionado
		
		txtNome.setText(clienteSelecionado.getNome());
		txtNIF.setText(String.valueOf(clienteSelecionado.getNIF()));
		txtMorada.setText(clienteSelecionado.getMorada());
		txtNIB.setText(String.valueOf(clienteSelecionado.getNIB()));
		txtNISS.setText(String.valueOf(clienteSelecionado.getNISS()));
				
		btnOkFormCliente.setOnAction(e->{
			int codCivil;
			String nome;
			int nif;
			String morada;
			int nib;
			int niss;
			
			codCivil = clienteSelecionado.getCodCivil();
			nome = txtNome.getText();
			morada = txtMorada.getText();
			try {
				nif = Integer.parseInt(txtNIF.getText());
				nib = Integer.parseInt(txtNIB.getText());
				niss = Integer.parseInt(txtNISS.getText());
				UtilsSQLConn.mySqlDml("UPDATE `cliente` SET `codCivil`= "+codCivil+",`Nome`= '" +nome+ "',`NIF`= "+nif+",`Morada`= '"+morada+"' ,`NIB`= "+nib+" ,`NISS`= "+niss+" WHERE codCivil = " +codCivil);
			} catch (NumberFormatException e2) {
				alert.setTitle("Exeption! ");
				alert.setHeaderText("Tentou converter para um numero um string vazia ou demasiado grande");
				alert.showAndWait();
				
			}
			 catch (NullPointerException e2) {
				alert.setTitle("Exception ");
				alert.setHeaderText("Erro de Ligação à BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
			//Apanha caso houver um ponteiro nulo
			try {
				tabelaCliente.setAll(UtilsSQLConn.mySqlQweryCliente("SELECT * FROM `cliente`"));
				layoutRoot.setCenter(layoutCliente);
			} catch (NullPointerException e2) {
				alert.setTitle("Exception ");
				alert.setHeaderText("Erro de Ligação à BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
		});
		return layoutFormAlterarCliente;
		
	}
	
	
}
