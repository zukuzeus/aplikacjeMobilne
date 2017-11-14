package server;

import database.util.CRUD;
import database.util.JsonUtil;
import spark.Request;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        port(8080);
        init();

        post("/login", (req, res) -> CRUD.QUERY.isUserExistsAndPasswordMatch(
                req.queryParams("username"),
                req.queryParams("password")));

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

        after((req, res) -> {
            res.type("application/json");
        });

        post("/register", (req, res) -> {
            if (CRUD.INSERT.insertUser(req.queryParams("username"), req.queryParams("password"))) {
                return true;
            } else
                return false;

        });
    }

    public static boolean isLoginAndPasswordMatch(Request req) {
        boolean isLogged = CRUD.QUERY.isUserExistsAndPasswordMatch(req.queryParams("username"), req.queryParams("password"));
        if (isLogged) {
            return true;
        } else return false;

    }
}