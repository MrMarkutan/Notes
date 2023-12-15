package home.project.notes.controllers;

import home.project.notes.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    private static final String SEARCH_VIEW_FOLDER = "search";

    private static final String SEARCH_PAGE = SEARCH_VIEW_FOLDER + "/search";
    private static final String SEARCH_RESULTS = SEARCH_VIEW_FOLDER + "/results";

    @GetMapping
    public String getSearchPage() {
        return SEARCH_PAGE;
    }

    @GetMapping("/name")
    public String search(@RequestParam(name = "name") String query, Model model) {
        String[] split = query.split(" ");

        if (split.length > 0) {
            model.addAttribute("searchResult", searchService.searchByFullName(
                    split[0],
                    split.length > 1 ? split[1] : null
            ));
        }

        return SEARCH_RESULTS;
    }


    @GetMapping("/fullName")
    public String fullNameSearch(
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            Model model) {
        model.addAttribute("searchResult", searchService.searchByFullName(firstName, lastName));
        return SEARCH_RESULTS;
    }

    @GetMapping("/phone")
    public String phoneSearch(@RequestParam(name = "number") String query, Model model) {
        model.addAttribute("searchResult", searchService.phoneSearch(query));
        return SEARCH_RESULTS;
    }

    @GetMapping("/year")
    public String yearSearch(@RequestParam(name = "year") int year, Model model) {
        model.addAttribute("searchResult", searchService.yearSearch(year));
        return SEARCH_RESULTS;
    }

    @GetMapping("/address")
    public String searchByAddress(@RequestParam(required = false) String country,
                                  @RequestParam(required = false) String city,
                                  @RequestParam(required = false) String street,
                                  @RequestParam(required = false) Integer building,
                                  @RequestParam(required = false) Integer apartment,
                                  Model model) {
        model.addAttribute("searchResult",
                searchService.addressSearch(country, city, street, building, apartment));
        return SEARCH_RESULTS;
    }
}
