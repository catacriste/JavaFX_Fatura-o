package application;
	
import java.awt.CheckboxMenuItem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
	static Faturacao faturaSelecionada = null;
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {
		
		try {
			//----------------------ALERT DIALOG---------------------
			Alert alert = new Alert(AlertType.ERROR);
			Alert alertInfo = new Alert(AlertType.INFORMATION);
			
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
			
			
			//Condição que ativa e desativa as mensagens
			on.setOnAction(e->{
				if(on.isSelected())
				{
					UtilsSQLConn.msgON = true;
				}	
			});
			off.setOnAction(e->{
				if (off.isSelected()) {
					UtilsSQLConn.msgON = false;
				}
			});
			
			
			
			MenuBar menuBar = new MenuBar();
			menuBar.setStyle("-fx-background-color: #33CC33");
	        //Adiciona os menus ao menuBar
	        menuBar.getMenus().addAll(menuFatura, menuProduto,menuCliente,msg);
			//------------------------------JANELA E LAYOUT-----------------------------------------------------
			// TODO : Configurar os layouts e janela
//--------------------------------------------------TABLE DAS FATURAS----------------------------------------------------
	        //TableViews - Criação das tabelas
	        TableView<Faturacao> tableFatura = new TableView<>();
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
	        hBoxFatura.setStyle("-fx-background-color: #006600");
	        
	        //Os 3 Butões , inserir ,alterar e eliminar
	        Button btnInserirFatura = new Button("Inserir");
	        //btnInserirFatura.setPadding(value);
	        Button btnAlterarFatura = new Button("Alterar");
	        Button btnEliminarFatura = new Button("Eliminar");
	       
	        //Adiciona os butoes a HBOX e Hbox ao Buttom do Border Pane
	        hBoxFatura.getChildren().addAll(btnInserirFatura,btnAlterarFatura,btnEliminarFatura);
			BorderPane layoutFatura = new BorderPane();
	        
	      //layoutFatura.getChildren().add(tableFatura);
			layoutFatura.setCenter(tableFatura);
			layoutFatura.setBottom(hBoxFatura);
			
 //--------------------------------------------------TABLE DOS PRODUTOS----------------------------------------------------
	      //TableViews - Criação das tabelas
	        TableView<Produto> tableProdutos = new TableView<>();
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
			BorderPane layoutProduto = new BorderPane();
	        
			//layoutProduto.getChildren().add(tableProdutos);
			layoutProduto.setCenter(tableProdutos);;
			
			 //--------------------------------------------------TABLE DOS Cliente----------------------------------------------------
		      //TableViews - Criação das tabelas
		        TableView<Cliente> tableCliente = new TableView<>();
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
				BorderPane layoutCliente = new BorderPane();
		        
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
			BorderPane layoutLogin = new BorderPane();
			
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
			
			// label info cliente
			Label lbCliente = new Label("Registar o Cliente");
			Label lbNome = new Label("Nome");
			Label lbNIF = new Label("NIF");
			Label lbMorada = new Label("Morada");
			Label lbNIB = new Label("NIB");
			Label lbNISS = new Label("NISS");
			
			
			GridPane layoutForm = new GridPane();
			Button btnOKFatura = new  Button("OK");
			Button btnCancelFatura = new Button("Cancelar");
			//Cria um hbox por baixo do formulario para o OK e Cancelar
			HBox layoutOkCancelFatura = new HBox(50);
			layoutOkCancelFatura.getChildren().addAll(btnOKFatura, btnCancelFatura);
			
		
			
			
			layoutForm.setAlignment(Pos.TOP_CENTER);
			layoutForm.setHgap(10);
			layoutForm.setVgap(10);
			layoutForm.setPadding(new Insets(10, 20, 20, 20));
			
			//Adiciona ao layout os objetos
			layoutForm.add(lbTopo, 1, 1);
			layoutForm.add(lbCodCliente, 1, 2);
			layoutForm.add(lbDataFatura,1,3);
			layoutForm.add(lbGarantia,1,4);
			layoutForm.add(lbTotal,1,5);
			
			layoutForm.add(txtCodCliente, 2, 2);
			layoutForm.add(txtDataFatura, 2, 3);
			layoutForm.add(txtGarantia, 2, 4);
			layoutForm.add(txtTotal, 2, 5);
			layoutForm.add(layoutOkCancelFatura, 2, 6);
			//--------------------------------------------------SCENE E LAYOUT -------------------------------------------
	        //Border Pane
			BorderPane layoutRoot = new BorderPane();
			layoutRoot.setTop(menuBar);
			//layoutRoot.setBottom(btnInserir);
			//Scene ( Janela) Principal------
			Scene principal = new Scene(layoutRoot,700,500);
			Scene login = new Scene(layoutLogin,330,180);
		//	Scene fatura = new Scene(layoutFatura, 400,400);
			
			
			//-------------------MENUS and BUTTONS EVENT HANDLERS ----------------------
			menuFaturaMostrar.setOnAction(e->{
	        	layoutRoot.setCenter(layoutFatura);
	        	tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
	        	
	        });
			menuProdutoMostrar.setOnAction(e->{
	        	layoutRoot.setCenter(layoutProduto);
	        	tabelaProduto.setAll(UtilsSQLConn.mySqlQweryProduto("SELECT * FROM `produto`"));
	        	
	        });
			
			menuClienteMostrar.setOnAction(e->{
	        	layoutRoot.setCenter(layoutCliente);
	        tabelaCliente.setAll(UtilsSQLConn.mySqlQweryCliente("SELECT * FROM `cliente`"));
	        	
	        });
			
			//BUTÕES
			btnInserirFatura.setOnAction(e->{
			layoutRoot.setCenter(layoutForm);
			});
			
			
			//Event handler de os botões da form da criação da Fatura  Ok e Cancelar
			
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
				
				ClientecodCivil = Integer.parseInt(txtCodCliente.getText());
				dataDaFatura = txtDataFatura.getText();
				garantia = txtGarantia.getText();
				total = Double.parseDouble(txtTotal.getText());
				UtilsSQLConn.mySqlDml("Insert into fatura"				// Tabela Aluno
						+" (ClientecodCivil, DataDaFatura, Garantia,Total)"						// Nomes Colunas
						+" Values('"+ClientecodCivil+"', '"+dataDaFatura+"', '"+garantia+"', '"+total+"')");	// Dados
				
				tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
				layoutRoot.setCenter(layoutFatura);
			});
			
			
			btnCancelFatura.setOnAction(e->{
				layoutRoot.setCenter(layoutFatura);
			});
			
