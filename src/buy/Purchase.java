package buy;

import java.sql.Connection;

public class Purchase {
	String idPurchase;
	String idCustomer;
	String date;
	
	public String getIdPurchase() {
		return idPurchase;
	}
	public void setIdPurchase(String idPurchase) {
		this.idPurchase = idPurchase;
	}
	public String getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public PurchaseDetail[] getPurchaseDetail(Connection connection) {
		PurchaseDetail[] data = null;
		String idPurchase = this.idPurchase;
		
		
		
		return data;
	}
	
}
