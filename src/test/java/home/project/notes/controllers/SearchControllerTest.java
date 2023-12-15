package home.project.notes.controllers;

import home.project.notes.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SearchControllerTest {
    @Mock
    private SearchService searchService;
    @Mock
    private Model model;
    private SearchController searchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchController = new SearchController(searchService);
    }

    @Test
    void getSearchPageShouldReturnSearchPageViewName() {
        String viewName = searchController.getSearchPage();
        assertEquals("search/search", viewName);
    }

    @Test
    void searchShouldReturnSearchResultsViewNameAndAttributes() {
        String query = "John Doe";
        when(searchService.searchByFullName("John", "Doe")).thenReturn(null);

        String viewName = searchController.search(query, model);

        verify(model).addAttribute(eq("searchResult"), any());
        assertEquals("search/results", viewName);
    }

    @Test
    void fullNameSearchShouldReturnSearchResultsViewNameAndAttributes() {
        String firstName = "John";
        String lastName = "Doe";
        when(searchService.searchByFullName(firstName, lastName)).thenReturn(null);

        String viewName = searchController.fullNameSearch(firstName, lastName, model);

        verify(model).addAttribute(eq("searchResult"), any());
        assertEquals("search/results", viewName);
    }

    @Test
    void phoneSearchShouldReturnSearchResultsViewNameAndAttributes() {
        String query = "1234567890";
        when(searchService.phoneSearch(query)).thenReturn(null);

        String viewName = searchController.phoneSearch(query, model);

        verify(model).addAttribute(eq("searchResult"), any());
        assertEquals("search/results", viewName);
    }

    @Test
    void yearSearchShouldReturnSearchResultsViewNameAndAttributes() {
        int year = 1990;
        when(searchService.yearSearch(year)).thenReturn(null);

        String viewName = searchController.yearSearch(year, model);

        verify(model).addAttribute(eq("searchResult"), any());
        assertEquals("search/results", viewName);
    }

    @Test
    void searchByAddressShouldReturnSearchResultsViewNameAndAttributes() {
        when(searchService.addressSearch(any(), any(), any(), any(), any())).thenReturn(null);

        String viewName = searchController.searchByAddress("USA", "New York", "Broadway", 123, 456, model);

        verify(model).addAttribute(eq("searchResult"), any());
        assertEquals("search/results", viewName);
    }
}
