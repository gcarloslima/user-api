package br.com.edu.iftm.tspi.userapi.repositories;

import br.com.edu.iftm.tspi.userapi.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByCpf(String cpf);

    List<User> queryByNomeLike(String name);

}
