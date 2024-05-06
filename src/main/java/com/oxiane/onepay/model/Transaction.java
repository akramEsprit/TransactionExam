package com.oxiane.onepay.model;

import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Transaction {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MoyenPaiement moyenPaiementType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTransaction status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Command> commands;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public MoyenPaiement getMoyenPaiementType() {
		return moyenPaiementType;
	}

	public void setMoyenPaiementType(MoyenPaiement moyenPaiementType) {
		this.moyenPaiementType = moyenPaiementType;
	}

	public StatusTransaction getStatus() {
		return status;
	}

	public void setStatus(StatusTransaction status) {
		this.status = status;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}
    
}

