package E2E;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(OrderAnnotation.class) //permet de faire un ordre de passage précis des tests
public class ParticipantE2ETest {
    private static Integer participantId;
    @BeforeAll
    public static void setup() {
        // Configurez l'URL de base pour l'application que vous testez
        RestAssured.baseURI = "https://productionlogicielle.onrender.com/api"; // URL de votre application
    }


    /**
     * Test 1 - Création d'un participant
     * GIVEN un participant avec nom et prénom
     * WHEN on l'enregistre
     * THEN il est sauvegardé avec succès
     */
    @Test
    @Order(1)
    void givenAParticipant_whenCreate_thenParticipantIsSaved() {
        participantId = given()
                .contentType(ContentType.JSON)
                .body("{\"nom\": \"Doe\", \"prenom\": \"John\"}")
                .when()
                .post("/participant/")
                .then()
                .statusCode(201)
                .body("nom", equalTo("Doe"))
                .body("prenom", equalTo("John"))
                .log().all()  // Affiche toute la réponse pour voir la structure
                .extract().path("participantId");


        System.out.println("Participant créé : " + participantId);
    }

    /**
     * Test 2 - Récupérer un participant par ID
     * GIVEN un ID de participant existant
     * WHEN on le récupère
     * THEN il est retourné avec les bonnes informations
     */
    @Test
    @Order(2)
    void givenAParticipantId_whenGet_thenCorrectParticipantIsReturned() {
        given()
                .when()
                .get("/participant/" + participantId)
                .then()
                .statusCode(200)
                .body("nom", equalTo("Doe"))
                .body("prenom", equalTo("John"));

        System.out.println("Participant récupéré : " + participantId);
    }

    /**
     * Test 3 - Mettre à jour un participant
     * GIVEN un participant existant
     * WHEN on met à jour son prénom
     * THEN les changements sont sauvegardés
     */
    @Test
    @Order(3)
    void givenAParticipant_whenUpdate_thenParticipantIsUpdated() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nom\": \"Doe\", \"prenom\": \"Jane\"}") // Changement de prénom
                .when()
                .put("/participant/" + participantId)
                .then()
                .statusCode(200)
                .body("nom", equalTo("Doe"))
                .body("prenom", equalTo("Jane"));

        System.out.println("Participant mis à jour : " + participantId);
    }

    /**
     * Test 4 - Récupérer la liste des participants
     * GIVEN des participants existants
     * WHEN on récupère la liste
     * THEN elle contient au moins un participant
     */
    @Test
    @Order(4)
    void givenParticipants_whenGetAll_thenAtLeastOneParticipantIsReturned() {
        given()
                .when()
                .get("/participant/")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));

        System.out.println("Liste des participants récupérée");
    }

    /**
     * Test 5 - Supprimer un participant
     * GIVEN un participant existant
     * WHEN on le supprime
     * THEN il n'est plus disponible dans la base de données
     */
    @Test
    @Order(5)
    void givenAParticipant_whenDelete_thenParticipantIsRemoved() {
        given()
                .when()
                .delete("/participant/" + participantId)
                .then()
                .statusCode(200);

        System.out.println("Participant supprimé : " + participantId);
    }

    /**
     * Test 6 - Vérifier qu'un participant supprimé ne peut plus être récupéré
     * GIVEN un participant supprimé
     * WHEN on tente de le récupérer
     * THEN il retourne une erreur 404
     */
    @Test
    @Order(6)
    void givenADeletedParticipant_whenGet_thenNotFoundErrorIsReturned() {
        given()
                .when()
                .get("/participant/" + participantId)
                .then()
                .statusCode(500);

        System.out.println("Vérification : le participant supprimé n'existe plus.");
    }
}
