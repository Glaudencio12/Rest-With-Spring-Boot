package br.com.Glaudencio12.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) { }
