//package com.meetime.hubspot_integration.infrastructure.adapters.persistence.repository;
//
//import com.meetime.hubspot_integration.application.ports.outbound.OAuthTokenRepositoryPort;
//import com.meetime.hubspot_integration.core.model.OAuthToken;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public class OAuthTokenJpaAdapter implements OAuthTokenRepositoryPort {
//
//    private static final String ERROR_MESSAGE = "Failed to delete OAuthToken";
//
//    private final JpaRepository<OAuthToken, Long> jpaRepository;
//
//    public OAuthTokenJpaAdapter(JpaRepository<OAuthToken, Long> jpaRepository) {
//        this.jpaRepository = jpaRepository;
//    }
//
//    @Override
//    public Optional<OAuthToken> findLatestToken() {
//        return jpaRepository.findAll().stream().findFirst();
//    }
//
//    @Override
//    public OAuthToken save(OAuthToken token) {
//        return jpaRepository.save(token);
//    }
//
//    @Override
//    public void delete(OAuthToken token) {
//        try {
//            jpaRepository.delete(token);
//        } catch (Exception e) {
//            throw new RuntimeException(ERROR_MESSAGE, e);
//        }
//    }
//}
