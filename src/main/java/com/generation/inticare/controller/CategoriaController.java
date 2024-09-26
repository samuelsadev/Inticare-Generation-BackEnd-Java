package com.generation.inticare.controller;

import com.generation.inticare.model.CategoriaModel;
import com.generation.inticare.repository.CategoriaRepository;
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
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<CategoriaModel>> getAll(){
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<CategoriaModel> getById(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<CategoriaModel> post(@Valid @RequestBody CategoriaModel categoriaModel) {
        try {
            System.out.println("Recebendo requisição POST com dados: " + categoriaModel);
            CategoriaModel savedCategoria = categoriaRepository.save(categoriaModel);
            System.out.println("Categoria salva com sucesso: " + savedCategoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoria);
        } catch (Exception e) {
            System.err.println("Erro ao salvar categoria: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping
    public ResponseEntity<CategoriaModel> put(@Valid @RequestBody CategoriaModel categoriaModel) {
        return categoriaRepository.findById(categoriaModel.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(categoriaRepository.save(categoriaModel)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus (HttpStatus.NO_CONTENT)
    @DeleteMapping ("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<CategoriaModel> categoriaModel = categoriaRepository.findById(id);

        if (categoriaModel.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        categoriaRepository.deleteById(id);
    }

    @GetMapping ("/genero/{genero}")
    public ResponseEntity<List<CategoriaModel>> getByGenero(@PathVariable String genero) {
        return ResponseEntity.ok(categoriaRepository
                .findAllByGeneroContainingIgnoreCase(genero));
    }

    @GetMapping ("/nome/{nome}")
    public ResponseEntity<List<CategoriaModel>> getByNome(@PathVariable String nome) {
        return ResponseEntity.ok(categoriaRepository
                .findAllByNomeContainingIgnoreCase(nome));
    }
}