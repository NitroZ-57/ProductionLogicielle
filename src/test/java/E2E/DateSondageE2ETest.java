package E2E;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //permet de faire un ordre de passage précis des tests
public class DateSondageE2ETest {
    private static Integer sondageId;
    private static Integer participantId;
    private static Integer dateSondageId;

    @BeforeAll
    public static void setup() {
        // Configurez l'URL de base pour l'application que vous testez
        RestAssured.baseURI = "https://productionlogicielle.onrender.com/api"; // URL de votre application
    }

    /**
     * Test 1 - Création d'un participant et d'un sondage
     * GIVEN un participant avec nom et prénom + un sondage
     * WHEN on l'enregistre
     * THEN ils sont sauvegardé avec succès
     */
    @Test
    @Order(1)
    void givenAParticipantAndSondage_whenCreate_thenBothAreSaved() {
        // Création du participant
        participantId = given()
                .contentType(ContentType.JSON)
                .body("{\"nom\": \"Doe\", \"prenom\": \"John\"}")
                .when()
                .post("/participant/")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("participantId");

        System.out.println("Participant créé : " + participantId);

        // Création du sondage
        sondageId = given()
                .contentType(ContentType.JSON)
                .body("{\"cloture\": \"false\", \"description\": \"Sondage test\", \"fin\": \"2025-02-15T23:59:00\", \"nom\": \"Sondage Test\", \"createBy\": " + participantId + "}")
                .when()
                .post("/sondage/")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("sondageId");

        System.out.println("Sondage créé : " + sondageId);
    }

    /**
     * Test 2 - Création d'une DateSondage
     * GIVEN une date de sondage
     * WHEN on l'enregistre
     * THEN il est sauvegardé avec succès
     */
    @Test
    @Order(2)
    void givenADateSondage_whenCreate_thenDateSondageIsSaved() {
        dateSondageId = given()
                .contentType(ContentType.JSON)
                .body("{\"date\": \"2025-02-20T14:00:00\"}")
                .when()
                .post("/sondage/" + sondageId + "/dates")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("dateSondageId");

        System.out.println("DateSondage créée : " + dateSondageId);
    }

    /**
     * Test 3 - Recuperation une date de sondage sur son ID
     * GIVEN une date de sondage
     * WHEN on le récupère
     * THEN il est retourné avec les bonnes informations
     */
    @Test
    @Order(3)
    void givenADateSondageId_whenGet_thenCorrectDateSondageIsReturned() {
        given()
                .when()
                .get("/sondage/" + sondageId + "/dates")
                .then()
                .statusCode(200)
                .body("find { it.dateSondageId == " + dateSondageId + " }.date", startsWith("2025-02-20T14:00:00"))
                .log().all();

        System.out.println("DateSondage récupérée : " + dateSondageId);
    }

    /**
     * Test 4 - Supprimer une date de sondage
     * GIVEN une date de sondage existant
     * WHEN on le supprime
     * THEN il n'est plus disponible dans la base de données
     */
    @Test
    @Order(4)
    void givenADateSondage_whenDelete_thenDateSondageIsRemoved() {
        given()
                .when()
                .delete("/date/" + dateSondageId)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("DateSondage supprimée : " + dateSondageId);
    }

    /**
     * Test 5 - Vérifier qu'une date supprimée ne peut plus être récupérée
     * GIVEN une date de sondage supprimée
     * WHEN on tente de la récupérer
     * THEN elle retourne une erreur 404
     */
    @Test
    @Order(5)
    void givenADeletedDateSondage_whenGet_thenNotFoundErrorIsReturned() {
        given()
                .when()
                .get("/date/" + dateSondageId)
                .then()
                .statusCode(405)
                .log().all();

        System.out.println("Vérification : la date de sondage supprimée n'existe plus.");
    }

    /**
     *  Test 6 - Supprimer sondage + participant
     *  GIVEN un sondage + un participant existant
     *  WHEN on les supprime
     *  THEN il n'est plus disponible dans la base de données
     */
    @Test
    @Order(6)
    void givenASondageAndParticipant_whenDelete_thenBothAreRemoved() {
        // Suppression du sondage
        given()
                .when()
                .delete("/sondage/" + sondageId)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Sondage supprimé : " + sondageId);

        // Vérification que le sondage est bien supprimé
        given()
                .when()
                .get("/sondage/" + sondageId)
                .then()
                .statusCode(500)
                .log().all();

        System.out.println("Vérification : le sondage supprimé n'existe plus.");

        // Suppression du participant
        given()
                .when()
                .delete("/participant/" + participantId)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Participant supprimé : " + participantId);
    }
}
