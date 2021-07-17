package com.proyectoIntegrador.MultiCom.service;

import com.proyectoIntegrador.MultiCom.logic.myFuntions;
import com.proyectoIntegrador.MultiCom.model.*;
import com.proyectoIntegrador.MultiCom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.*;
import java.util.*;

@Service
@Transactional
public class ReservationService {

	@Autowired
	ReservationRepository reservaRepository;

	@Autowired
	UserService usuarioService;

	public List<Reserva> list() {
		return reservaRepository.findAll();
	}

	public void save(Reserva reserva) {
		reservaRepository.save(reserva);
	}  

	public boolean getByFecha(String fecha, String hora1, String hora2) {
		List<Reserva> lista1 = reservaRepository.findAll();
		Boolean value = false;

		for (int i = 0; i < lista1.size(); i++) {
			Reserva obj = (Reserva) lista1.get(i);
			if (obj.getFecha().equals(fecha) && !obj.getEstado().equals("Cita cancelada."))
				value = myFuntions.verifyCross(fecha, obj.getHoraInicio(), obj.getHoraFin(), hora1, hora2);
		}

		return value;
	}

	public Optional<Reserva> getByHoraInicio(String horaInicio) {
		return reservaRepository.findByHoraInicio(horaInicio);
	}

	public Optional<Reserva> getByHoraFin(String horaFin) {
		return reservaRepository.findByHoraFin(horaFin);
	}

	public Optional<Reserva> getById(int id) {
		return reservaRepository.findById(id);
	}

	public List<Reserva> listReservationFilter(int id, boolean filter) {
		List<Reserva> listaDeReservasPorCliente = reservaRepository.findByUsuario(usuarioService.getById(id).get());
		List<Reserva> listaDeReservasPorClienteFilter = new ArrayList<Reserva>();

		for (int i = 0; i < listaDeReservasPorCliente.size(); i++) {
			Reserva obj = (Reserva) reservaRepository.findByUsuario(usuarioService.getById(id).get()).get(i);
			if (obj.getEstado().equals("Cita cancelada.") == filter)
				listaDeReservasPorClienteFilter.add(obj);
		}

		return listaDeReservasPorClienteFilter;
	}

	public List<Reserva> getByUserNC(int id) {
		List<Reserva> listaDeReservasPorClienteNC = listReservationFilter(id, false);
		return listaDeReservasPorClienteNC;
	}

	public List<Reserva> getByUserC(int id) {
		List<Reserva> listaDeReservasPorClienteC = listReservationFilter(id, true);
		return listaDeReservasPorClienteC;
	}

	public boolean existsByFecha(String fecha) {
		return reservaRepository.existsByFecha(fecha);
	}

	public boolean existsByHoraInicio(String HoraInicio) {
		return reservaRepository.existsByHoraInicio(HoraInicio);
	}

	public boolean existsByHoraFin(String HoraFin) {
		return reservaRepository.existsByHoraFin(HoraFin);
	}
}