//EVENTOS DOS BOTÕES ALTERAR E OK DA FORM DO ALTERAR -----------------------------------------------------------------------------
/*	//FORM REPETIDO APENAS PARA A ALTERAÇÃO Das Faturas
			GridPane layoutFormFaturaAlterar = new GridPane();
			Button btnOKFaturaAlterar = new  Button("OK");
			Button btnCancelFaturaAlterar = new Button("Cancelar");
			//Cria um hbox por baixo do formulario para o OK e Cancelar
			HBox layoutOkCancelFaturaAlterar = new HBox(50);
			layoutOkCancelFaturaAlterar.getChildren().addAll(btnOKFaturaAlterar, btnCancelFaturaAlterar);
			
		
			
			
			layoutFormFaturaAlterar.setAlignment(Pos.TOP_CENTER);
			layoutFormFaturaAlterar.setHgap(10);
			layoutFormFaturaAlterar.setVgap(10);
			layoutFormFaturaAlterar.setPadding(new Insets(10, 20, 20, 20));
			
			//Adiciona ao layout os objetos
			layoutFormFaturaAlterar.add(lbTopo, 1, 1);
			layoutFormFaturaAlterar.add(lbCodCliente, 1, 2);
			layoutFormFaturaAlterar.add(lbDataFatura,1,3);
			layoutFormFaturaAlterar.add(lbGarantia,1,4);
			layoutFormFaturaAlterar.add(lbTotal,1,5);
			
			layoutFormFaturaAlterar.add(txtCodCliente, 2, 2);
			layoutFormFaturaAlterar.add(txtDataFatura, 2, 3);
			layoutFormFaturaAlterar.add(txtGarantia, 2, 4);
			layoutFormFaturaAlterar.add(txtTotal, 2, 5);
			layoutFormFaturaAlterar.add(layoutOkCancelFaturaAlterar, 2, 6);
			
			
			*/
			
			btnAlterarFatura.setOnAction(e->{

				ObservableList<Faturacao> itemSelecionado = tableFatura.getSelectionModel().getSelectedItems();
				
				faturaSelecionada = itemSelecionado.get(0);
				
				
				txtCodCliente.setText(String.valueOf(faturaSelecionada.getClienteCodCivil()));
			
			
			
				
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
					
					alert.setTitle("Deu");
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
	
	private GridPane FormFaturacaoInserir(ObservableList<Faturacao> itemSelecionado ) {
		
	return null;
	
	
	}
	
	
	
}
