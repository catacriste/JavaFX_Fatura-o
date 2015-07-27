package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
import javafx.util.Callback;
//--------------------------SUM�RIO--------------------------------------
/*Aplica��o destinada ao projeto de Fatura��o , para registar , alterar e eliminar faturas e produtos
 *Com acesso a uma base de dados , ser�o usados comandos SQL para inserir dados na base de dados eliminar e alterar
 *A fatura tem campos como Data, garantia e loja  e necessita de dados do cliente que s�o : Nome ,NIF ,Morada, NIB, NISS e codCivil
 *Tamb�m necessita de alguns campos de Produto , pois sem produto n�o h� fatura
 */

public class Main extends Application {
	
//Login
//	String userName = "";				//Define o utilizador 
//	String password = "";				//Define a password
	String checkUser, checkPw;				//2 Strings para verificar o login posteriormente
	ObservableList<Faturacao> 	tabelaFaturas = FXCollections.observableArrayList();
	ObservableList<Produto> 	tabelaProduto = FXCollections.observableArrayList();
	ObservableList<Cliente> 	tabelaCliente = FXCollections.observableArrayList();
	ObservableList<FaturaProduto> 	tabelaFaturaProduto = FXCollections.observableArrayList();
	boolean msgOn = false;
	//Outros
	//Objeto para receber o indice selecionado na tabela
	static Faturacao faturaSelecionada = null;
	static Produto produtoSelecionado = null;
	static Cliente clienteSelecionado = null;
	static FaturaProduto faturaProdutoSelecionado = null;
	int PosFaturaProduto;//Recebe a posi��o
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
	TableView<FaturaProduto> tableFaturaProduto = new TableView<>();
	
	//Janela Fatura -Produto
	BorderPane layoutFaturaProduto = new BorderPane();
	Scene sceneFaturaProduto = new Scene(layoutFaturaProduto);
	Stage janelaFaturaProduto = new Stage(); 
	ComboBox<Produto> cb;

	
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			
			/*---------------------------------------MENU-------------------------------*/
			//TODO : Fazer o menu
			//MENU FATURACAO
			Menu menuFatura = new Menu("Fatura��o");
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
			//Toggle para deselecionar quando nao necess�rio
			ToggleGroup onOffToogle = new ToggleGroup();
			on.setToggleGroup(onOffToogle);
			off.setToggleGroup(onOffToogle);
			on.setSelected(msgOn);
			
			
			//Condi��o que ativa e desativa as mensagens
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
			
			
			
			MenuBar menuBar = new MenuBar();			//Cri��o da menuBar
			menuBar.setStyle("-fx-background-color: #0099FF");
	        //Adiciona os menus ao menuBar
	        menuBar.getMenus().addAll(menuFatura, menuProduto,menuCliente,msg);
			//------------------------------JANELA E LAYOUT-----------------------------------------------------
			// TODO : Configurar os layouts e janela
//--------------------------------------------------TABLE DAS FATURAS----------------------------------------------------
	        //TableViews - Cria��o das tabelas
	       
	        //Coluna Do Indice
	        TableColumn<Faturacao, Integer> colunaIDFaturacao = new TableColumn<>("�ndice");
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
	        /*
	        tableFatura.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	            if (newSelection != null) {
	                alertInfo.setContentText("ola " + newSelection.getCodFatura());
	                alertInfo.showAndWait();
	            }
	        });*/
	        
	        janelaFaturaProduto.setMaxHeight(300);
	        janelaFaturaProduto.setMaxWidth(400); 
	        
	        janelaFaturaProduto.setMinHeight(300);
	        janelaFaturaProduto.setMinWidth(400); 
	        
	        
	        //M�todo que determina event click em cada row da tabela fatur
	        //If para apenas executar se ouver double click
	        tableFatura.setRowFactory( tv -> {
	        	//Define Modality para a janela FaturaProdutos
	    		janelaFaturaProduto.initModality(Modality.APPLICATION_MODAL);
	    		TableRow<Faturacao> rowSelecionda = new TableRow<>();
	    		rowSelecionda.setTooltip(new Tooltip("Clique duas vezes para ver os produtos ou adicionar novos � fatura"));
	            rowSelecionda.setOnMouseClicked(event -> {
	            if (event.getClickCount() == 2 && (! rowSelecionda.isEmpty()) ) {
	            	
	            	//Para receber a row selecionada na tabela das taturas
	            	ObservableList<Faturacao> itemSelecionado = tableFatura.getSelectionModel().getSelectedItems();
	            	PosFaturaProduto = itemSelecionado.get(0).getCodFatura();
	            	cb.setItems(UtilsSQLConn.mySqlQweryProduto("SELECT * FROM `produto`"));  //Select � db
	            	//Select a tabela fatura-produto
	            	tabelaFaturaProduto.setAll(UtilsSQLConn.mySqlQweryFaturaProduto("SELECT `FaturacodFatura`,`NomeProduto`, `codProduto` FROM `fatura_produto`, `produto` WHERE FaturacodFatura = "+PosFaturaProduto+" AND ProdutocodProduto = codProduto"));
	            	
	            	janelaFaturaProduto.setScene(sceneFaturaProduto);
	            	janelaFaturaProduto.show();
	
	            }
	            });
	            	return rowSelecionda ;
	        });
	        tableFaturaProduto.setItems(tabelaFaturaProduto);
	        
	     
	        
