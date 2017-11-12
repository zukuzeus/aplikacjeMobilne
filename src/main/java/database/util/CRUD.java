package database.util;

public class CRUD {
	public final static Delete DELETE = new Delete();
	public final static Update UPDATE = new Update();
	public final static Query QUERY = new Query();
	public final static Insert INSERT = new Insert();

	private CRUD() {
	}
}
