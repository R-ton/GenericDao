package buy;

public class PurchaseDetail {
	String idPurchaseDetail;
	String idPurchase;
	String idProduct;
	double unitPrice;
	double quantity;
	
	public String getIdPurchaseDetail() {
		return idPurchaseDetail;
	}
	public void setIdPurchaseDetail(String idPurchaseDetail) {
		this.idPurchaseDetail = idPurchaseDetail;
	}
	public String getIdPurchase() {
		return idPurchase;
	}
	public void setIdPurchase(String idPurchase) {
		this.idPurchase = idPurchase;
	}
	public String getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public PurchaseDetail[] getPurchaseDetail() {
		PurchaseDetail[] data = null;
		
		return data;
	}
	
}
