import io.restassured.response.Response;
import pojo.*;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import org.apache.logging.log4j.*;
import static io.restassured.RestAssured.given;

public class Main{

    public static Logger log4j = LogManager.getLogger(Main.class.getName());

    //Main request
    public static Response GETRequest(String link){
        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .get(link)
                .then()
                .extract().response();
        return response;
    }

    //Name getters
    public static String[] getNamesByLink(ArrayList<String> links){
        String[] names = new String[links.size()];
        for (int i=0; i<links.size(); i++){
            Response response = GETRequest(links.get(i));
//            Response response = given()
//                    .when()
//                    .contentType(ContentType.JSON)
//                    .get(links.get(i))
//                    .then()
//                    .extract().response();
            log4j.info("Reply to related request: " + response.asString());
           String name = response.body().jsonPath().get("name");
           if (name==null) {
               name = response.body().jsonPath().get("title");
           }
           names[i]=name;
        }
        return names;
    }
    public static String getNamesByLink(String link){
//        Response response = given()
//                .when()
//                .contentType(ContentType.JSON)
//                .get(links)
//                .then()
//                .extract().response();
        Response response = GETRequest(link);
        log4j.info("Reply to related request: " + response.asString());
        String name = response.body().jsonPath().get("name");
        if (name==null) {
            name = response.body().jsonPath().get("title");
        }
        return name;
    }

    //Printers
    public static void printFilm(Response response) {

        Film requestAnswerFilm = response.body().as(Film.class);
        System.out.println("Название: " + requestAnswerFilm.title + "\n"
                + "Директор: " + requestAnswerFilm.director + "\n"
                + "Продюссер: " + requestAnswerFilm.producer + "\n"
                + "Дата выхода: " + requestAnswerFilm.release_date + "\n"
                + "Герои: " + Arrays.toString(getNamesByLink(requestAnswerFilm.characters)));
    }
    public static void printPerson(Response response) {
        Person requestAnswerPerson = response.body().as(Person.class);
        System.out.println("Имя: " + requestAnswerPerson.name + "\n"
                + "Пол: " + requestAnswerPerson.gender + "\n"
                + "Цвет глаз: " + requestAnswerPerson.eye_color + "\n"
                + "Год рождения: " + requestAnswerPerson.birth_year + "\n"
                + "Родной мир: " + getNamesByLink(requestAnswerPerson.homeworld) + "\n"
                + "Появление в: " + Arrays.toString(getNamesByLink(requestAnswerPerson.films)));
    }
    public static void printPlanet(Response response) {
        Planet requestAnswerPlanet = response.body().as(Planet.class);
        System.out.println("Название: " + requestAnswerPlanet.name + "\n"
                + "Население: " + requestAnswerPlanet.population + "\n"
                + "Гравитация: " + requestAnswerPlanet.gravity + "\n"
                + "Длина суток: " + requestAnswerPlanet.rotation_period + "\n"
                + "Родная планета для: " + Arrays.toString(getNamesByLink(requestAnswerPlanet.residents)));
    }
    public static void printSpecie(Response response) {
        Specie requestAnswerSpecie = response.body().as(Specie.class);
        System.out.println("Название: " + requestAnswerSpecie.name + "\n"
                + "Цвета крожи: " + requestAnswerSpecie.skin_colors + "\n"
                + "Цвета волос: " + requestAnswerSpecie.hair_colors + "\n"
                + "Язык: " + requestAnswerSpecie.language + "\n"
                + "Представители: " + Arrays.toString(getNamesByLink(requestAnswerSpecie.people)));
    }
    public static void printStarship(Response response) {
        Starship requestAnswerStarship = response.body().as(Starship.class);
        System.out.println("Название: " + requestAnswerStarship.name + "\n"
                + "Модель: " + requestAnswerStarship.model + "\n"
                + "Стоимость: " + requestAnswerStarship.cost_in_credits + "\n"
                + "Размер экипажа: " + requestAnswerStarship.crew + "\n"
                + "Капитаны: " + Arrays.toString(getNamesByLink(requestAnswerStarship.pilots)));
    }
    public static void printVehicle(Response response) {
        Vehicle requestAnswerVehicle = response.body().as(Vehicle.class);
        System.out.println("Название: " + requestAnswerVehicle.name + "\n"
                + "Модель: " + requestAnswerVehicle.model + "\n"
                + "Длина: " + requestAnswerVehicle.length + "\n"
                + "Размер экипажа: " + requestAnswerVehicle.crew + "\n"
                + "Появлялся в: " + Arrays.toString(getNamesByLink((ArrayList<String>) requestAnswerVehicle.films)));
    }

    public static void main(String[] args) throws IOException {
        log4j.info("START");

        Dictionary Dictionary = new Dictionary();
        while (true){
            //User input
            System.out.println("Введите ваш запрос. Для получения списка возможных запросов напишите !help, 0 - для выхода.");
            Scanner console = new Scanner(System.in);
            String prompt = console.nextLine();
            log4j.info(prompt);

            //Getting dictionary
            if (Objects.equals(prompt, "!help")) {
                log4j.trace("List of prompts out");
                for (int i=0; i<Dictionary.ListOfContent.size(); i++) {
                    System.out.println(Dictionary.ListOfContent.get(i).nameRu + ", " + Dictionary.ListOfContent.get(i).name);
                }
            //Exit
            } else if (Objects.equals(prompt, "0")) {
                log4j.info("Exit");
                break;

            //Body
            } else {
                String URL = Dictionary.getURL(prompt);
                log4j.trace(URL);
                String type = Dictionary.getType(prompt);
                if (type!="Не найдено") {
                    Response response = GETRequest(URL);
                    log4j.info("Request answer: " + response.asString());

                    //Print
                    switch (type) {
                        case ("Film"):
                            printFilm(response);
                            break;
                        case ("Person"):
                            printPerson(response);
                            break;
                        case ("Planet"):
                            printPlanet(response);
                            break;
                        case ("Specie"):
                            printSpecie(response);
                            break;
                        case ("Starship"):
                            printStarship(response);
                            break;
                        case ("Vehicle"):
                            printVehicle(response);
                            break;

                        default:
                            log4j.warn("Type wasn't found");
                            System.out.println("Тип не найден");
                    }
                } else {
                    log4j.error("Unrecognized prompt: " + prompt);
                }
            }
        }
    }
}