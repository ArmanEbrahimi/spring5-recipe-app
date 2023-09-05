package guru.springframework.controller;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class RecipeControllerTest {
    @Mock
    RecipeService recipeService;
    RecipeController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeService);

    }

    @Test
    public void showById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1l);
        when(recipeService.findById(anyLong())).thenReturn(recipe);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"));
    }
    @Test
    public void testGetNewRecipeForm() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }
    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/recipe/show/1"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/show/1"));

    }

}