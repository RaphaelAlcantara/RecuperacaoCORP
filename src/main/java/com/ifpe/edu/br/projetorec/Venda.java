/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifpe.edu.br.projetorec;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabri
 */
@Entity
@Table(name = "TB_VENDA")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_TIPO_PAGAMENTO", nullable = false, length = 20)
    private TipoPagamento pag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    private Cliente cliente;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_VENDA_PRODUTO", joinColumns = {
        @JoinColumn(name = "ID_VENDA")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_PRODUTO")})
    private List<Produto> produtos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LOJA", referencedColumnName = "ID")
    private Loja loja;

    @Column(name = "TXT_VALOR", length = 50, nullable = false)
    private double valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPagamento getPag() {
        return pag;
    }

    public void setPag(TipoPagamento pag) {
        this.pag = pag;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.cliente.setVendas(this);
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Produto produto) {
        if(this.produtos == null){
            this.produtos = new ArrayList<>();
        }
        
        this.produtos.add(produto);
        produto.setVendas(this);
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
        this.loja.setVendas(this);
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
