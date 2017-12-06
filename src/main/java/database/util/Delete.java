package database.util;

public class Delete extends CrudTemplate {

    Delete() {
    }

    public boolean deleteProduct(String username, String product) {
        String sql = "DELETE from products where username=" + addEarsToString(username) + " and product="
                + addEarsToString(product);
        return isExecuted(sql);
    }

    public boolean deleteProductsQtyFrom_quantytiesperdevice(String username, int deviceId) {
        String sqlDropForId = "DELETE FROM quantytiesperdevice WHERE username=" + addEarsToString(username) + " AND deviceid=" + deviceId;
        return isExecuted(sqlDropForId);
    }


    public boolean deleteProductsFromProductsWhenProductNotExistForSuchUserInQty() {
        String sqlCleanProducts = "DELETE FROM products p\n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT 1\n" +
                "    FROM   quantytiesperdevice qty\n" +
                "    WHERE  qty.username = p.username and qty.product=p.product\n" +
                ")";
        return isExecuted(sqlCleanProducts);
    }

}
