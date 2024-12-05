package com.vm.gerenciaUsuarios.repositories;

import com.vm.gerenciaUsuarios.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    /*@Query("SELECT u FROM usuario u WHERE UPPER(u.nome) LIKE UPPER(CONCAT('%', :nome, '%')) ESCAPE '\\'")
    Page<Usuario> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);*/

    /*@Query(value = "SELECT * FROM usuario u ORDER BY u.email DESC OFFSET :offset LIMIT :limit", nativeQuery = true)
    List<Usuario> findAllUsuarios(@Param("offset") int offset, @Param("limit") int limit);*/

    @Query(value = "SELECT * FROM usuario u WHERE UPPER(u.nome) LIKE UPPER(CONCAT('%', :nome, '%')) " +
            "ORDER BY CASE WHEN :sort = 'nome' THEN u.nome END ASC, " +
            "CASE WHEN :sort = 'email' THEN u.email END :direction " +
            "OFFSET :offset LIMIT :limit",
            nativeQuery = true)
    List<Usuario> findAllWithNameFilter(
            @Param("nome") String nome,
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("sort") String sort,
            @Param("direction") String direction);

    @Query(value = "SELECT * FROM usuario u " +
            "ORDER BY CASE WHEN :sort = 'nome' THEN u.nome END ASC, " +
            "CASE WHEN :sort = 'email' THEN u.email END :direction " +
            "OFFSET :offset LIMIT :limit",
            nativeQuery = true)
    List<Usuario> findAllUsuarios(
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("sort") String sort,
            @Param("direction") String direction);
}
