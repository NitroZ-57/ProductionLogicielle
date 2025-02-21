package E2E;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //permet de faire un ordre de passage précis des tests
public class SondageE2ETest {
    private static Integer sondageId;
    private static Integer sondageId2;
    private static Integer participantId;

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
                .body("{\"nom\": \"Letuile\", \"prenom\": \"Johnny\"}")
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
     * Test 2 - Recuperation d'un sondage
     * GIVEN un sondage
     * WHEN on le récupère
     * THEN il est retourné avec les bonnes informations
     */
    @Test
    @Order(2)
    void givenASondage_whenGet_thenCorrectSondage(){
        given()
                .when()
                .get("/sondage/"+sondageId)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Sondage récupéré : " + sondageId);
    }

    /**
     * Test 3 - Creation d'un second sondage
     * GIVEN un autre sondage
     * WHEN on l'enregistre
     * THEN il est sauvegardé avec succès
     */
    @Test
    @Order(3)
    void givenASecondSondage_whenCreate_thenAreSaved(){
        sondageId2 = given()
                .contentType(ContentType.JSON)
                .body("{\"cloture\": \"true\", \"description\": \"Second Sondage test\", \"fin\": \"2025-02-15T23:59:00\", \"nom\": \"Second Sondage Test\", \"createBy\": " + participantId + "}")
                .when()
                .post("/sondage/")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("sondageId");

        System.out.println("Sondage créé : " + sondageId2);
    }

    /**
     * Test 4 - Recuperation tout les sondages
     * GIVEN les sondages
     * WHEN on les récupère
     * THEN il sont retourné avec les bonnes informations
     */
    @Test
    @Order(4)
    void givenAllSondage_whenGet_thenCorrectSondage(){
        given()
                .when()
                .get("/sondage/")
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Tout les sondages recupérés");
    }

    /**
     * Test 5 - Mettre à jour un sondage
     * GIVEN le premier sondage
     * WHEN on change son statut de cloture
     * THEN les changements sont sauvegarder
     */
    /*@Test
    @Order(5)
    void givenASondage_whenUpdate_thenSondageIsUpdated() {
        String updatedSondageJson = "{"
                + "\"sondageId\": " + sondageId + ","
                + "\"cloture\": true,"
                + "\"description\": \"Sondage test\","
                + "\"fin\": \"2025-02-15T23:59:00\","
                + "\"nom\": \"Sondage Test\","
                + "\"createBy\": " + participantId
                + "}";

        given()
                .contentType(ContentType.JSON)
                .body(updatedSondageJson)
                .log().all()
                .when()
                .put("/sondage/" + sondageId)
                .then()
                .log().all()
                .statusCode(200);

        System.out.println("Sondage mis à jour : " + sondageId);
    }*/

    /**
     *  Test 5 - Supprimer les sondages + participant
     *  GIVEN un sondage + un participant existant
     *  WHEN on les supprime
     *  THEN il n'est plus disponible dans la base de données
     */
    @Test
    @Order(5)
    void givenAllSondageAndAParticipant_whenDelete_thenBothAreRemoved() {
        // Suppression du premier sondage
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

        // Suppression du second sondage
        given()
                .when()
                .delete("/sondage/" + sondageId2)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Sondage supprimé : " + sondageId2);

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
