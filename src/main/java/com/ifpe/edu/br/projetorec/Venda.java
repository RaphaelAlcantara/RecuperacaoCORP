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
import java.util.List;

/**
 *
 * @author gabri
 */
@Entity
@Table(name="TB_VENDA")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_TIPO_PAGAMENTO", nullable = false, length = 20)
    private TipoPagamento pag;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_LOJA_PRODUTO", joinColumns = {
        @JoinColumn(name = "ID_LOJA")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_PRODUTO")})
    private List<Produto> produtos;
    @ManyToOne(fetch = FetchType.LAZY)
    private Loja loja;
    private double valor;
}
