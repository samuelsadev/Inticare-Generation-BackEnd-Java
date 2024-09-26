package com.generation.inticare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "tb_produto")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatorio!")
    @Size(max = 100, message = "O texto deve conter até 100 caracteres")
    @Column(name = "nome")
    private String nomeProduto;

    @NotBlank(message = "A descrição do produto é obrigatoria!")
    @Size(max = 255, message = "O texto deve conter até 255 caracteres")
    private String descricao;
    
    @NotBlank(message = "A foto do produto é obrigatoria!")
    @Size(max = 500, message = "a URL deve conter no maximo 500 caracteres")
    private String foto_produto;

    @NotNull
    private double preco;

    @NotNull
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaModel categoriaModel;

    @ManyToOne
    @JsonIgnoreProperties("produto")
    private UsuarioModel usuarioModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public CategoriaModel getCategoriaModel() {
        return categoriaModel;
    }

    public void setCategoriaModel(CategoriaModel categoriaModel) {
        this.categoriaModel = categoriaModel;
    }

    public UsuarioModel getUsuarioModel() {
        return usuarioModel;
    }

    public void setUsuarioModel(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }

	public String getFoto_produto() {
		return foto_produto;
	}

	public void setFoto_produto(String foto_produto) {
		this.foto_produto = foto_produto;
	}
    
    
}