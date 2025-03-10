package fr.fabien.api.cotations

import fr.fabien.jpa.cotations.repository.RepositoryCours
import fr.fabien.jpa.cotations.repository.RepositoryValeur
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class TestRestControllerCours(
    @Autowired val mockMvc: MockMvc
) {

    @Test
    fun `Given 1 valeur avec 2 cours when request getDernierCoursToutesValeurs then return 1 cours`() {
        mockMvc.perform(get("/bourse/cours").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.date").value(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .andExpect(jsonPath("$.cours").isArray())
            .andExpect(jsonPath("$.cours.length()").value(1))
    }

    @Test
    fun `Given 1 valeur avec 2 cours when request getDernierCoursPourUneValeur NIMP then 404`() {
        mockMvc.perform(get("/bourse/cours/NIMP").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Given 1 valeur avec 2 cours when request getDernierCoursPourUneValeur then return 1 cours`() {
        mockMvc.perform(get("/bourse/cours/${ConfigurationTest.TICKER}").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.date").value(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)))
    }

    @Test
    fun `Given 1 valeur avec 2 cours when request getDerniersCoursPourUneValeur then return 2 cours`() {
        mockMvc.perform(get("/bourse/cours/${ConfigurationTest.TICKER}/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(2))
    }
}
