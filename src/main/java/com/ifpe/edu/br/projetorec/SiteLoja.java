/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ifpe.edu.br.projetorec;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 *
 * @author user
 */

@Entity
@Table(name="TB_SETOR")
@DiscriminatorValue(value = "F")
@PrimaryKeyJoinColumn(name="ID_PRODUTO", referencedColumnName = "ID")
public class SiteLoja {

    
}
