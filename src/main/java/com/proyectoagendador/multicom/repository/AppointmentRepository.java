package com.proyectoagendador.multicom.repository;

import com.proyectoagendador.multicom.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public
interface AppointmentRepository extends JpaRepository<Appointment, String> {}