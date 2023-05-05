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
import java.io.IOException;
import com.google.gson.stream.JsonReader;
import java.io.StringReader;

/**
 * This application takes in a request for a country name and uses apiworldbank.org.
 * The website gives back the longitude and latitude values which are used to find the
 * sunrise and sunset time of the capital city using api.sunrise-sunset.org.
 * Thank you for teaching me this semester I really enjoyed your class Dr. Barnes!
 * And thank you to the TAs!!
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
    HBox insH;
    TextFlow instructions;
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
        insH = new HBox();
        Text iText = new Text("Find out when the sun will rise and set in the capital ");
        Text tTwo = new Text("city of the given country!  (Not all countries included)");
        instructions = new TextFlow(iText, tTwo);
        countryH = new HBox();
        countryText = new TextFlow(new Text("Country:\t\t\t\t\t\t\t\t\t\t\t\t"));
        capitalText = new TextFlow(new Text("Capital: "));
        imageH = new HBox();
        sunRIV = new ImageView();
        sunSIV = new ImageView();
        sunTimeH = new HBox();
        sunriseText = new TextFlow(new Text("Sunrise Time:\t\t\t\t\t\t\t\t\t\t\t"));
        sunsetText = new TextFlow(new Text("Sunset Time: "));
    } // ApiApp



    /** {@inheritDoc} */
    @Override
    public void init() {
        root.getChildren().addAll(queryH, insH, countryH, imageH, sunTimeH);
        queryH.getChildren().addAll(searchText, searchField, searchB);
        HBox.setHgrow(this.searchField, Priority.ALWAYS);
        insH.getChildren().add(instructions);
        countryH.getChildren().addAll(countryText, capitalText);
        imageH.getChildren().addAll(sunRIV, sunSIV);
        sunTimeH.getChildren().addAll(sunriseText, sunsetText);
        sunRIV.setPreserveRatio(true);
        sunRIV.setFitWidth(400);
        sunSIV.setPreserveRatio(true);
        sunSIV.setFitWidth(400);
        String oneR = "https://creazilla-store.fra1.digitaloceanspaces.com/";
        String twoR = "cliparts/1723310/sunrise-clipart-md.png";
        Image sRise = new Image(oneR + twoR);
        String oneS = "https://creazilla-store.fra1.digitaloceanspaces.com/";
        String twoS = "cliparts/78211/sunset-clipart-md.png";
        Image sSet = new Image(oneS + twoS);
        sunRIV.setImage(sRise);
        sunSIV.setImage(sSet);

        Runnable countryRun = () -> getCountry(getCountryCode(this.searchField.getText()));
        this.searchB.setOnAction(event -> runInThread(countryRun));
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
     * Retrieve country information.
     * @param cc the country code given by the getCountryCode
     */
    public void getCountry(String cc) {
        // construct url
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create("https://api.worldbank.org/v2/country/" + cc + "?format=json"))
            .build();
        Countries csResp = null;
        Country[] countryResp = null;
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(req, BodyHandlers.ofString());
            String responseBody = response.body();
            JsonReader read = new JsonReader(new StringReader(responseBody));
            String countryName = "";
            String capitalC = "";
            String longitudeC = "";
            String latitudeC = "";
            read.beginArray();
            read.skipValue();
            read.beginArray();
            while (read.hasNext()) {
                read.beginObject();
                while (read.hasNext()) {
                    String name = read.nextName();
                    if (name.equals("id")) {
                        read.skipValue();
                    } else if (name.equals("iso2Code")) {
                        read.skipValue();
                    } else if (name.equals("name")) {
                        countryName = read.nextString();
                    } else if (name.equals("region")) {
                        read.skipValue();
                    } else if (name.equals("adminregion")) {
                        read.skipValue();
                    } else if (name.equals("incomeLevel")) {
                        read.skipValue();
                    } else if (name.equals("capitalCity")) {
                        capitalC = read.nextString();
                    } else if (name.equals("longitude")) {
                        longitudeC = read.nextString();
                    } else if (name.equals("latitude")) {
                        latitudeC = read.nextString();
                    } else {
                        read.skipValue();
                    }
                }
                read.endObject();
            }
            read.endArray();
            read.close();
            // update values displayed
            Platform.runLater(() -> this.countryText.getChildren().clear());
            Text theName = new Text("Country:  " + countryName + "\t\t\t\t\t\t\t\t\t");
            Platform.runLater(() -> this.countryText.getChildren().add(theName));
            Text cap = new Text("Capital:  " + capitalC);
            Platform.runLater(() -> this.capitalText.getChildren().clear());
            Platform.runLater(() -> this.capitalText.getChildren().add(cap));
            // THIS IS FOR SUN TIMES
            SunResult sRes = getSunTime(latitudeC, longitudeC);
            Platform.runLater(() -> this.sunriseText.getChildren().clear());
            Text riseTime = new Text("Sunrise Time:  " + sRes.sunrise + "\t\t\t\t\t\t\t\t");
            Platform.runLater(() -> this.sunriseText.getChildren().add(riseTime));
            Text setTime = new Text("Sunset Time:  " + sRes.sunset);
            Platform.runLater(() -> this.sunsetText.getChildren().clear());
            Platform.runLater(() -> this.sunsetText.getChildren().add(setTime));
        } catch (IOException | InterruptedException | IllegalStateException e) {
            errorUpdate();
        } catch (IllegalArgumentException ie) {
            errorUpdate();
        } // try catch

    } // getCountry


    /**
     * Retrieve sunset and sunrise information.
     * @param lat the latitude value
     * @param lon the longitude value
     * @return the results from the website about the sunrise and sunset times
     */
    public SunResult getSunTime(String lat, String lon) {
        // construct url
        //THROW EXCEPTIONS??
        String urlLat = URLEncoder.encode(lat, StandardCharsets.UTF_8);
        String urlLong = URLEncoder.encode(lon, StandardCharsets.UTF_8);
        String queryS = String.format("?lat=%s&lng=%s", urlLat, urlLong);
        HttpRequest reqS = HttpRequest.newBuilder()
            .uri(URI.create("https://api.sunrise-sunset.org/json" + queryS))
            .build();
        SunResponse sResponse = null;
        SunResult sResult = null;
        try {
            HttpResponse<String> responseS = HTTP_CLIENT.send(reqS, BodyHandlers.ofString());
            String responseBody = responseS.body();
            sResponse = GSON.<SunResponse>fromJson(responseBody, SunResponse.class);
            sResult = sResponse.results;
        } catch (IOException | InterruptedException e) {
            errorUpdate();
        }
        return sResult;
    } // getSunTime


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
     * @return the code for the specified country
     */
    public String getCountryCode(String country) {
        String coco = "";
        if (country.length() < 3) {
            coco = "xxx";
        } else if (country.equalsIgnoreCase("united arab emirates")) {
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

    /**
     * Update what is displayed if no results are found.
     */
    public void errorUpdate() {
        Platform.runLater(() -> this.countryText.getChildren().clear());
        Text not = new Text("Country not found :( Try a different query! ");
        Platform.runLater(() -> this.countryText.getChildren().add(not));
        Platform.runLater(() -> this.capitalText.getChildren().clear());
        Platform.runLater(() -> this.sunriseText.getChildren().clear());
        Text emptyRise = new Text("Sunrise Time:\t\t\t\t\t\t\t\t\t\t\t");
        Platform.runLater(() -> this.sunriseText.getChildren().add(emptyRise));
        Text emptySet = new Text("Sunset Time:");
        Platform.runLater(() -> this.sunsetText.getChildren().clear());
        Platform.runLater(() -> this.sunsetText.getChildren().add(emptySet));
    } // errorUpdate

} // ApiApp
