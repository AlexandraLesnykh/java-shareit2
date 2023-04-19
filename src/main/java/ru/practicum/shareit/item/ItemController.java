package ru.practicum.shareit.item;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comments.dto.CommentDto;
import ru.practicum.shareit.comments.model.Comment;
import ru.practicum.shareit.exeptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(value = "/items",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> findAll(@RequestHeader(value = "X-Sharer-User-Id") long ownerId) {
        return itemService.findAll(ownerId);
    }

    @GetMapping(value = "/{id}")
    public Item findItem(@PathVariable("id") @NotNull Long id,
                         @RequestHeader(value = "X-Sharer-User-Id") long ownerId) {
        return itemService.findItem(id, ownerId);
    }

    @PostMapping
    public Item create(@RequestBody @Valid Item item, @RequestHeader(value = "X-Sharer-User-Id") long ownerId)
            throws ValidationException {

        if (!item.isAvailable() || item.getName().isEmpty() || item.getDescription() == null) {
            throw new ValidationException("Error");
        }
        return itemService.create(item, ownerId);
    }

    @PatchMapping(value = "/{id}")
    public Item update(@RequestBody @Valid ItemDto item, @PathVariable("id") @NotNull Long id,
                       @RequestHeader(value = "X-Sharer-User-Id") long ownerId) {
        return itemService.update(item, id, ownerId);
    }

    @GetMapping(value = "/search")
    public List<Item> search(@RequestParam String text) {
        return itemService.search(text);
    }

    @PostMapping(value = "/{id}/comment")
    public CommentDto addComment(@RequestBody @Valid Comment comment, @PathVariable("id") @NotNull Long id,
                                 @RequestHeader(value = "X-Sharer-User-Id") long ownerId) throws ValidationException {
        return itemService.addComment(comment, id, ownerId);
    }
}
