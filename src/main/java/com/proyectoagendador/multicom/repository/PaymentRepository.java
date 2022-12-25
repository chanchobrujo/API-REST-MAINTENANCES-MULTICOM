package com.proyectoagendador.multicom.repository;

import com.proyectoagendador.multicom.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public
interface PaymentRepository extends JpaRepository<Payment, Integer> {}