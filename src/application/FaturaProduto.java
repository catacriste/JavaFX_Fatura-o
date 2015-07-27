package application;

public class FaturaProduto {
	private int faturaCodFatura;
	private int produtoCodProduto;
	private String nomeProduto;
	
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public int getFaturaCodFatura() {
		return faturaCodFatura;
	}
	public int getProdutoCodProduto() {
		return produtoCodProduto;
	}
	public void setFaturaCodFatura(int faturaCodFatura) {
		this.faturaCodFatura = faturaCodFatura;
	}
	public void setProdutoCodProduto(int produtoCodProduto) {
		this.produtoCodProduto = produtoCodProduto;
	}


}