	        //HBOX PARA OS BUTOES
	        
	        HBox hBoxFatura = new HBox(10);
	        hBoxFatura.setPadding(new Insets(10,0,10,250));
	        hBoxFatura.setStyle("-fx-background-color: #005CB8");
	        
	        //Os 3 But�es , inserir ,alterar e eliminar
	        Button btnInserirFatura = new Button("Inserir");
	        //btnInserirFatura.setPadding(value);
	        Button btnAlterarFatura = new Button("Alterar");
	        Button btnEliminarFatura = new Button("Eliminar");
	       
	        //Adiciona os butoes a HBOX e Hbox ao Buttom do Border Pane
	        hBoxFatura.getChildren().addAll(btnInserirFatura,btnAlterarFatura,btnEliminarFatura);
			//Adiciona ao layout
			layoutFatura.setCenter(tableFatura);
			layoutFatura.setBottom(hBoxFatura);
			
//---------------------------------------------------Table FATURA - PRODUTO
			 //Coluna Do Indice
	        TableColumn<FaturaProduto, Integer> colunaIDFaturacodFatura = new TableColumn<>("�ndice");
	        //Determina a que atributo corresponde na classe
	        colunaIDFaturacodFatura.setCellValueFactory(new PropertyValueFactory<>("faturaCodFatura"));
	        //Coluna do Nome do Produto
	        TableColumn<FaturaProduto, String> colunaFaturaNomeProduto = new TableColumn<>("Nome Produto");
	        //Determina a que atributo pretence � classe
	        colunaFaturaNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
	    	colunaFaturaNomeProduto.setMinWidth(200);		 //Tamanho da coluna
			
			tableFaturaProduto.getColumns().addAll(colunaIDFaturacodFatura,colunaFaturaNomeProduto);
			//HBOX Fatura-Produto
			 HBox hBoxFaturaProduto = new HBox(10);
			 hBoxFaturaProduto.setPadding(new Insets(10,0,10,10));
			 hBoxFaturaProduto.setStyle("-fx-background-color: #005CB8");
			 //Os 3 But�es , inserir ,alterar e eliminar
		     Button btnInserirFaturaProduto = new Button("Inserir");
		     Button btnAlterarFaturaProduto = new Button("Alterar");
		     Button btnEliminarFaturaProduto = new Button("Eliminar");
		       

	        //Adiciona os butoes a HBOX e Hbox ao Buttom do Border Pane
	       
			//Combo box que faz quero � base de dados para receber a lista de itens a escoher
		     
