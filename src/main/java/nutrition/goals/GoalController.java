package nutrition.goals;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/goals")
public class GoalController {

    private final GoalService service;

    public GoalController(GoalService service) { this.service = service; }

    @GetMapping
    public GoalResponse getActive(@RequestParam("userId") String userId,
                                  @RequestParam(name = "at", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {
        LocalDate date = (at == null) ? LocalDate.now() : at;
        Goal g = service.activeOn(userId, date).orElseThrow(() -> new NoSuchElementException("no active goal"));
        return GoalResponse.of(g);
    }

    @GetMapping("/history")
    public List<GoalResponse> history(@RequestParam("userId") String userId) {
        return service.history(userId).stream().map(GoalResponse::of).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GoalResponse create(@Valid @RequestBody CreateGoalRequest req) {
        return GoalResponse.of(service.create(req));
    }

    @PatchMapping("/{id}")
    public GoalResponse edit(@PathVariable("id") UUID id,
                             @RequestParam("userId") String userId,
                             @RequestBody UpdateGoalRequest req) {
        return GoalResponse.of(service.update(id, userId, req));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id, @RequestParam("userId") String userId) {
        service.delete(id, userId);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, NoSuchElementException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> errors(Exception e) {
        return Map.of("error", e.getClass().getSimpleName(), "message", e.getMessage());
    }
}
