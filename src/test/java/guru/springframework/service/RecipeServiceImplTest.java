package guru.springframework.service;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeHashSet = new HashSet<>();
        recipeHashSet.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipeHashSet);
        assertEquals(recipeService.getRecipes().size(),1);
        verify(recipeRepository,times(1)).findAll();
    }
    @Test
    public void testDeleteById(){
        Long idToDelete= Long.valueOf(1);
        recipeService.deleteById(idToDelete);
        verify(recipeRepository,times(1)).deleteById(anyLong());
    }
    @Test(expected = NotFoundException.class)
    public void testNotFound(){
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyLong()))
                .thenReturn(recipeOptional);
        recipeService.findById(1l);
    }
}