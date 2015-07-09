package application;

public class Faturacao {
	private int codFatura;
	private int clienteCodCivil;
	private String dataDaFatura;
	private String garantia;
	private double total;
	
	
	public int getCodFatura() {
		return codFatura;
	}
	public String getDataDaFatura() {
		return dataDaFatura;
	}
	public String getGarantia() {
		return garantia;
	}
	public double getTotal() {
		return total;
	}
	public void setCodFatura(int codFatura) {
		this.codFatura = codFatura;
	}
	public void setDataDaFatura(String dataDaFatura) {
		this.dataDaFatura = dataDaFatura;
	}
	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	/**
	 * @return the clienteCodCivil
	 */
	public int getClienteCodCivil() {
		return clienteCodCivil;
	}
	/**
	 * @param clienteCodCivil the clienteCodCivil to set
	 */
	public void setClienteCodCivil(int clienteCodCivil) {
		this.clienteCodCivil = clienteCodCivil;
	}
}
