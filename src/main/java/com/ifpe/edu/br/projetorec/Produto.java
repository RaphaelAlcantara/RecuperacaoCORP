/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifpe.edu.br.projetorec;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */

@Entity
@Table(name = "TB_PRODUTO")
@Inheritance(strategy = InheritanceType.JOINED) 
@DiscriminatorColumn(name = "DISC_MERCADORIA", 
        discriminatorType = DiscriminatorType.STRING, length = 1)
public abstract class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_NOME", length = 50, nullable = false)
    private String nome;
    @Column(name = "TXT_TIPO", length = 50, nullable = false)
    private String tipo;
    @Column(name = "TXT_PRECO", length = 50, nullable = false)
    private double preco;    
    @ManyToMany(mappedBy = "produtos")
    @JoinTable(name = "TB_PRODUTO_LOJA", joinColumns = {
        @JoinColumn(name = "ID_LOJA")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_PRODUTO")})
    private List<Loja> lojas;

     public Produto() {
        this.lojas = new ArrayList<>();
        
    }
     
      public void adicionar(Loja loja) {
        lojas.add(loja);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Loja> getLojas() {
        return lojas;
    }

    public void setLojas(List<Loja> lojas) {
        this.lojas = lojas;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    

    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
     @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + '}';
    }


    
}
