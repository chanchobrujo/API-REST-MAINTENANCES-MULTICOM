package com.proyectoIntegrador.MultiCom.logic;

public class myStates {  
	 /* GEENRAL ERRORS */
	 public static String ERROR_GENERAL = "Datos incorrectos.";

	 /* APPOINTMENTS */
	 public static String APPOINTMENT_CREATED= "Cita registrada correctamente.";
	 
	 public static String STATE_APPOINTMENT_ACEPTBYCLIENT = "Solicitada por un cliente (o usted).";
	 public static String STATE_APPOINTMENT_ACEPTBYEMPLOYEE = "En proceso.";
	 public static String STATE_APPOINTMENT_CANCEL = "Cita cancelada.";
	 public static String STATE_APPOINTMENT_EXPIRED = "Cita expirada.";  
	 public static String STATE_APPOINTMENT_NEARLY_EXPIRED = "Cita apunto de expirar."; 
	 
	 public static String ERROR_APPOINTMENT_NOT_FOUND = "Cita no encontrada.";  
	 public static String ERROR_APPOINTMENT_CROSS_DATETIME = "Hay cruce de horarios.";
	 public static String ERROR_APPOINTMENT_HOURS_FAIL = "La hora inicial debe ser menor a la final.";
	 public static String ERROR_APPOINTMENT_SIZECLIENT_VOID = "No a seleccionado ningún cliente."; 
	 
	 /* CLIENTS */
	 public static String ERROR_CLIENTE_NOT_FOUND = "Cliente no encontrado.";
	 
}