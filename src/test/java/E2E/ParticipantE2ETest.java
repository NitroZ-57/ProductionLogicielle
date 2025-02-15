package E2E;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ParticipantE2ETest {
/*
    @BeforeAll
    public static void setup() {
        // Configurez l'URL de base pour l'application que vous testez
        RestAssured.baseURI = "https://productionlogicielle.onrender.com/api"; // URL de votre application
    }


    @Test
    public void testCreateParticipant() {
        // Exemple d'une requête POST pour créer un nouveau participant
        String jsonBody = "{ \"nom\": \"Dupont\", \"prenom\": \"Jean\" }";  // Corps de la requête (JSON)

        given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("/participant/")  // Endpoint de création
                .then()
                .statusCode(201)  // Vérifie que le statut de la réponse est 201 (création réussie)
                .body("nom", equalTo("Dupont"))
                .body("prenom", equalTo("Jean"));
    }*/
}
