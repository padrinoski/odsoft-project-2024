package pt.psoft.g1.psoftg1.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pt.psoft.g1.psoftg1.PsoftG1Application;

import pt.psoft.g1.psoftg1.authormanagement.api.AuthorView;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorViewMapper;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.authormanagement.services.CreateAuthorRequest;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.shared.services.ConcurrencyService;
import pt.psoft.g1.psoftg1.shared.services.FileStorageService;

import java.nio.file.Path;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PsoftG1Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class AuthorIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorViewMapper authorViewMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ConcurrencyService concurrencyService;

    @Test
    public void testCreateAuthor() throws Exception {
        CreateAuthorRequest request = new CreateAuthorRequest();
        request.setName("Author Name");
        request.setPhoto(null);

        Author author = new Author();
        author.setName("Author Name");

        when(authorService.create(any(CreateAuthorRequest.class))).thenReturn(author);
        when(authorViewMapper.toAuthorView(any(Author.class))).thenReturn(new AuthorView());

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Author Name\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAuthorByNumber() throws Exception {
        Author author = new Author();
        author.setName("Author Name");

        when(authorService.findByAuthorNumber(anyLong())).thenReturn(Optional.of(author));
        when(authorViewMapper.toAuthorView(any(Author.class))).thenReturn(new AuthorView());

        mockMvc.perform(get("/api/authors/{authorNumber}", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAuthorPhoto() throws Exception {
        Author author = new Author();
        author.setName("Author Name");
        author.setPhoto(String.valueOf(new Photo(Path.of("photoURI"))));

        when(authorService.findByAuthorNumber(anyLong())).thenReturn(Optional.of(author));

        mockMvc.perform(delete("/api/authors/{authorNumber}/photo", "1"))
                .andExpect(status().isOk());
    }
}