	        cb = new ComboBox<>();
	        //Tratamento de exec��es
			try {
				cb.setItems(UtilsSQLConn.mySqlQweryProduto("SELECT * FROM `produto`"));				//Faz select � tabela produtos
				
			}
			//Catch Ponteiro Nulo (NullPointerException)
			catch (NullPointerException e3) {
				alert.setTitle("Exception ");
				alert.setHeaderText("Erro de Liga��o � BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
			
			
			cb.setCellFactory( new Callback<ListView<Produto>, ListCell<Produto>>() {				//List cell s�o cada c�lula com dados 
						//M�todo override que lista cada c�lula																		
		                @Override public ListCell<Produto> call(ListView<Produto> param) {
		                    final ListCell<Produto> cell = new ListCell<Produto>() {
		                        {
		                            super.setPrefWidth(100);
		                        }    
		                        //Override que atualiza os itens
		                        @Override public void updateItem(Produto item, 
		                            boolean empty) {
		                                super.updateItem(item, empty);
		                                if (item != null) {
		                                    setText(item.getNomeProduto());    			//Define o item que � o nome de cada produto
		                                }
		                                else {
		                                    setText(null);
		                                }
		                            }
		                };
		                return cell;		//Retorna  A lista de itens
		            }
		        });
			//Ap�s selecionar atribui o campo do nome da Cbox com a selecionada na lista
			cb.setButtonCell(new ListCell<Produto>(){

				@Override
				//Recebe Objeto e um booelan para atualizar o nome da celula principal da Combobox de acordo com a selecionada
				protected void updateItem(Produto t, boolean bln) {
					super.updateItem(t, bln);
					if (bln) {
	                    setText("");
	                } else {
	                	setText(t.getNomeProduto());						 //Atualiza com o nome do produto da classe Produto e tabela produto da bd
	                }
				}

        		
        	});
			//Define o layout
			layoutFaturaProduto.setCenter(tableFaturaProduto);
			layoutFaturaProduto.setBottom(hBoxFaturaProduto);
			
			//Adiciona aos layouts HorizontalBox
			hBoxFaturaProduto.getChildren().addAll(cb, btnInserirFaturaProduto, btnAlterarFaturaProduto, btnEliminarFaturaProduto);
			
			
			
			
			
			
			
 //--------------------------------------------------TABLE DOS PRODUTOS----------------------------------------------------
	      //TableViews - Cria��o das tabelas
	      
	        //Coluna Do Indice
	        TableColumn<Produto, Integer> colunaIDProduto = new TableColumn<>("�ndice");
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
	        
	        TableColumn<Produto, String> colunaPreco = new TableColumn<>("Pre�o");
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
	        
	        //Os 3 But�es , inserir ,alterar e eliminar
	        Button btnInserirProduto = new Button("Inserir");
	        //btnInserirFatura.setPadding(value);
	        Button btnAlterarProduto = new Button("Alterar");
	        Button btnEliminarProduto = new Button("Eliminar");
	       
	        //Adiciona os butoes a HBOX e Hbox ao Buttom do Border Pane
	        hBoxProduto.getChildren().addAll(btnInserirProduto,btnAlterarProduto,btnEliminarProduto);
			
	        
	        
	        //Define o layout
			layoutProduto.setCenter(tableProdutos);;
			layoutProduto.setBottom(hBoxProduto);
			
			 //--------------------------------------------------TABLE DOS Cliente----------------------------------------------------
		      //TableViews - Cria��o das tabelas
		     
		        //Coluna 
		        TableColumn<Cliente, Integer> colunaIDCliente = new TableColumn<>("C�digo Civil");
		        colunaIDCliente.setCellValueFactory(new PropertyValueFactory<>("codCivil"));
		        
		        //Coluna 
		        TableColumn<Cliente, String> colunaNome = new TableColumn<>("Nome");
		        colunaNome.setMinWidth(100);		//largura em pixeis da coluna
		        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		        
		        //Coluna
		        TableColumn<Cliente, Integer> colunaNIF = new TableColumn<>("NIF");
		        colunaNIF.setCellValueFactory(new PropertyValueFactory<>("NIF"));
		        
		        // Coluna
		        TableColumn<Cliente, String> colunaMorada = new TableColumn<>("Morada");
		        colunaMorada.setMinWidth(0);		//largura em pixeis da coluna
		        colunaMorada.setCellValueFactory(new PropertyValueFactory<>("morada"));
		        
		        // Coluna
		        TableColumn<Cliente, Integer> colunaNIB = new TableColumn<>("NIB");
		        colunaNIB.setCellValueFactory(new PropertyValueFactory<>("NIB"));
		        
		        // Coluna
		        TableColumn<Cliente, Integer> colunaNISS = new TableColumn<>("NISS");
		        colunaNISS.setCellValueFactory(new PropertyValueFactory<>("NISS"));
		        
		        
		        //Adiciona as colunas � TableView e seleciona os itens recebeidos na observableList
		        tableCliente.getColumns().addAll(colunaIDCliente,colunaNome,colunaNIF,colunaMorada, colunaNIB , colunaNISS);
		        tableCliente.setItems(tabelaCliente);
			
		        
				//layoutCliente.getChildren().add(tableCliente);
				
				//Hbox do Cliente
				HBox hBoxCliente = new HBox(10);
				hBoxCliente.setPadding(new Insets(10,0,10,250));
				hBoxCliente.setStyle("-fx-background-color: #005CB8");
		        
		        //Os 3 But�es , inserir ,alterar e eliminar
		        Button btnInserirCliente = new Button("Inserir");
		        //btnInserirFatura.setPadding(value);
		        Button btnAlterarCliente = new Button("Alterar");
		        Button btnEliminarCliente = new Button("Eliminar");
		       
		        //Adiciona os butoes a HBOX e Hbox ao Buttom do Border Pane
		        hBoxCliente.getChildren().addAll(btnInserirCliente,btnAlterarCliente,btnEliminarCliente);
				layoutCliente.setCenter(tableCliente);
				layoutCliente.setBottom(hBoxCliente);
			
//-------------------------------------------------------------LOGIN----------------------------------------------------------
				
		//---------------------------FORM LOGIN--------------------------------------
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

	//---------------------------FORM LOGIN END--------------------------------------	
			        
			        
			//--------------------------------------------------SCENE E LAYOUT -------------------------------------------
	        //Border Pane
			
			layoutRoot.setTop(menuBar);
			//Scenes principais do programa
			Scene principal = new Scene(layoutRoot,700,500);
			Scene login = new Scene(layoutLogin,300,250);
			
			//-------------------MENUS and BUTTONS EVENT HANDLERS ----------------------
			/***
			 * M�todos que definem o setonaction dos Menus , cada um faz literalmente a mesma coisa
			 * 1� Define o layout correspondente
			 * 2� Faz uma query A base de dados para preencher as tabelas de cada 1
			 */
			menuFaturaMostrar.setOnAction(e->{
	        	layoutRoot.setCenter(layoutFatura);
	        	primaryStage.setTitle("Fatura��o");
	        	try {
	        		tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
				
	        	} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Erro de Liga��o � BD");
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
					alert.setHeaderText("Erro de Liga��o � BD");
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
				alert.setHeaderText("Erro de Liga��o � BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
	        	
	        });
			
//----------------------------------------------Eventos de Inserir , Alterar e Eliminar Faturas-----------------------------------------------------
			/***
			 * M�todos Inserir Alterar Eliminar Faturas
			 * Cada um chama uma fun��o que faz a insers�o e altera��o
			 * Eliminar � feito aqui pois � facil e mais pr�tico
			 */
			
			//Inserir
			btnInserirFatura.setOnAction(e->{
				layoutRoot.setCenter(FormFaturacaoInserir());		 //Chama o m�todo
			});

			//Alterar
			btnAlterarFatura.setOnAction(e->{
				try {
					layoutRoot.setCenter(FormAlterarFaturacao());
				} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("N�o selecionou nenhum dado da tabela");
					alert.setContentText("");
					alert.showAndWait();
				}
				
			});
			//Eliminar
			btnEliminarFatura.setOnAction(e->{
				//Efetua a elimina��o de dados numa tabela
				int codFatura; 				//Vari�vel para usar no comando

				ObservableList<Faturacao> itemSelecionado = tableFatura.getSelectionModel().getSelectedItems();
				faturaSelecionada = itemSelecionado.get(0);
				
				try {
					codFatura = faturaSelecionada.getCodFatura();
					UtilsSQLConn.mySqlDml("Delete from fatura where codFatura = "+codFatura+" ");
				} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("N�o selecionou nenhum dado da tabela");
					alert.setContentText("");
					alert.showAndWait();
					
				}
				layoutRoot.setCenter(layoutFatura);
				tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
				faturaSelecionada = null; // volta a meter o objeto vazio (null)
				  
			});
//--------------------------------------------Inserir, Alterar e Eliminar Faturas-Produtos-------------------------------------------------
			
			/********************
			 * M�todo do bot�o Inserir na Layout Fatura-Produto
			 * O m�todo Event Handler Usa uma observable list para receber  posi��o na lista da fatura e o codFatura 
			 * Faz um insert na base de dados dependendo da fatura selecionda e o codigo de procudo selecionado na ComboBox
			 */
			btnInserirFaturaProduto.setOnAction(e->{
				
				try {
					ObservableList<Faturacao> itemSelecionado = tableFatura.getSelectionModel().getSelectedItems();
					faturaSelecionada = itemSelecionado.get(0);
					int codProduto;
					codProduto = cb.getSelectionModel().getSelectedItem().getCodProduto();
					//SQL INSERT DATABSE -> fatura_produto
					UtilsSQLConn.mySqlDml("INSERT INTO `fatura_produto`(`FaturacodFatura`, `ProdutocodProduto`) VALUES (" +  faturaSelecionada.getCodFatura() + ","+codProduto+")");
					//Select na BD PARA atualizar a tabela Fatura-Produto com os novos produtos 
					tabelaFaturaProduto.setAll(UtilsSQLConn.mySqlQweryFaturaProduto("SELECT `FaturacodFatura`,`NomeProduto`, `codProduto` FROM `fatura_produto`, `produto` WHERE FaturacodFatura = "+PosFaturaProduto+" AND ProdutocodProduto = codProduto"));
				
				} catch (NullPointerException e1) {
					alert.setTitle("Exception ");
					alert.setHeaderText("N�o selecionou nenhum produto na comboBox");
					alert.setContentText("� necess�rio ter produtos criados antes de adicionar produtos a esta fatura");
					alert.showAndWait();
				}
			});
		
			btnAlterarFaturaProduto.setOnAction(e->{
				
				try {
					ObservableList<Faturacao> faturaSelecionado = tableFatura.getSelectionModel().getSelectedItems();
					faturaSelecionada = faturaSelecionado.get(0);
					ObservableList<FaturaProduto> faturaProdutoSelected = tableFaturaProduto.getSelectionModel().getSelectedItems();
					faturaProdutoSelecionado =  faturaProdutoSelected.get(0);
					int codProduto;
					codProduto = cb.getSelectionModel().getSelectedItem().getCodProduto();
					//UPDATE `fatura_produto` SET`ProdutocodProduto`=[value-2] WHERE FaturacodFatura = 1 AND ProdutocodProduto = 1
					//tabelaFaturaProduto.setAll(UtilsSQLConn.mySqlQweryFaturaProduto("SELECT `FaturacodFatura`,`NomeProduto` FROM `fatura_produto`, `produto` WHERE FaturacodFatura = "+PosFaturaProduto+" AND ProdutocodProduto = codProduto"));
					UtilsSQLConn.mySqlDml("UPDATE `fatura_produto` SET`ProdutocodProduto`="+codProduto+" WHERE FaturacodFatura = "+faturaSelecionada.getCodFatura()+" AND ProdutocodProduto = " + faturaProdutoSelecionado.getProdutoCodProduto());
					tabelaFaturaProduto.setAll(UtilsSQLConn.mySqlQweryFaturaProduto("SELECT `FaturacodFatura`,`NomeProduto`, `codProduto` FROM `fatura_produto`, `produto` WHERE FaturacodFatura = "+PosFaturaProduto+" AND ProdutocodProduto = codProduto"));
					//System.out.println("SELECT `FaturacodFatura`,`NomeProduto`, `codProduto` FROM `fatura_produto`, `produto` WHERE FaturacodFatura = "+PosFaturaProduto+" AND ProdutocodProduto = codProduto");
				} catch (NullPointerException e1) {
					alert.setTitle("Exception ");
					alert.setHeaderText("N�o selecionou nenhum dado da tabela ou tabela est� vazia");
					alert.setContentText("");
					alert.showAndWait();
				}
				faturaProdutoSelecionado = null;
			});
			
			btnEliminarFaturaProduto.setOnAction(E->{
				try {
					ObservableList<FaturaProduto> faturaProdutoSelected = tableFaturaProduto.getSelectionModel().getSelectedItems();
					faturaProdutoSelecionado =  faturaProdutoSelected.get(0);
					//DELETE FROM `fatura_produto` WHERE FaturacodFatura = `x` AND ProdutocodProduto = `x`
					UtilsSQLConn.mySqlDml("DELETE FROM `fatura_produto` WHERE FaturacodFatura = "+PosFaturaProduto+" AND ProdutocodProduto = "+faturaProdutoSelecionado.getProdutoCodProduto());
//	UtilsSQLConn.mySqlDml("UPDATE `fatura_produto` SET`ProdutocodProduto`="+codProduto+" WHERE FaturacodFatura = "+faturaSelecionada.getCodFatura()+" AND ProdutocodProduto = " + faturaProdutoSelecionado.getProdutoCodProduto());
					tabelaFaturaProduto.setAll(UtilsSQLConn.mySqlQweryFaturaProduto("SELECT `FaturacodFatura`,`NomeProduto`, `codProduto` FROM `fatura_produto`, `produto` WHERE FaturacodFatura = "+PosFaturaProduto+" AND ProdutocodProduto = codProduto"));
				} catch (NullPointerException e1) {
					alert.setTitle("Exception ");
					alert.setHeaderText("N�o selecionou nenhum dado da tabela ou tabela est� vazia");
					alert.setContentText("");
					alert.showAndWait();
				}
				faturaProdutoSelecionado = null;
				
				
			});
			
			janelaFaturaProduto.setOnCloseRequest(e->{
				tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));

			});
			
