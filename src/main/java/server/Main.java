package server;

import database.util.CRUD;
import database.util.JsonUtil;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        port(8080);
        init();

        get("/hello", (request, response) -> "helol");
        post("/login", (req, res) -> CRUD.QUERY.isUserExistsAndPasswordMatch(
                req.queryParams("username"),
                req.queryParams("password")));

        post("/results", (req, res) -> CRUD.QUERY.getResultsAsProductService(
                req.queryParams("username")).getAllProducts(),
                JsonUtil.json());
        //
        post("/delete", (req, res) -> CRUD.DELETE.deleteProduct(
                req.queryParams("username"),
                req.queryParams("product")),
                JsonUtil.json());

        post("/update", (req, res) -> CRUD.UPDATE.updateProductQuantity(
                req.queryParams("username"),
                req.queryParams("product"),
                Integer.parseInt(req.queryParams("quantity"))),
                JsonUtil.json());

        post("/add", (req, res) -> CRUD.INSERT.insertProduct(
                req.queryParams("username"),
                req.queryParams("product"),
                req.queryParams("shop"),
                Double.parseDouble(req.queryParams("price")),
                0),
                JsonUtil.json());

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
}