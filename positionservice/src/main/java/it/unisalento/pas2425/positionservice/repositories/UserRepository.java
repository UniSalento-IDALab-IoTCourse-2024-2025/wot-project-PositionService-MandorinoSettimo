package it.unisalento.pas2425.positionservice.repositories;

import it.unisalento.pas2425.positionservice.domain.Role;
import it.unisalento.pas2425.positionservice.domain.User;
import it.unisalento.pas2425.positionservice.domain.UserStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


//sarebbero l'equivalente delle classi DAO
public interface UserRepository extends MongoRepository<User, String> {

    //nome del campo preciso che è attributo del JSON
    // "Email" deve essere scritta con prima maiuscola rispetto a come l'abbiamo chiamata nella classe User "email"
    Optional<User> findByEmail(String email);
    List<User> findByNameAndSurname(String name, String surname);
    List<User> findByUserStatus(UserStatus userStatus);
    int countByUserStatusAndRole(UserStatus status, Role role);


}