//--------------------------------------------Inserir, Alterar e Eliminar Produtos-------------------------------------------------
			//Metodo setOnAction Inserir
			btnInserirProduto.setOnAction(e->{
				layoutRoot.setCenter(FormInserirProduto());
			});
			
			//Meteodo setOnAction Alterar
			btnAlterarProduto.setOnAction(e-> {
			//	try {
					layoutRoot.setCenter(FormAlterarProduto());
			/*	} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("N�o selecionou nenhum dado da tabela");
					alert.setContentText("");
					alert.showAndWait();
				}*/
				
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
					alert.setHeaderText("N�o selecionou nenhum dado da tabela");
					alert.setContentText("");
					alert.showAndWait();
				}
				tabelaProduto.setAll(UtilsSQLConn.mySqlQweryProduto("SELECT * FROM `produto`"));
			});
//--------------------------------------------Inserir, Alterar e Eliminar Clientes-------------------------------------------------	
			//M�todo para chamar o layout de Inserir Clientes
			btnInserirCliente.setOnAction(e->{
				layoutRoot.setCenter(FormInserirClientes());
			});
			
			//M�todo para chamar o layout de Alterar Clientes
			btnAlterarCliente.setOnAction(e->{
				try {
					layoutRoot.setCenter(FormAlterarClientes());
				}	
				catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("N�o selecionou nenhum dado da tabela");
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
					alert.setHeaderText("N�o selecionou nenhum dado da tabela");
					alert.setContentText("");
					alert.showAndWait();
				}
				tabelaCliente.setAll(UtilsSQLConn.mySqlQweryCliente("SELECT * FROM `cliente`"));
			});
