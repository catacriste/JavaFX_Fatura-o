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
	String userName = "";				//Define o utilizador 
	String password = "";				//Define a password
	String checkUser, checkPw;				//2 Strings para verificar o login posteriormente
	ObservableList<Faturacao> 	tabelaFaturas = FXCollections.observableArrayList();
	ObservableList<Produto> 	tabelaProduto = FXCollections.observableArrayList();
	ObservableList<Cliente> 	tabelaCliente = FXCollections.observableArrayList();
	boolean msgOn = false;
	//Outros
	
	static Faturacao faturaSelecionada = null;
	static Produto produtoSelecionado = null;
	//----------------------ALERT DIALOG---------------------
	Alert alert = new Alert(AlertType.ERROR);
	Alert alertInfo = new Alert(AlertType.INFORMATION);
	
	
	//Todos os layouts do projeto
	BorderPane layoutLogin = new BorderPane();
	GridPane layoutFormInserir = new GridPane();
	GridPane layoutFormAlterar = new GridPane();
	GridPane layoutFormInserirProduto = new GridPane();
	GridPane layoutFormAlterarProduto = new GridPane();
	BorderPane layoutProduto = new BorderPane();
	BorderPane layoutCliente = new BorderPane();
	BorderPane layoutFatura = new BorderPane();
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
				
				layoutCliente.setCenter(tableCliente);
			
			
			
			
			//Layout root e outros layouts ----
	        
	        //VBOX CENTRAL
	       /* VBox layoutCenter = new VBox();
	        layoutCenter.setPadding(new Insets(150,100,100,150));
	        Button btnEntrar = new Button("Entrar");
	        Button btnQuit = new Button("Sair");
	        
	        layoutCenter.getChildren().addAll(btnEntrar, btnQuit);*/
	        //-------------------------------------login-----------------------------------------
			Button btnOk = new Button("OK");

	        //Layout GRID
			
			
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
			
			btnOk.setDefaultButton(true);
			
			//--------------------------------------------------SCENE E LAYOUT -------------------------------------------
	        //Border Pane
			
			layoutRoot.setTop(menuBar);
			//layoutRoot.setBottom(btnInserir);
			//Scene ( Janela) Principal------
			Scene principal = new Scene(layoutRoot,700,500);
			Scene login = new Scene(layoutLogin,330,180);
		//	Scene fatura = new Scene(layoutFatura, 400,400);
			
			
			//-------------------MENUS and BUTTONS EVENT HANDLERS ----------------------
			menuFaturaMostrar.setOnAction(e->{
	        	layoutRoot.setCenter(layoutFatura);
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
//---------------------------------------------------------------------------------------------------------------
			//Método do botão OK - LOGIN
			btnOk.setOnAction(e->{
				
				//Necessário para receber o que está dentro das text field e passwordfield
				checkUser = textFieldUserName.getText().toString();
				checkPw = passwordFieldPassword.getText().toString();
				
				
				//Compara (if) se a string checkUser equals(é igual) ao username e o mesmo para a password
				//Se sim entra , muda de stage
				
				if(checkUser.equals(userName)  && checkPw.equals(password)){
					
					alertInfo.setTitle("Deu");
					alertInfo.showAndWait();
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
					layoutRoot.setCenter(layoutFatura);
					tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
				} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Erro de ligação à BD ");
					alert.setContentText("O ponteiro apontou para um NULL");
					alert.showAndWait();
				}
				
			});
			primaryStage.setMinHeight(180);
			primaryStage.setMinWidth(330);
			primaryStage.setMaxHeight(180);
			primaryStage.setMaxWidth(330);
			primaryStage.setScene(login);
			primaryStage.setTitle("Faturação");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/*********************************************************************************************************************
	 * Esta função é apresentada ao criar uma nova Insersão para a tabela
	 * Apresenta os dados e  campos necessários e faz a conexão com a base de dados para inserir os dados
	 * @return um Layout Grid Pane
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
				});
				
				btnCancelFormProduto.setOnAction(e->{
					layoutRoot.setCenter(layoutProduto);
				});
		return layoutFormAlterarProduto;
		
	}
	
	
	
}
