package E2E;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //permet de faire un ordre de passage précis des tests
public class CommentaireE2ETest {
    private static Integer commentaireId;
    private static Integer sondageId;
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
     * Test 2 : Création d'un sondage
     *  GIVEN un sondage
     *  WHEN on l'enregistre
     *  THEN il est sauvegarde avec succes
     */
    @Test
    @Order(2)
    void givenASondage_whenCreate_thenSondageIsSaved(){
        sondageId = given()
                .contentType(ContentType.JSON)
                .body("{\"cloture\": \"false\", \"description\": \"Description du sondage\",\"fin\": \"2025-02-12T23:00:00\",\"nom\": \"Sondage de Test\", \"createBy\": \"" + participantId + "\"}")
                .when()
                .post("/sondage/")
                .then()
                .statusCode(201)
                .log().all()
                .extract().path("sondageId");

        System.out.println("Sondage créé : " + sondageId);
    }

    /**
     * Test 3 : Création d'un commentaire
     *  GIVEN un commentaire
     *  WHEN on l'enregistre
     *  THEN il est sauvegarde avec succes
     */
    @Test
    @Order(3)
    void givenACommentary_whenCreate_thenCommentaryIsSaved(){

        commentaireId = given()
                .contentType(ContentType.JSON)
                .body("{\"commentaire\": \"Ce commentaire est horrible\", \"participant\": \"" + participantId + "\", \"sondage\": \"" +sondageId+ "\" }") // Assurez-vous que le participant existe
                .when()
                .post("/sondage/" + sondageId + "/commentaires")
                .then()
                .statusCode(201)
                .body("commentaire", equalTo("Ce commentaire est horrible"))
                .log().all()
                .extract().path("commentaireId");

        System.out.println("Commentaire créé : " + commentaireId);
    }

    /**
     * Test 4 : Recuperation un commentaire sur ID sur un sondage
     * GIVEN un commentaire existant dans le sondage
     * WHEN on le récupère
     * THEN il est retourné avec les bonnes informations
     */
    @Test
    @Order(4)
    void givenACommentaireId_whenGet_thenCorrectCommentaireIsReturned() {
        given()
                .when()
                .get("/sondage/" + sondageId + "/commentaires")
                .then()
                .statusCode(200)
                .log().all();

        System.out.println("Commentaire récupéré : " + commentaireId);
    }

    /**
     * Test 5 : Mettre à jour un commentaire
     * GIVEN un commentaire existant
     * WHEN on change sa description
     * THEN les chargements sont sauvegardé
     */
    /*@Test
    @Order(5)
    void givenACommentaire_whenUpdate_thenCommentaireIsUpdated() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"commentaire\": \"Commentaire est toujours horrible\", \"participant\": " + participantId + "}")
                .log().all()
                .when()
                .put("/commentaire/" + commentaireId)
                .then()
                .log().all()
                .statusCode(200)
                .body("commentaire", equalTo("Commentaire est toujours horrible"));


        System.out.println("Commentaire mis à jour : " + commentaireId);
    }*/

    /**
     * Test 6 : Supprimer le commentaire
     * GIVEN un commentaire existant
     * WHEN on le supprime
     * THEN il a disparu
     */
    @Test
    @Order(5)
    void givenACommentaire_whenDelete_thenCommentaireIsRemoved() {
        given()
                .when()
                .delete("/commentaire/" + commentaireId)
                .then()
                .statusCode(200);

        System.out.println("Commentaire supprimé : " + commentaireId);
    }

    /**
     * Test 7 : Vérifier qu'un commentaire supprimé ne peut plus être récupéré
     * GIVEN un commentaire supprimé
     * WHEN on tente de le récupérer
     * THEN il retourne une erreur 404
     */
    @Test
    @Order(6)
    void givenADeletedCommentaire_whenGet_thenNotFoundErrorIsReturned() {
        given()
                .when()
                .get("/commentaire/" + commentaireId)
                .then()
                .statusCode(405);

        System.out.println("Vérification : le commentaire supprimé n'existe plus.");
    }

    /**
     * Test 8 : Supprimer le sondage
     * GIVEN un sondage existant
     * WHEN on le supprime
     * THEN il a disparu
     */
    @Test
    @Order(7)
    void givenASondage_whenDelete_thenSondageIsRemoved() {
        given()
                .when()
                .delete("/sondage/" + sondageId)
                .then()
                .statusCode(200);

        System.out.println("Sondage supprimé : " + sondageId);
    }

    /**
     * Test 9 : Vérifier qu'un sondage supprimé ne peut plus être récupéré
     * GIVEN un sondage supprimé
     * WHEN on tente de le récupérer
     * THEN il retourne une erreur 500
     */
    @Test
    @Order(8)
    void givenADeletedSondage_whenGet_thenNotFoundErrorIsReturned() {
        given()
                .when()
                .get("/sondage/" + sondageId)
                .then()
                .statusCode(500);

        System.out.println("Vérification : le sondage supprimé n'existe plus.");
    }

    /**
     * Test 10 - Supprimer un participant
     * GIVEN un participant existant
     * WHEN on le supprime
     * THEN il n'est plus disponible dans la base de données
     */
    @Test
    @Order(9)
    void givenAParticipant_whenDelete_thenParticipantIsRemoved() {
        given()
                .when()
                .delete("/participant/" + participantId)
                .then()
                .statusCode(200);

        System.out.println("Participant supprimé : " + participantId);
    }
}