//---------------------------------------------------------------------------------------------------------------
			//M�todo do bot�o OK - LOGIN
			btnOk.setOnAction(e->{
				pressedText.setText("Entrando...");
				
				//Necess�rio para receber o que est� dentro das text field e passwordfield
				checkUser = textFieldUserName.getText().toString();
				checkPw = passwordFieldPassword.getText().toString();
				
				
				//Compara (if) se a string checkUser equals(� igual) ao username e o mesmo para a password
				//Se sim entra , muda de stage
				try{
					if(UtilsSQLConn.mySqlQueryVerificarLogin(checkUser, checkPw)){
						primaryStage.setMinHeight(500);
						primaryStage.setMinWidth(700);
						primaryStage.setMaxHeight(564213);
						primaryStage.setMaxWidth(415623);
						primaryStage.setScene(principal);
					}
					//Sen�o d� um aviso
					else{
						alert.setTitle("Aviso !");
						alert.setHeaderText("Ocorreu um erro ao efectuar o login");
						alert.setContentText("Senha ou Password Incorretos");
						alert.showAndWait();
						passwordFieldPassword.setText("");
					}
					
				} catch (NullPointerException e4){
					alert.setTitle("Exception ");
					alert.setHeaderText("Erro de liga��o � BD ");
					alert.setContentText("O ponteiro apontou para um NULL, Verifique a conex�o ao MySQL, Verifique a conex�o ao MySQL");
					alert.showAndWait();
				}
				
				
				
					
					
				
				try {
					
					tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
					layoutRoot.setCenter(layoutFatura);
				} catch (NullPointerException e2) {
					alert.setTitle("Exception ");
					alert.setHeaderText("Erro de liga��o � BD ");
					alert.setContentText("O ponteiro apontou para um NULL, Verifique a conex�o ao MySQL");
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
			primaryStage.setTitle("Fatura��o");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/********************************************
	 * Metodo que faz a cria��o da form de registar com 3 labels e text fields e 2 bot�es
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
				alertInfo.setContentText("Adicionado � Base de Dados");
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
	 * Esta fun��o � apresentada ao criar uma nova Insers�o para a tabela
	 * Apresenta os dados e  campos necess�rios e faz a conex�o com a base de dados para inserir os dados
	 * @return um Layout gridLogin Pane
	 */
	private GridPane FormFaturacaoInserir() {
		
		//Formul�rio Inserir
		// LABEL e textField info fatura
		//Label que fica no topo informativo
		Label lbTopo = new Label("Informa��es da Fatura");
		lbTopo.setFont(Font.font("Arial",FontWeight.BOLD, 12));
		//-------------
		//Label e text field do codCliente, com tooltip , se deixar o rato em cima mostra uma informa��o para ajuda no preenchimento
		Label lbCodCliente = new Label("Cod Cliente");
		TextField txtCodCliente = new TextField();
		txtCodCliente.setTooltip(new Tooltip("Introduza o Cliente j� existente na tabela clientes, se n�o crie um antes de criar a fatura"));
		txtCodCliente.setPromptText("ID do cliente");
		
		//Data da Fatura com as mesmas defini��es de acima
		Label lbDataFatura = new Label("Data da Fatura");
		TextField txtDataFatura = new TextField();
		txtDataFatura.setPromptText("Data de Fatura");
		txtDataFatura.setTooltip(new Tooltip("Pode conter numeros, letras e caracteres alfanum�ricos"));
		
		//Garantia  com as mesmas defini��es de acima
		Label lbGarantia = new Label("Garantia");
		TextField txtGarantia = new TextField();
		txtGarantia.setPromptText("Dura��o da Garantia");
		txtGarantia.setTooltip(new Tooltip("Dura��o da garantia, por exemplo : 2 Anos"));
		
		//Total com as mesmas defini��es de acima
		Label lbTotal = new Label("Total");
		TextField txtTotal = new TextField();
		txtTotal.setPromptText("Total da fatura");
		txtTotal.setTooltip(new Tooltip("Desabilitado, pois com a nova vers�o , o total � somado de acordo com o pre�o dos produtos"));
		txtTotal.setText("0");
		txtTotal.setDisable(true);
		
		
		
		
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
			
			//Chama a fun��o de outra classe para executar o comando Insert para a base de dados do foruml�rio
			UtilsSQLConn.mySqlDml("Insert into fatura"				
					+" (ClientecodCivil, DataDaFatura, Garantia,Total)"						
					+" Values('"+ClientecodCivil+"', '"+dataDaFatura+"', '"+garantia+"', '"+total+"')");
			
			//Trata da exe��o de tentar converter uma string vazia em um inteiro
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
	 * A��o de alterar um dado  na tabela para a fatura��o
	 * @return
	 */
	
	private GridPane FormAlterarFaturacao() {
		
		//Formul�rio Inserir
				// LABEL e textField info fatura
				//Label que fica no topo informativo
				Label lbTopo = new Label("Informa��es da Fatura");
				lbTopo.setFont(Font.font("Arial",FontWeight.BOLD, 12));
				//-------------
				//Label e text field do codCliente, com tooltip , se deixar o rato em cima mostra uma informa��o para ajuda no preenchimento
				Label lbCodCliente = new Label("Cod Cliente");
				TextField txtCodCliente = new TextField();
				txtCodCliente.setTooltip(new Tooltip("Introduza o Cliente j� existente na tabela clientes, se n�o crie um antes de criar a fatura"));
				txtCodCliente.setPromptText("ID do cliente");
				
				//Data da Fatura com as mesmas defini��es de acima
				Label lbDataFatura = new Label("Data da Fatura");
				TextField txtDataFatura = new TextField();
				txtDataFatura.setPromptText("Data de Fatura");
				txtDataFatura.setTooltip(new Tooltip("Pode conter numeros, letras e caracteres alfanum�ricos"));
				
				//Garantia  com as mesmas defini��es de acima
				Label lbGarantia = new Label("Garantia");
				TextField txtGarantia = new TextField();
				txtGarantia.setPromptText("Dura��o da Garantia");
				txtGarantia.setTooltip(new Tooltip("Dura��o da garantia, por exemplo : 2 Anos"));
				
				//Total com as mesmas defini��es de acima
				Label lbTotal = new Label("Total");
				TextField txtTotal = new TextField();
				txtTotal.setPromptText("Total da fatura");
				txtTotal.setTooltip(new Tooltip("Desabilitado, pois com a nova vers�o , o total � somado de acordo com o pre�o dos produtos"));
			//	txtTotal.setText("0");
				txtTotal.setDisable(true);
				
				
				
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
				
				
				//Metodo ok executa o c�digo SQL
				btnOKFatura.setOnAction(e->{
					//Recebe os dados contidos nas textFields
					int codFatura;					
					int ClientecodCivil;
					String dataDaFatura;
					String garantia;
					Double total;
					//O utilizador n�o pode alterar o c�digo de Fatura , por�m ela � reentroduzida a j� existente sem alterar
					codFatura = faturaSelecionada.getCodFatura();
					//Try catch para exe��es
					try {
						ClientecodCivil = Integer.parseInt(txtCodCliente.getText());
					
					
					dataDaFatura = txtDataFatura.getText();
					garantia = txtGarantia.getText();
					total = Double.parseDouble(txtTotal.getText());
					//Chama  a fun��o de outra classe e executa o comando UDADATE na base de dados MySql
					UtilsSQLConn.mySqlDml("UPDATE `fatura` SET `codFatura`= "+codFatura+",`ClientecodCivil`= " +ClientecodCivil+ ",`DataDaFatura`= '"+dataDaFatura+"',`Garantia`= '"+garantia+"' ,`Total`= "+total+" WHERE codFatura = " +codFatura);	
					
					
					//Trata a exe��o ao tentar converter uma string vazia para um numero
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
				//But�o de cancelar no formul�rio volta para a Tabela voltando a fazer uma query � DB
				btnCancelFatura.setOnAction(e->{
					layoutRoot.setCenter(layoutFatura);
					//tabelaFaturas.setAll(UtilsSQLConn.mySqlQweryFaturacao("SELECT * FROM `fatura` WHERE 1"));
				});
				
				
				
		return layoutFormAlterar;
		
	} 
	
	/************************************
	 * Form para inserir um novo Produto na BD
	 * Cria-se a form para preenchimento dos dados e depois faz-se um insert � BD
	 * @return
	 */
	private GridPane FormInserirProduto() {
		
		
		//Label do topo informativa
		Label lbTopo = new Label("Informa��es do Produto");
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
		
		//Label Pre�o + TextField
		Label lbPreco = new Label("Pre�o");
		TextField txtPreco = new TextField();
		txtPreco.setPromptText("Pre�o do Produto");
		
		
		//Label Stock + TextField
		Label lbStock = new Label("Stock");
		TextField txtStock = new TextField();
		txtStock.setPromptText("Quantidade em Stock");
		
		//Bot�es OK e Cancelar
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
				alert.setHeaderText("Erro de Liga��o � BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
			
			layoutRoot.setCenter(layoutProduto);
			
			try {
				tabelaProduto.setAll(UtilsSQLConn.mySqlQweryProduto("SELECT * FROM `produto`"));
			} catch (NullPointerException e2) {
				alert.setTitle("Exception ");
				alert.setHeaderText("Erro de Liga��o � BD");
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
		Label lbTopo = new Label("Informa��es do Produto");
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
		
		//Label Pre�o + TextField
		Label lbPreco = new Label("Pre�o");
		TextField txtPreco = new TextField();
		txtPreco.setPromptText("Pre�o do Produto");
		
		
		//Label Stock + TextField
		Label lbStock = new Label("Stock");
		TextField txtStock = new TextField();
		txtStock.setPromptText("Quantidade em Stock");
		
		//Bot�es OK e Cancelar
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
	 * M�todo que faz  o Insert na tabela Clientes
	 * O metodo retorna um Layout grid com um formul�rio 
	 * O formul�rio tem  as Labels e Text Field e 2 bot�es, aceitar e Cancelar
	 * Ok - Far� o acesso � bd com o Comando INSERT
	 * Cancelar - Voltar ao Layout principal com a tabela dos Clientes
	 * @return
	 */
	private GridPane FormInserirClientes() {
		
		//Label do topo informativa
		Label lbTopo = new Label("Informa��es do Cliente");
		lbTopo.setFont(Font.font("Arial",FontWeight.BOLD, 12));
		
		//Label Nome + TextField
		Label lbNome = new Label("Nome");
		TextField txtNome = new TextField();
		txtNome.setPromptText("Nome do Cliente");
		
		//Label NIF + Text Field
		Label lbNIF = new Label("NIF");
		TextField txtNIF = new TextField();
		txtNIF.setPromptText("N�mero de Indentifica��o F�scal");
		//Label Morada + Text Field
		Label lbMorada = new Label("Morada");
		TextField txtMorada = new TextField();
		txtMorada.setPromptText("Ex: Rua das Flores n�3 4�Esq");
		
		//Label Pre�o + TextField
		Label lbNIB = new Label("NIB");
		TextField txtNIB = new TextField();
		txtNIB.setPromptText("N�mero de Indentifica��o Banc�rio");
		
		
		//Label Stock + TextField
		Label lbNISS = new Label("NISS");
		TextField txtNISS = new TextField();
		txtNISS.setPromptText("N�mero de Identifica��o de Seguran�a Social");
		
		//Bot�es OK e Cancelar
		Button btnOkFormCliente = new Button("OK");
		Button btnCancelFormCliente = new Button("Cancelar");
		btnCancelFormCliente.setCancelButton(true);
		HBox layoutOkCancelCliente = new HBox(55);
		layoutOkCancelCliente.getChildren().addAll(btnOkFormCliente, btnCancelFormCliente);
		//Tratamento e Adi��o ao layout
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
		
		//Metodo que faz a insers�o na BD
		btnOkFormCliente.setOnAction(e->{
			//Protege os campos para serem preenchidos
			if(txtNome.getText().isEmpty() || txtNIF.getText().isEmpty() || txtMorada.getText().isEmpty() || txtNIB.getText().isEmpty() || txtNISS.getText().isEmpty())
			{
				alert.setTitle("Erro ao criar!");
				alert.setHeaderText("Preencha os Campos!");
				alert.showAndWait();
			}
			
			//Variaveis para receberem o que est� dentro das TextFields da Form
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
				se tentou converter uma string que est� vazia para um numero
				O Segundo apanha caso houver um erro de liga��o
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
				alert.setHeaderText("Erro de Liga��o � BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
			
			//Apanha caso houver um ponteiro nulo
			try {
				tabelaCliente.setAll(UtilsSQLConn.mySqlQweryCliente("SELECT * FROM `cliente`"));
				layoutRoot.setCenter(layoutCliente);
			} catch (NullPointerException e2) {
				alert.setTitle("Exception ");
				alert.setHeaderText("Erro de Liga��o � BD");
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
		Label lbTopo = new Label("Informa��es do Cliente");
		lbTopo.setFont(Font.font("Arial",FontWeight.BOLD, 12));
		
		//Label Nome + TextField
		Label lbNome = new Label("Nome");
		TextField txtNome = new TextField();
		txtNome.setPromptText("Nome do Cliente");
		
		//Label NIF + Text Field
		Label lbNIF = new Label("NIF");
		TextField txtNIF = new TextField();
		txtNIF.setPromptText("N�mero de Indentifica��o F�scal");
		//Label Morada + Text Field
		Label lbMorada = new Label("Morada");
		TextField txtMorada = new TextField();
		txtMorada.setPromptText("Ex: Rua das Flores n�3 4�Esq");
		
		//Label Pre�o + TextField
		Label lbNIB = new Label("NIB");
		TextField txtNIB = new TextField();
		txtNIB.setPromptText("N�mero de Indentifica��o Banc�rio");
		
		
		//Label Stock + TextField
		Label lbNISS = new Label("NISS");
		TextField txtNISS = new TextField();
		txtNISS.setPromptText("N�mero de Identifica��o de Seguran�a Social");
		
		//Bot�es OK e Cancelar
		Button btnOkFormCliente = new Button("OK");
		Button btnCancelFormCliente = new Button("Cancelar");
		btnCancelFormCliente.setCancelButton(true);
		HBox layoutOkCancelCliente = new HBox(55);
		layoutOkCancelCliente.getChildren().addAll(btnOkFormCliente, btnCancelFormCliente);
		//Tratamento e Adi��o ao layout
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
				alert.setHeaderText("Erro de Liga��o � BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
			//Apanha caso houver um ponteiro nulo
			try {
				tabelaCliente.setAll(UtilsSQLConn.mySqlQweryCliente("SELECT * FROM `cliente`"));
				layoutRoot.setCenter(layoutCliente);
			} catch (NullPointerException e2) {
				alert.setTitle("Exception ");
				alert.setHeaderText("Erro de Liga��o � BD");
				alert.setContentText("Ponteiro Nulo");
				alert.showAndWait();
			}
		});
		return layoutFormAlterarCliente;
		
	}
	
	
}
