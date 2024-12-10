package br.com.edu.iftm.tspi.userapi.services;

import com.github.javafaker.Faker;
import br.com.edu.iftm.tspi.userapi.exceptions.UserNotFoundException;
import br.com.edu.iftm.tspi.userapi.models.User;
import br.com.edu.iftm.tspi.userapi.models.dto.UserDTO;
import br.com.edu.iftm.tspi.userapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<UserDTO> getAll() {
        List<User> usuarios = repository.findAll();
        return usuarios.stream().map(UserDTO::convert).toList();
    }

    public UserDTO findById(String id) {
        User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
        return UserDTO.convert(user);
    }

    public UserDTO save(UserDTO userDTO) {
        User user = User.convert(userDTO);
        return UserDTO.convert(repository.save(user));
    }

    public UserDTO delete(String id) {
        User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
        repository.delete(user);
        return UserDTO.convert(user);
    }

    public UserDTO findByCpf(String cpf) {
        User user = repository.findByCpf(cpf).orElseThrow(UserNotFoundException::new);
        return UserDTO.convert(user);
    }

    public List<UserDTO> queryByNomeLike(String name) {
        List<User> usuarios = repository.queryByNomeLike(name);
        return usuarios.stream().map(UserDTO::convert).toList();
    }

    public UserDTO editUser(String id, UserDTO userDTO) {
        User user = repository.findById(id).orElseThrow(UserNotFoundException::new);

        updateIfNotNullAndDifferent(userDTO.getNome(), user.getNome(), user::setNome);
        updateIfNotNullAndDifferent(userDTO.getCpf(), user.getCpf(), user::setCpf);
        updateIfNotNullAndDifferent(userDTO.getEndereco(), user.getEndereco(), user::setEndereco);
        updateIfNotNullAndDifferent(userDTO.getEmail(), user.getEmail(), user::setEmail);
        updateIfNotNullAndDifferent(userDTO.getTelefone(), user.getTelefone(), user::setTelefone);

        return UserDTO.convert(repository.save(user));
    }

    public Page<UserDTO> getAllPage(Pageable pageable) {
        Page<User> usuarios = repository.findAll(pageable);
        return usuarios.map(UserDTO::convert);
    }

    public UserDTO insertRandomUser() {
        Faker faker = new Faker();
        User user = new User();

        user.setCpf(faker.number().digits(11));
        user.setNome(faker.harryPotter().character());
        user.setEmail(faker.internet().emailAddress());
        user.setEndereco(faker.address().fullAddress());
        user.setDataCadastro(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        user.setTelefone(faker.phoneNumber().cellPhone());

        return UserDTO.convert(repository.save(user));
    }

    private <T> void updateIfNotNullAndDifferent(T newValue, T currentValue, Consumer<T> setter) {
        if (newValue != null && !newValue.equals(currentValue)) {
            setter.accept(newValue);
        }
    }

}
