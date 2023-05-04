package cs1302.api;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;
import java.net.http.HttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

/**
 * REPLACE WITH NON-SHOUTING DESCRIPTION OF YOUR APP.
 */
public class ApiApp extends Application {
    Stage stage;
    Scene scene;
    VBox root;

    // HttpClient
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();

    // Gson
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    HBox queryH;
    Button searchB;
    TextFlow searchText;
    TextField searchField;
    HBox countryH;
    TextFlow countryText;
    TextFlow capitalText;
    HBox imageH;
    ImageView sunRIV;
    ImageView sunSIV;
    HBox sunTimeH;
    TextFlow sunriseText;
    TextFlow sunsetText;


    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        root = new VBox();
        queryH = new HBox();
        searchB = new Button("Search");
        searchText = new TextFlow(new Text("Enter a country name:   "));
        searchField = new TextField();
        countryH = new HBox();
        countryText = new TextFlow(new Text("Country:        "));
        capitalText = new TextFlow(new Text("Capital:        "));
        imageH = new HBox();
        sunRIV = new ImageView();
        sunSIV = new ImageView();
        sunTimeH = new HBox();
        sunriseText = new TextFlow(new Text("Sunrise Time:      "));
        sunsetText = new TextFlow(new Text("Sunset Time:      "));
    } // ApiApp



    /** {@inheritDoc} */
    @Override
    public void init() {
        root.getChildren().addAll(queryH, countryH, imageH, sunTimeH);
        queryH.getChildren().addAll(searchText, searchField, searchB);
        countryH.getChildren().addAll(countryText, capitalText);
        imageH.getChildren().addAll(sunRIV, sunSIV);
        sunTimeH.getChildren().addAll(sunriseText, sunsetText);
        String oneR = "https://creazilla-store.fra1.digitaloceanspaces.com/";
        String twoR = "cliparts/1723310/sunrise-clipart-md.png";
        Image sRise = new Image(oneR + twoR);
        String oneS = "https://creazilla-store.fra1.digitaloceanspaces.com/";
        String twoS = "cliparts/78211/sunset-clipart-md.png";
        Image sSet = new Image(oneS + twoS);
        sunRIV.setImage(sRise);
        sunSIV.setImage(sSet);
    } // init


    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;

        /**
        // demonstrate how to load local asset using "file:resources/"
        Image bannerImage = new Image("file:resources/readme-banner.png");
        ImageView banner = new ImageView(bannerImage);
        banner.setPreserveRatio(true);
        banner.setFitWidth(640);

        // some labels to display information
        Label notice = new Label("Modify the starter code to suit your needs.");

        // setup scene
        root.getChildren().addAll(banner, notice);
        */
        this.scene = new Scene(root);

        // setup stage
        stage.setTitle("ApiApp!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
    } // start


    /**
     * Sunrise sunset time.
     * @param cc the country code given by the user
     */
    /**
    public void getSunTime(String cc) {
        // CHANGE VOID RESPONSE TYPE
        // construct url
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create("http://api.worldbank.org/v2/country/" + cc + "?format=json"))
            .build();
        // ClassName worldResponse = null;
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(req, BodyHandlers.ofString());
            String responseBody = response.body();
            // worldResponse = GSON.<ClassName>fromJson(responseBody, ClassName.class);
            // return worldResponse;
        } catch (IOException | InterruptedException e){
            Platform.runLater(() -> this.countryText.getChildren().clear());
            Text not = new Text("Country not found :(   ");
            Platform.runLater(() -> this.countryText.getChildren().add(not));
            Text again = new Text("Try a different query!");
            Platform.runLater(() -> this.capitalText.getChildren().clear());
            Platform.runLater(() -> this.capitalText.getChildren().add(again));
        }
        // return worldResponse;
    } // getSunTime
    */

    /**
     * Create a Thread.
     * @param r Runnable object
     */
    private void runInThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    } // runInThread

    /**
     * Return the country code for the specified country.
     * @param country the country name the user enters
     */
    public String getCountryCode(String country) {
        String coco = "";
        if (country.length() <= 3) {
            return coco;
        } // non existant country name
        if (country.equalsIgnoreCase("united arab emirates")) {
            coco = "are";
        } else if (country.equalsIgnoreCase("austria")) {
            coco = "aut";
        } else if (country.equalsIgnoreCase("bangladesh")) {
            coco = "bgd";
        } else if (country.equalsIgnoreCase("bulgaria")) {
            coco = "bgr";
        } else if (country.equalsIgnoreCase("bahamas")) {
            coco = "bhs";
        } else if (country.equalsIgnoreCase("belarus")) {
            coco = "blr";
        } else if (country.equalsIgnoreCase("bermuda")) {
            coco = "bmu";
        } else if (country.equalsIgnoreCase("botswana")) {
            coco = "bwa";
        } else if (country.equalsIgnoreCase("switzerland")) {
            coco = "che";
        } else if (country.equalsIgnoreCase("chile")) {
            coco = "chl";
        } else if (country.equalsIgnoreCase("china")) {
            coco = "chn";
        } else if (country.equalsIgnoreCase("costa rica")) {
            coco = "cri";
        } else if (country.equalsIgnoreCase("united states")) {
            coco = "usa";
        } else if (country.equalsIgnoreCase("greece")) {
            coco = "grc";
        } else if (country.equalsIgnoreCase("indonesia")) {
            coco = "idn";
        } else if (country.equalsIgnoreCase("iraq")) {
            coco = "irq";
        } else if (country.equalsIgnoreCase("iran")) {
            coco = "irn";
        } else if (country.equalsIgnoreCase("portugal")) {
            coco = "prt";
        } else if (country.equalsIgnoreCase("saudi arabia")) {
            coco = "sau";
        } else if (country.equalsIgnoreCase("zambia")) {
            coco = "zmb";
        } else {
            coco = country.substring(0, 3);
        } // if else statement

        return coco;
    } // getCountryCode


} // ApiApp
