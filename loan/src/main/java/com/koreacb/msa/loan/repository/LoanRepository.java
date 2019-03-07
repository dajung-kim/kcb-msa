package com.koreacb.msa.loan.repository;

import com.koreacb.msa.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
