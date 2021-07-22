package com.proyectoIntegrador.MultiCom.controller;

import com.proyectoIntegrador.MultiCom.dto.*;
import com.proyectoIntegrador.MultiCom.model.*;
import com.proyectoIntegrador.MultiCom.logic.*;
import com.proyectoIntegrador.MultiCom.service.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.beans.factory.annotation.*;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/Reservation")
@CrossOrigin(origins = "*")
public class ReservationController {

	@Autowired
	ReservationService reservaService;

	@Autowired
	ClientService clienteService;

	@Autowired
	UserService usuarioService;

	@Autowired
	ClaimService claimService;

	static ScheduledExecutorService executor = null;

	@PreAuthorize("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN')")
	@GetMapping("/")
	public ResponseEntity<?> list() {
		List<Reserva> list = reservaService.list();
		return new ResponseEntity<List<Reserva>>(list, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody ReservationDto reservaDto) {
		try {

			Reserva reserva = new Reserva(reservaDto.getFecha(), reservaDto.getHoraInicio(), reservaDto.getHoraFin(),
					myStates.STATE_APPOINTMENT_ACEPTBYEMPLOYEE, usuarioService.getById(reservaDto.getIdusuario()).get(),
					reservaDto.getPropocito());
			Set<Cliente> clientes = new HashSet<>();

			if (myFuntions.compareTime(reserva.getHoraInicio(), reserva.getHoraFin()) != -1)
				return new ResponseEntity<Object>(new _Message(myStates.ERROR_APPOINTMENT_HOURS_FAIL),
						HttpStatus.BAD_REQUEST);

			if (reservaService.getByFecha(reservaDto.getFecha(), reservaDto.getHoraInicio(), reservaDto.getHoraFin()))
				return new ResponseEntity<Object>(new _Message(myStates.ERROR_APPOINTMENT_CROSS_DATETIME),
						HttpStatus.BAD_REQUEST);

			if (reservaDto.getClientes().size() == 0)
				return new ResponseEntity<Object>(new _Message(myStates.ERROR_APPOINTMENT_SIZECLIENT_VOID),
						HttpStatus.NOT_FOUND);

			for (Object object : reservaDto.getClientes()) {
				String id = (String) object;

				if (!(clienteService.existsById(Integer.parseInt(id))))
					return new ResponseEntity<Object>(new _Message(myStates.ERROR_CLIENTE_NOT_FOUND),
							HttpStatus.NOT_FOUND);

				clientes.add(clienteService.getOne(Integer.parseInt(id)).get());
				myFuntions.sendMail(clienteService.getOne(Integer.parseInt(id)).get().getEmail(),
						"Su cita a sido registrada.",
						"Lo esperamos el " + reservaDto.getFecha() + " a las " + reservaDto.getHoraFin() + ".");
			}

			reserva.setClientes(clientes);
			reservaService.save(reserva);

			return new ResponseEntity<Object>(new _Message(myStates.APPOINTMENT_CREATED), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new _Message(myStates.ERROR_GENERAL), HttpStatus.BAD_REQUEST);
		}
	}

	public void sendMail(int idusuario, String asunto, String mensaje) {
		try {
			myFuntions.sendMail(usuarioService.getById(idusuario).get().getEmail(), asunto, mensaje);
		} catch (Exception e) {
		}
	}

	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/createReservationByClient")
	public ResponseEntity<?> createByClient(@RequestBody ReservationDto reservaDto) {
		try {

			if (myFuntions.compareTime(reservaDto.getHoraInicio(), reservaDto.getHoraFin()) != -1)
				return new ResponseEntity<Object>(new _Message(myStates.ERROR_APPOINTMENT_HOURS_FAIL),
						HttpStatus.BAD_REQUEST);

			if (reservaService.getByFecha(reservaDto.getFecha(), reservaDto.getHoraInicio(), reservaDto.getHoraFin()))
				return new ResponseEntity<Object>(new _Message(myStates.ERROR_APPOINTMENT_CROSS_DATETIME),
						HttpStatus.BAD_REQUEST);

			Reserva reserva = new Reserva(reservaDto.getFecha(), reservaDto.getHoraInicio(), reservaDto.getHoraFin(),
					myStates.STATE_APPOINTMENT_ACEPTBYCLIENT, usuarioService.getById(reservaDto.getIdusuario()).get(),
					reservaDto.getPropocito());
			reservaService.save(reserva);

			TimerTask _timerTask = new TimerTask() {
				@Override
				public void run() {
					String fechaString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					String horaString = new SimpleDateFormat("HH:mm").format(new Date());
					//System.out.println(fechaString+horaString);

					if (fechaString.equals(reservaDto.getFecha())) {
						if ((myFuntions.compareTime(myFuntions.incrementHours(horaString, 1),
								reservaDto.getHoraInicio()) >= 0)) {

							changeStateReservation(reserva.getId(), myStates.STATE_APPOINTMENT_NEARLY_EXPIRED);
							sendMail(reservaDto.getIdusuario(), myStates.STATE_APPOINTMENT_NEARLY_EXPIRED,
									"Su cita está cerca de expirar."); 
						}

						if ((myFuntions.compareTime(horaString, reservaDto.getHoraInicio()) == 0)) {

							changeStateReservation(reserva.getId(), myStates.STATE_APPOINTMENT_EXPIRED);
							sendMail(reservaDto.getIdusuario(), myStates.STATE_APPOINTMENT_EXPIRED, "Su cita venció.");
							executor.shutdown();

						}
					}
				}
			};

			executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(_timerTask, 1, 1, TimeUnit.SECONDS);

			return new ResponseEntity<Object>(new _Message(myStates.APPOINTMENT_CREATED), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new _Message(myStates.ERROR_GENERAL), HttpStatus.BAD_REQUEST);
		}
	}

	public void changeStateReservation(int id, String state) {
		try {
			Reserva reserva = reservaService.getById(id).get();
			reserva.setEstado(state);
			reservaService.save(reserva);
		} catch (Exception e) {
		}
	}

	@PreAuthorize("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENTE')")
	@GetMapping("/viewReservation/{id}")
	public ResponseEntity<?> view_Reservation(@PathVariable("id") int id) {
		try {
			Reserva reserva = reservaService.getById(id).get();
			return new ResponseEntity<Reserva>(reserva, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new _Message(myStates.ERROR_GENERAL), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENTE')")
	@GetMapping("/ByUserNC/{id}")
	public ResponseEntity<?> listByUserNC(@PathVariable("id") int id) {
		List<Reserva> list = reservaService.getByUserNC(id);
		return new ResponseEntity<List<Reserva>>(list, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENTE')")
	@GetMapping("/ByUserC/{id}")
	public ResponseEntity<?> listByUserC(@PathVariable("id") int id) {
		List<Reserva> list = reservaService.getByUserC(id);
		return new ResponseEntity<List<Reserva>>(list, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENTE')")
	@GetMapping("/ByUserEX/{id}")
	public ResponseEntity<?> listByUserEX(@PathVariable("id") int id) {
		List<Reserva> list = reservaService.getByUserEX(id);
		return new ResponseEntity<List<Reserva>>(list, HttpStatus.OK);
	} 

	@PreAuthorize("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENTE')")
	@PostMapping("/cancelReservation/{id}")
	public ResponseEntity<?> cancelReservation(@PathVariable("id") int id) {
		try {
			Reserva reserva = reservaService.getById(id).get();
			reserva.setEstado(myStates.STATE_APPOINTMENT_CANCEL);
			reservaService.save(reserva); 
			executor.shutdown();
			return new ResponseEntity<Object>(new _Message(myStates.STATE_APPOINTMENT_CANCEL), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new _Message(myStates.ERROR_APPOINTMENT_NOT_FOUND), HttpStatus.OK);
		}
	}

	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/complainByCLient")
	public ResponseEntity<?> complainReservation(@RequestBody ComplainDto complaindto) {
		try {
			Reclamo reclamo = new Reclamo(complaindto.getMotivo(), complaindto.getDescripcion());
			claimService.save(reclamo);

			Set<Reclamo> reclamoList = new HashSet<>();
			reclamoList.add(reclamo);

			Reserva reserva = reservaService.getById(complaindto.getIdreserva()).get();

			reserva.setEstado(myStates.STATE_APPOINTMENT_CANCEL);
			reserva.setReclamo(reclamoList);
			reservaService.save(reserva);
			return new ResponseEntity<Object>(new _Message("Reclamo realizado."), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new _Message(myStates.ERROR_APPOINTMENT_NOT_FOUND), HttpStatus.OK);
		}
	}
}
