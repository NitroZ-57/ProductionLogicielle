package E2E;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //permet de faire un ordre de passage précis des tests
public class DateSondeeE2ETest {
    private static Integer sondageId;
    private static Integer participantId;
    private static Integer participantId2;
    private static Integer participantId3;
    private static Integer dateSondageId;
    private static Integer sondeeId1;
    private static Integer sondeeId2;
    private static Integer sondeeId3;
    @BeforeAll
    public static void setup() {
        // Configurez l'URL de base pour l'application que vous testez
        RestAssured.baseURI = "https://productionlogicielle.onrender.com/api"; // URL de votre application
    }

    /**
     * Test 1 - Creation de trois participants et d'un sondage
     * GIVEN deux participants et un sondage
     * WHEN on les enregistre
     * THEN ils sont sauvegardé avec succès
     */
    @Test
    @Order(1)
    void givenAllParticipantAndASondage_whenCreate_thenAllSave(){
        // Création du participant
        participantId = given()
                .contentType(ContentType.JSON)
                .body("{\"nom\": \"Premier\", \"prenom\": \"William\"}")
                .when()
                .post("/participant/")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("participantId");

        System.out.println("Participant créé : " + participantId);

        // Création du second participant
        participantId2 = given()
                .contentType(ContentType.JSON)
                .body("{\"nom\": \"Second\", \"prenom\": \"Joe\"}")
                .when()
                .post("/participant/")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("participantId");

        System.out.println("Participant créé : " + participantId2);

        // Création du second participant
        participantId3 = given()
                .contentType(ContentType.JSON)
                .body("{\"nom\": \"Troisieme\", \"prenom\": \"Leo\"}")
                .when()
                .post("/participant/")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("participantId");

        System.out.println("Participant créé : " + participantId3);

        // Création du sondage
        sondageId = given()
                .contentType(ContentType.JSON)
                .body("{\"cloture\": \"false\", \"description\": \"On travaille pour le projet?\", \"fin\": \"2025-01-19T23:59:00\", \"nom\": \"Sondage\", \"createBy\": " + participantId + "}")
                .when()
                .post("/sondage/")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("sondageId");

        System.out.println("Sondage créé : " + sondageId);
    }

    /**
     * Test 2 - Création d'une date de sondage relié sur le même sondage
     * GIVEN date de sondage sur le même sondage
     * WHEN on l'enregistre
     * THEN il est sauvegardé avec succès
     */
    @Test
    @Order(2)
    void givenDateSondage_whenCreate_thenSave(){
        dateSondageId = given()
                .contentType(ContentType.JSON)
                .body("{\"date\": \"2025-01-20T14:00:00\"}")
                .when()
                .post("/sondage/" + sondageId + "/dates")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("dateSondageId");

        System.out.println("DateSondage créée : " + dateSondageId);
    }

    /**
     * Test 3 - Création de trois sondee des trois participants sur la meme date de sondage
     * GIVEN trois sondee
     * WHEN on les enregistre
     * THEN ils sont sauvegardé avec succès
     */
    @Test
    @Order(3)
    void givenThreeSondee_whenCreate_thenAllSave(){
        sondeeId1 = given()
                .contentType(ContentType.JSON)
                .body("{\"participant\": " + participantId + ", \"choix\": \"DISPONIBLE\"}")
                .when()
                .post("/date/"+dateSondageId+"/participer")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("dateSondeeId");

        System.out.println("DateSondee 1 créée : " + sondeeId1);

        sondeeId2 = given()
                .contentType(ContentType.JSON)
                .body("{\"participant\": " + participantId2 + ", \"choix\": \"PEUTETRE\"}")
                .when()
                .post("/date/"+dateSondageId+"/participer")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("dateSondeeId");

        System.out.println("DateSondee 2 créée : " + sondeeId2);

        sondeeId3 = given()
                .contentType(ContentType.JSON)
                .body("{\"participant\": " + participantId3 + ", \"choix\": \"INDISPONIBLE\"}")
                .when()
                .post("/date/"+dateSondageId+"/participer")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("dateSondeeId");

        System.out.println("DateSondee 3 créée : " + sondeeId3);
    }

    /**
     * Test 4 - Suppression de la date de sondage qui regroupe toutes les sondees
     * GIVEN la date du sondage excistant
     * WHEN on le supprime
     * THEN il est supprimé avec succès
     */
    @Test
    @Order(4)
    void givenADateSondageAlive_whenDelete_thenAreRemoved(){
        given()
                .when()
                .delete("/date/" + dateSondageId)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("DateSondage supprimée : " + dateSondageId);
    }

    /**
     * Test 5 - Suppression des trois participants et du sondage existant
     * GIVEN trois participants et un sondage existant
     * WHEN on les supprime
     * THEN ils sont supprimer avec succès
     */
    @Test
    @Order(5)
    void givenAll_thenDelete_whenAreRemoved(){
        //Suppression du sondage
        given()
                .when()
                .delete("/sondage/"+sondageId)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Sondage supprimée");

        //Suppression des participants
        given()
                .when()
                .delete("/participant/" + participantId)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Participant 1 supprimé : " + participantId);

        given()
                .when()
                .delete("/participant/" + participantId2)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Participant 2 supprimé : " + participantId2);

        given()
                .when()
                .delete("/participant/" + participantId3)
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Participant 2 supprimé : " + participantId3);
    }
}
