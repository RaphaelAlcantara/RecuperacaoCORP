/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifpe.edu.br.projetorec;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author user
 */
@Entity
@Table(name = "TB_LOJA")
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_LOJA_PRODUTO", joinColumns = {
        @JoinColumn(name = "ID_LOJA")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_PRODUTO")})
    protected List<Produto> produtos;

    @OneToMany(mappedBy = "loja", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<Venda> vendas;

    @Column(name = "TXT_CNPJ", nullable = false, length = 18, unique = true)
    protected String cnpj;

    @Column(name = "TXT_NOME", nullable = false, length = 255)
    protected String nome;

    @Column(name = "TXT_EMAIL", nullable = false, length = 50)
    protected String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_NASCIMENTO", nullable = true)
    protected Date dataNascimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Produto produto) {
        if (this.produtos == null) {
            this.produtos = new ArrayList<>();
        }

        this.produtos.add(produto);
        produto.setLojas(this);
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(Venda venda) {
        if (this.vendas == null) {
            this.vendas = new ArrayList<>();
        }

        this.vendas.add(venda);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
