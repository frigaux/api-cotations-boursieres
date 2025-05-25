package fr.fabien.api.cotations

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class TestRestControllerValeur(
    @Autowired val mockMvc: MockMvc
) {

    @Test
    fun `Given 1 valeur avec 2 cours when request getValeurs then return 1 valeur`() {
        mockMvc.perform(get("/bourse/valeurs").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(header().exists("Cache-Control"))
    }

    @Test
    fun `Given 1 valeur avec 2 cours when request getValeur NIMP then 404`() {
        mockMvc.perform(get("/bourse/valeurs/NIMP").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Given 1 valeur avec 2 cours when request getValeur then return 1 valeur`() {
        mockMvc.perform(get("/bourse/valeurs/${ConfigurationTest.TICKER}").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.ticker").value(ConfigurationTest.TICKER))
            .andExpect(header().exists("Cache-Control"))
    }
}
