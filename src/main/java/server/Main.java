package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.containers.Product;
import database.util.CRUD;
import database.util.JsonUtil;
import spark.Request;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        port(8080);
        init();

        post("/login", (req, res) -> {
//            System.out.println("LOGIN BODy = " + req.body().toString());
//            System.out.println("LOGIN PARAMS" + req.queryParams());
            return CRUD.QUERY.isUserExistsAndPasswordMatch(
                    req.queryParams("username"),
                    req.queryParams("password"));
        });

        post("/getId", (req, res) -> {
            if (isLoginAndPasswordMatch(req)) {
                int devId = -1;
                if (req.queryParams("id").toString().equals(Integer.toString(-1))) {
                    //dodaj id do tabeli deviceidentyficator
                    devId = CRUD.INSERT.generateNewIdForDevice(req.queryParams("username"));
                } else if (Integer.parseInt(req.queryParams("id")) >= 1) {
                    devId = Integer.parseInt(req.queryParams("id"));
                }
                return devId;
            } else return "not autenticated";
        });

        post("/results", (req, res) -> {
            if (isLoginAndPasswordMatch(req)) {
                return CRUD.QUERY.getResultsAsProductService(req.queryParams("username")).getAllProducts();
            } else return "not autenticated";

        }, JsonUtil.json());

        post("/delete", (req, res) -> {
            if (isLoginAndPasswordMatch(req)) {
                return CRUD.DELETE.deleteProduct(req.queryParams("username"), req.queryParams("product"));
            } else return "not autenticated";
        }, JsonUtil.json());

        post("/update", (req, res) -> {
            if (isLoginAndPasswordMatch(req)) {
                return CRUD.UPDATE.updateProductQuantity(req.queryParams("username"), req.queryParams("product"), Integer.parseInt(req.queryParams("quantity")));
            } else return "not autenticated";
        }, JsonUtil.json());

        post("/add", (req, res) -> {
            if (isLoginAndPasswordMatch(req)) {
                return CRUD.INSERT.insertProduct(req.queryParams("username"), req.queryParams("product"), req.queryParams("shop"), Double.parseDouble(req.queryParams("price")), 0);
            } else return "not autenticated";
        }, JsonUtil.json());
//new
        post("/synchronize", (req, res) -> {

            String username = getUserFromBody(req);
            int deviceId = getDeviceIdBody(req);

            CRUD.DELETE.deleteProductsQtyFrom_quantytiesperdevice(username, deviceId);

            getProductListFromJsonFromDeviceDB(req.body()).forEach(product -> {
                if (CRUD.QUERY.ifProductExistsInDataBase(product.getProductName(), username)) {
                    System.out.println("istnieje");
                    CRUD.INSERT.insertProductQtyToUserTable(username, product.getProductName(), deviceId, product.getQuantity());
                } else {
                    System.out.println("nie istnieje");
                    CRUD.INSERT.insertProduct(username, product.getProductName(), product.getShop(), product.getPrize(), product.getQuantity());
                    CRUD.INSERT.insertProductQtyToUserTable(username, product.getProductName(), deviceId, product.getQuantity());
                }
            });
            CRUD.UPDATE.updateProductQuantityInDevicesTable();
            CRUD.DELETE.deleteProductsFromProductsWhenProductNotExistForSuchUserInQty();

            System.out.println(req.body().toString());
            boolean a = isLoginAndPasswordMatchHEADER(req);
            if (a) {
//                CRUD.INSERT.insertProductQtyToUserTable("S", "pomarancz", 1, 2);
                System.out.println("udało sie autentykacja");
                return true;
            } else return "not autenticated";


        }, JsonUtil.json());


        after((req, res) -> {
            res.type("application/json");
        });

        post("/register", (req, res) -> {
            if (CRUD.INSERT.insertUser(req.queryParams("username"), req.queryParams("password"))) {
                return true;
            } else
                return false;

        });
//        CRUD.UPDATE.updateProductQuantityInDevicesTable("a", "a", 1, 1);
    }

    public static boolean isLoginAndPasswordMatch(Request req) {
        boolean isLogged = CRUD.QUERY.isUserExistsAndPasswordMatch(req.queryParams("username").toString(), req.queryParams("password").toString());
        if (isLogged) {
            return true;
        } else return false;

    }

    public static boolean isLoginAndPasswordMatchHEADER(Request req) { //TODO -> mozna zrobic ze haslo i uzytkownik w headerze
        String autorisation = req.headers("Authorization").toString();
        autorisation = autorisation.substring("Basic ".length());
        System.out.println(autorisation);
        byte[] base64String = new byte[0];
        try {
            base64String = Base64.getDecoder().decode(autorisation.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String userandpassword = new String(base64String);
        String user = userandpassword.substring(0, userandpassword.lastIndexOf(":"));
        String password = userandpassword.substring(userandpassword.lastIndexOf(":") + 1);
        boolean isLogged = CRUD.QUERY.isUserExistsAndPasswordMatch(user, password);
        return isLogged;

    }

    public static String getUserFromBody(Request req) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(req.body());
        return element.getAsJsonObject().get("username").getAsString();
    }

    public static int getDeviceIdBody(Request req) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(req.body());
        return element.getAsJsonObject().get("id").getAsInt();
    }

//    public static void getInfoFromJson(String json) { // wersja jak ejst substate product
//        List<String> list = new ArrayList<>();
//
//        JsonParser jsonParser = new JsonParser();
//        JsonElement element = jsonParser.parse(json);
//        JsonArray lista = element.getAsJsonObject().get("stanBazy").getAsJsonArray();
//        for (int i = 0; i < lista.size(); i++) {
//            JsonObject obiektWliscie = lista.get(i).getAsJsonObject();
//            obiektWliscie.get("productName");
//            obiektWliscie.get("store");
//            obiektWliscie.get("price");
//            obiektWliscie.get("quantity");
//            JsonObject ilosciNaUrzadzeniu = obiektWliscie.get("subStatesOfProduct").getAsJsonObject();
//            ilosciNaUrzadzeniu.get("quantityLocaly").getAsInt();
//            ilosciNaUrzadzeniu.get("secondDeviceQuantity").getAsInt();
//        }
//
//    }

    public static List<Product> getProductListFromJsonFromDeviceDB(String json) {
        List<Product> list = new ArrayList<>();

        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonArray lista = element.getAsJsonObject().get("stanBazy").getAsJsonArray();
        for (int i = 0; i < lista.size(); i++) {
            JsonObject obiektWliscie = lista.get(i).getAsJsonObject();
            list.add(new Product(
                    obiektWliscie.get("productName").getAsString(),
                    obiektWliscie.get("store").getAsString(),
                    obiektWliscie.get("price").getAsDouble(),
                    obiektWliscie.get("quantity").getAsInt()));
        }
        return list;
    }
}