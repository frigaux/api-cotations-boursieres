package fr.fabien.api.cotations;

import com.nimbusds.jose.shaded.gson.Gson;
import fr.fabien.api.cotations.restcontroller.dto.DtoAlerteR;
import fr.fabien.jpa.cotations.enumerations.TypeAlerte;
import fr.fabien.jpa.cotations.enumerations.TypeNotification;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRestControllerAlertes {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void given2AlertesWhenGetThenReturn2Alertes() throws Exception {
        mockMvc.perform(get("/bourse/alertes").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Cache-Control"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @Order(2)
    void given1AlerteWhenPostThenReturnAlerteCree() throws Exception {
        mockMvc.perform(post("/bourse/alertes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(
                                new Gson().toJson(new DtoAlerteR(null,
                                        ConfigurationTest.VALEUR_GLE.getTicker(), "la clôture a franchi à la baisse le seuil de 20 euros",
                                        TypeAlerte.SEUIL_BAS, "CLOTURE(1) < 20", null, true,
                                        TypeNotification.SYSTEME
                                ))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Cache-Control"))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.notification").value("SYSTEME"))
                .andExpect(content().string("{\"id\":3,\"ticker\":\"GLE\",\"libelle\":\"la clôture a franchi à la baisse le seuil de 20 euros\",\"type\":\"SEUIL_BAS\",\"expression\":\"CLOTURE(1) < 20\",\"dateLimite\":null,\"declenchementUnique\":true,\"notification\":\"SYSTEME\"}"));
    }

    @Test
    @Order(2)
    void given1AlerteAvecUnTickerIntrouvableWhenPostThenReturn404() throws Exception {
        mockMvc.perform(post("/bourse/alertes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(
                                new Gson().toJson(new DtoAlerteR(null,
                                        "NIMP", "la clôture a franchi à la baisse le seuil de 20 euros",
                                        TypeAlerte.SEUIL_BAS, "CLOTURE(1) < 20", null, true,
                                        TypeNotification.SYSTEME
                                ))))
                .andExpect(status().isNotFound());
    }
}
