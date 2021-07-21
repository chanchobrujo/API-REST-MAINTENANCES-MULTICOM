package com.proyectoIntegrador.MultiCom.service;

import com.proyectoIntegrador.MultiCom.logic.myFuntions;
import com.proyectoIntegrador.MultiCom.logic.myStates;
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
		List<Reserva> lista = reservaRepository.findAll();

		lista.sort(new Comparator<Reserva>() {
			@Override
			public int compare(Reserva o1, Reserva o2) {
				try {
					SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm");
					Date horaInicioA = formatoHora.parse(o1.getHoraInicio());
					Date horaInicioB = formatoHora.parse(o2.getHoraInicio());

					return horaInicioA.compareTo(horaInicioB);
				} catch (Exception e) {
					return -1;
				}

			}
		});

		lista.sort(new Comparator<Reserva>() {
			@Override
			public int compare(Reserva o1, Reserva o2) {
				try {
					SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
					Date fecha1 = formato.parse(o1.getFecha());
					Date fecha2 = formato.parse(o2.getFecha());

					return fecha1.compareTo(fecha2);
				} catch (Exception e) {
					return -1;
				}

			}
		});

		return lista;
	}

	public void save(Reserva reserva) {
		reservaRepository.save(reserva);
	}

	public boolean getByFecha(String fecha, String hora1, String hora2) {
		List<Reserva> lista1 = reservaRepository.findAll();
		Boolean value = false;

		for (int i = 0; i < lista1.size(); i++) {
			Reserva obj = (Reserva) lista1.get(i);
			if (obj.getFecha().equals(fecha))
				if (obj.getEstado().equals(myStates.STATE_APPOINTMENT_ACEPTBYCLIENT)
						&& obj.getEstado().equals(myStates.STATE_APPOINTMENT_ACEPTBYEMPLOYEE)
						&& obj.getEstado().equals(myStates.STATE_APPOINTMENT_NEARLY_EXPIRED))
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

	public List<Reserva> listReservationFilter(int id, String state) {
		List<Reserva> listaDeReservasPorCliente = reservaRepository.findByUsuario(usuarioService.getById(id).get());
		List<Reserva> listaDeReservasPorClienteFilter = new ArrayList<Reserva>();

		for (int i = 0; i < listaDeReservasPorCliente.size(); i++) {
			Reserva obj = (Reserva) reservaRepository.findByUsuario(usuarioService.getById(id).get()).get(i);
			if (obj.getEstado().equals(state))
				listaDeReservasPorClienteFilter.add(obj);
		}

		listaDeReservasPorClienteFilter.sort(new Comparator<Reserva>() {
			@Override
			public int compare(Reserva o1, Reserva o2) {
				try {
					SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm");
					Date horaInicioA = formatoHora.parse(o1.getHoraInicio());
					Date horaInicioB = formatoHora.parse(o2.getHoraInicio());

					return horaInicioA.compareTo(horaInicioB);
				} catch (Exception e) {
					return -1;
				}

			}
		});

		listaDeReservasPorClienteFilter.sort(new Comparator<Reserva>() {
			@Override
			public int compare(Reserva o1, Reserva o2) {
				try {
					SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
					Date fecha1 = formato.parse(o1.getFecha());
					Date fecha2 = formato.parse(o2.getFecha());

					return fecha1.compareTo(fecha2);
				} catch (Exception e) {
					return -1;
				}

			}
		});

		return listaDeReservasPorClienteFilter;
	}

	public List<Reserva> getByUserNC(int id) {
		List<Reserva> listaA = listReservationFilter(id, myStates.STATE_APPOINTMENT_ACEPTBYCLIENT);
		List<Reserva> listaB = listReservationFilter(id, myStates.STATE_APPOINTMENT_NEARLY_EXPIRED); 
		listaB.addAll(listaA);
		return listaB;
	}

	public List<Reserva> getByUserC(int id) {
		return listReservationFilter(id, myStates.STATE_APPOINTMENT_CANCEL);
	}

	public List<Reserva> getByUserEX(int id) {
		return listReservationFilter(id, myStates.STATE_APPOINTMENT_EXPIRED);
	} 

	public int getSizeByUserNC(int id) {
		return listReservationFilter(id, myStates.STATE_APPOINTMENT_ACEPTBYCLIENT).size();
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
