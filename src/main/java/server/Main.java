package server;

import http.connection.EchoWebSocket;
import spark.Route;

import static spark.Spark.*;

import java.sql.SQLException;

import com.google.gson.Gson;

import database.containers.ProductService;
import database.util.CRUD;
import database.util.DatabaseConnection;
import database.util.JsonUtil;

public class Main {

	public static void main(String[] args) {

		port(8080);

		// webSocket("/echo", EchoWebSocket.class);
		// get("/echo", (req, res) -> "Hello World - omlet pedzio");
		get("/echo", (req, res) -> "sdf");
		init();

		// ProductService products = null;
		// products = CRUD.QUERY.getResultsAsProductService("daniel");

		// Gson gson = new Gson();
		// gson.toJson(products);
		// products.updateProductQuantity("baton1", 5);
		get("/hello",(request, response) -> "helol");
		post("/login", (req, res) -> CRUD.QUERY.isUserExistsAndPasswordMatch(req.queryParams("username"),
				req.queryParams("password")));
		post("/results", (req, res) -> CRUD.QUERY.getResultsAsProductService(req.queryParams("username")).getAllProducts(),
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

		//
		// stop();
	}
}