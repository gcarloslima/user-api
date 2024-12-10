package br.com.edu.iftm.tspi.userapi.models;


import br.com.edu.iftm.tspi.userapi.models.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    @Field("nome")
    private String nome;

    @Field("cpf")
    private String cpf;

    @Field("endereco")
    private String endereco;

    @Field("email")
    private String email;

    @Field("telefone")
    private String telefone;

    @Field("dataCadastro")
    private LocalDateTime dataCadastro;

    public static User convert(UserDTO userDTO) {
        User user = new User();
        user.setNome(userDTO.getNome());
        user.setCpf(userDTO.getCpf());
        user.setEndereco(userDTO.getEndereco());
        user.setEmail(userDTO.getEmail());
        user.setTelefone(userDTO.getTelefone());
        user.setDataCadastro(LocalDateTime.now());
        return user;
    }


}
