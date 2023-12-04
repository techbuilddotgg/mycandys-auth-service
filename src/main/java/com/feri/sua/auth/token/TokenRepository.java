package com.feri.sua.auth.token;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

  Optional<Token> findByToken(String token);

  @Query("{ 'userId' : ?0, 'revoked' : false, 'expired' : false }")
  List<Token> findAllValidTokenByUser(String userId);

}
