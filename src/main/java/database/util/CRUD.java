package database.util;

public class CRUD {
	public final static Delete DELETE = new Delete();
	public final static Update UPDATE = new Update();
	public final static Query QUERY = new Query();
	public final static Insert INSERT = new Insert();

	private CRUD() {
	}

//	public static void InsertUser(String user, String password) {
//		CRUD.INSERT.insertUser(user, password);
//	}
//
//	public static void InsertProduct(String user, String product, String sklep, double cena, int ilosc) {
//		CRUD.INSERT.insertProduct(user, product, sklep, cena, ilosc);
//	}
//
//	public static void UpdateProductQuantity(String username, String produkt, Integer newQuantity) {
//		CRUD.UPDATE.updateProductQuantity(username, produkt, newQuantity);
//	}
//
//	public static void DeleteProduct(String username, String product) {
//		CRUD.DELETE.deleteProduct(username, product);
//	}
//
//	public static void DisplayResultSet(ResultSet resultSet) {
//		CRUD.QUERY.displayResultSet(resultSet);
//	}
//
//	public static ResultSet GetResultsFromSql(String SqlQuery) {
//		return CRUD.QUERY.getResultsFromSql(SqlQuery);
//	}
//	public static void DisplayResultSet() {
//		CRUD.QUERY.displayResultSet();
//	}
//	public static void CloseQueryStatement() {
//		CRUD.QUERY.closeStatement();
//	}

}
