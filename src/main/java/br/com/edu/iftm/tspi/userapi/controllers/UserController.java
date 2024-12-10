package br.com.edu.iftm.tspi.userapi.controllers;

import br.com.edu.iftm.tspi.userapi.models.dto.UserDTO;
import br.com.edu.iftm.tspi.userapi.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save(@RequestBody @Valid UserDTO userDTO) {
        return service.save(userDTO);
    }

    @GetMapping("/{cpf}/cpf")
    public UserDTO findByCpf(@PathVariable String cpf) {
        return service.findByCpf(cpf);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserDTO delete(@PathVariable String id) {
        return service.delete(id);
    }

    @GetMapping("/search")
    public List<UserDTO> queryByName(@RequestParam(name = "nome") String name) {
        return service.queryByNomeLike(name);
    }

    @PostMapping("/random")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save() {
        return service.insertRandomUser();
    }

    @GetMapping("/pageable")
    public Page<UserDTO> getAllPageable(Pageable pageable) {
        return service.getAllPage(pageable);
    }

    @PatchMapping("/{id}")
    public UserDTO editUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return service.editUser(id, userDTO);
    }

}
