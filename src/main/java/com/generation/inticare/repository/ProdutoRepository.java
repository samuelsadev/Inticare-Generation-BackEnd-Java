package com.generation.inticare.repository;

import com.generation.inticare.model.CategoriaModel;
import com.generation.inticare.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
    List<ProdutoModel> findAllByNomeProdutoContainingIgnoreCase(String nomeProduto);
    List<ProdutoModel> findAllByCategoriaModelId(Long categoriaId);
}