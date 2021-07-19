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
					"En proceso.", usuarioService.getById(reservaDto.getIdusuario()).get(), reservaDto.getPropocito());
			Set<Cliente> clientes = new HashSet<>();

			if (myFuntions.compareTime(reserva.getHoraInicio(), reserva.getHoraFin()) != -1)
				return new ResponseEntity<Object>(new _Message("La hora inicial debe ser menor a la final."),
						HttpStatus.BAD_REQUEST);

			if (reservaService.getByFecha(reservaDto.getFecha(), reservaDto.getHoraInicio(), reservaDto.getHoraFin()))
				return new ResponseEntity<Object>(new _Message("Hay cruce de horarios."), HttpStatus.BAD_REQUEST);

			if (reservaDto.getClientes().size() == 0)
				return new ResponseEntity<Object>(new _Message("No selecionó clientes."), HttpStatus.NOT_FOUND);

			for (Object object : reservaDto.getClientes()) {
				String id = (String) object;

				if (!(clienteService.existsById(Integer.parseInt(id))))
					return new ResponseEntity<Object>(new _Message("Cliente no encontrado."), HttpStatus.NOT_FOUND);

				clientes.add(clienteService.getOne(Integer.parseInt(id)).get());
				myFuntions.sendMail(clienteService.getOne(Integer.parseInt(id)).get().getEmail(),
						"Su cita a sido registrada.",
						"Lo esperamos el " + reservaDto.getFecha() + " a las " + reservaDto.getHoraFin() + ".");
			}

			reserva.setClientes(clientes);
			reservaService.save(reserva);

			return new ResponseEntity<Object>(new _Message("Cita registrada."), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Object>(new _Message("Datos incorrectos."), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('ROLE_CLIENTE')")
	@PostMapping("/createReservationByClient")
	public ResponseEntity<?> createByClient(@RequestBody ReservationDto reservaDto) {
		try {

			if (myFuntions.compareTime(reservaDto.getHoraInicio(), reservaDto.getHoraFin()) != -1)
				return new ResponseEntity<Object>(new _Message("La hora inicial debe ser menor a la final."),
						HttpStatus.BAD_REQUEST);

			if (reservaService.getByFecha(reservaDto.getFecha(), reservaDto.getHoraInicio(), reservaDto.getHoraFin()))
				return new ResponseEntity<Object>(new _Message("Hay cruce de horarios."), HttpStatus.BAD_REQUEST);

			Reserva reserva = new Reserva(reservaDto.getFecha(), reservaDto.getHoraInicio(), reservaDto.getHoraFin(),
					"Solicitada por un cliente (o usted).", usuarioService.getById(reservaDto.getIdusuario()).get(),
					reservaDto.getPropocito());
			reservaService.save(reserva);

			TimerTask _timerTask = new TimerTask() { 

	 	        @Override
	 	        public void run() {  
	 	        	String fechaString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	 	        	String horaString = new SimpleDateFormat("HH:mm").format(new Date());

	 	        	if( fechaString.equals(reservaDto.getFecha()) ) {
	 	        		String state = "";
	 	        		if( ( myFuntions.compareTime( myFuntions.incrementHours(horaString, 1) , reservaDto.getHoraInicio() ) == 0 ) ) {
	 	        			state = "Apunto de ser expirada.";
 	        				changeStateReservation(reserva.getId(), state);
	 	        		}
	 	        		
	 	        		if ( (myFuntions.compareTime(horaString, reservaDto.getHoraInicio()) == 0) ) { 
	 	        			state = "Cita expirada.";
		 	        		executor.shutdown(); 
	 	        			changeStateReservation(reserva.getId(), state);
	 	        		}
	 	        	} 
	 	        }
	 	    };
	 	    
	 	    executor = Executors.newScheduledThreadPool(1);
	 	    executor.scheduleAtFixedRate(_timerTask, 1 , 1, TimeUnit.SECONDS);  
			
			return new ResponseEntity<Object>(new _Message("Cita registrada."), HttpStatus.OK); 
		} catch (Exception e) {
			return new ResponseEntity<Object>(new _Message("Datos incorrectos."), HttpStatus.BAD_REQUEST);
		}
	}
	
	public  void changeStateReservation(int id, String state) {
		try {
			Reserva reserva = reservaService.getById(id).get();
			reserva.setEstado(state);
			reservaService.save(reserva); 
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@PreAuthorize("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENTE')")
	@GetMapping("/viewReservation/{id}")
	public ResponseEntity<?> view_Reservation(@PathVariable("id") int id) {
		try {
			Reserva reserva = reservaService.getById(id).get();
			return new ResponseEntity<Reserva>(reserva, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new _Message(e.getMessage()), HttpStatus.BAD_REQUEST);
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
	@PostMapping("/cancelReservation/{id}")
	public ResponseEntity<?> cancelReservation(@PathVariable("id") int id) {
		try {
			Reserva reserva = reservaService.getById(id).get();
			reserva.setEstado("Cita cancelada.");
			reservaService.save(reserva);
			return new ResponseEntity<Object>(new _Message("Cita cancelada."), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new _Message("Cita no encontrada."), HttpStatus.OK);
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

			reserva.setEstado("Cita cancelada.");
			reserva.setReclamo(reclamoList);
			reservaService.save(reserva);
			return new ResponseEntity<Object>(new _Message("Reclamo realizado."), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new _Message("Cita no encontrada."), HttpStatus.OK);
		}
	}
}
