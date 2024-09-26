package com.generation.inticare.controller;

import com.generation.inticare.model.ProdutoModel;
import com.generation.inticare.repository.CategoriaRepository;
import com.generation.inticare.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;



//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<ProdutoModel>> getAll() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> getById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ProdutoModel> post(@Valid @RequestBody ProdutoModel produtoModel) {
        if (categoriaRepository.existsById(produtoModel.getCategoriaModel().getId()))
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(produtoRepository.save(produtoModel));
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existente!", null);
    }

    @PutMapping
    public ResponseEntity<ProdutoModel> put(@Valid @RequestBody ProdutoModel produtoModel) {
        if (produtoRepository.existsById(produtoModel.getId())) {
            if (categoriaRepository.existsById(produtoModel.getCategoriaModel().getId()))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(produtoRepository.save(produtoModel));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe", null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<ProdutoModel> produtoModel = produtoRepository.findById(id);
        if (produtoModel.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        produtoRepository.deleteById(id);
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity <List<ProdutoModel>> getByNome(@PathVariable String nome) {
        return ResponseEntity.ok(produtoRepository
                .findAllByNomeProdutoContainingIgnoreCase(nome));
    }
    
    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<ProdutoModel>> getByCategoria(@PathVariable Long id) {
        // Verifica se a categoria existe
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Busca produtos pela categoria
        List<ProdutoModel> produtos = produtoRepository.findAllByCategoriaModelId(id);
        return ResponseEntity.ok(produtos);
    }
}