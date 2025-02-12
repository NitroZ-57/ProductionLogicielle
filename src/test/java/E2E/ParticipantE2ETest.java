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

    private static Long sondageId;
    private static Long participantId;
    private static Long dateId;
    private static Long commentaireId;
    @BeforeAll
    public static void setup() {
        // Configurez l'URL de base pour l'application que vous testez
        RestAssured.baseURI = "https://productionlogicielle.onrender.com/api"; // URL de votre application
    }


    /**
     * Test: Créer un participant
     */
    @Test
    @Order(1)
    void testCreateParticipant() {
        participantId = given()
                .contentType(ContentType.JSON)
                .body(String.format("{\"nom\": \"Comte\", \"prenom\": \"Quentin\"}"))
                .when()
                .post("/participant/")
                .then()
                .statusCode(201)
                .body("nom", equalTo("Comte"))
                .body("prenom", equalTo("Quentin"))
                .extract()
                .path("participant_id");

        System.out.println("Participant créé : " + participantId);
    }

    /**
     *Test: Créer un sondage
     */
    @Test
    @Order(2)
    void testCreateSondage() {
        sondageId = given()
                .contentType(ContentType.JSON)
                .body(String.format("{\"nom\": \"Envie de mourir\", \"description\": \"Trop de projet et Esteban est meilleure que moi, qui veut me remplacer?\", \"fin\": \"2025-12-31T23:59:59\", \"participant_id\": %d}", participantId))
                .when()
                .post("/sondage/%d/", participantId)
                .then()
                .statusCode(201)
                .body("nom", equalTo("Envie de mourir"))
                .extract()
                .path("sondage_id");

        System.out.println("Sondage créé : " + sondageId);
    }

    /**
     *Test: Ajouter une date à un sondage
     */
    @Test
    @Order(3)
    void testAddDateToSondage() {
        dateId = given()
                .contentType(ContentType.JSON)
                .body(String.format("{\"date\": \"2025-06-15T10:00:00\", \"sondage_id\": %d}", sondageId))
                .when()
                .post("/sondage/%d/dates", sondageId)
                .then()
                .statusCode(201)
                .extract()
                .path("date_sondage_id");

        System.out.println("Date ajoutée : " + dateId);
    }

    /**
     *Test: Voter pour une date
     */
    @Test
    @Order(4)
    void testVoteForDate() {
        given()
                .contentType(ContentType.JSON)
                .body(String.format("{\"choix\": \"Disponible\", \"participant_id\": %d, \"date_sondage_id\": %d}", participantId, dateId))
                .when()
                .post("/date/%d/participer", dateId)
                .then()
                .statusCode(201)
                .body("choix", equalTo("Disponible"));

        System.out.println("Vote ajouté");
    }

    /**
     * Test: Ajouter un commentaire
     */
    @Test
    @Order(5)
    void testAddCommentaire() {
        commentaireId = given()
                .contentType(ContentType.JSON)
                .body(String.format("{\"commentaire\": \"Esteban - bhahaha cheh!\", \"participant_id\": %d, \"sondage_id\": %d}", participantId, sondageId))
                .when()
                .post("/sondage/%d/commentaires", sondageId)
                .then()
                .statusCode(201)
                .extract()
                .path("commentaire_id");

        System.out.println("Commentaire ajouté : " + commentaireId);
    }

    /**
     *Test: Récupérer les meilleures dates
     */
    @Test
    @Order(6)
    void testGetBestDate() {
        given()
                .when()
                .get("/sondage/%d/best", sondageId)
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));

        System.out.println("Meilleure date récupérée");
    }

    /**
     * Test: Supprimer un commentaire
     */
    @Test
    @Order(7)
    void testDeleteCommentaire() {
        given()
                .when()
                .delete("/commentaire/%d", commentaireId)
                .then()
                .statusCode(204);

        System.out.println("Commentaire supprimé");
    }

    /**
     * Test: Supprimer une date
     */
    @Test
    @Order(8)
    void testDeleteDate() {
        given()
                .when()
                .delete("/date/%d", dateId)
                .then()
                .statusCode(204);

        System.out.println("Date supprimée");
    }

    /**
     * Test: Supprimer le sondage
     */
    @Test
    @Order(9)
    void testDeleteSondage() {
        given()
                .when()
                .delete("/sondage/%d", sondageId)
                .then()
                .statusCode(204);

        System.out.println("Sondage supprimé");
    }

    /**
     * Test: Supprimer un participant
     */
    @Test
    @Order(10)
    void testDeleteParticipant() {
        given()
                .when()
                .delete("/participant/%d", participantId)
                .then()
                .statusCode(204);

        System.out.println("Participant supprimé");
    }
}
