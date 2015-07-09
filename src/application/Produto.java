package application;

public class Produto {
	private int codProduto;
	private String nomeProduto;
	private String marca;
	private String dataValidade;
	private String preco;
	private String stock;
	/**
	 * @return the codProduto
	 */
	public int getCodProduto() {
		return codProduto;
	}
	/**
	 * @return the nomeProduto
	 */
	public String getNomeProduto() {
		return nomeProduto;
	}
	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}
	/**
	 * @return the dataValidade
	 */
	public String getDataValidade() {
		return dataValidade;
	}
	/**
	 * @return the preco
	 */
	public String getPreco() {
		return preco;
	}
	/**
	 * @return the stock
	 */
	public String getStock() {
		return stock;
	}
	/**
	 * @param codProduto the codProduto to set
	 */
	public void setCodProduto(int codProduto) {
		this.codProduto = codProduto;
	}
	/**
	 * @param nomeProduto the nomeProduto to set
	 */
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}
	/**
	 * @param dataValidade the dataValidade to set
	 */
	public void setDataValidade(String dataValidade) {
		this.dataValidade = dataValidade;
	}
	/**
	 * @param preco the preco to set
	 */
	public void setPreco(String preco) {
		this.preco = preco;
	}
	/**
	 * @param stock the stock to set
	 */
	public void setStock(String stock) {
		this.stock = stock;
	}
}
