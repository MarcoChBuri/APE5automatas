import { HttpErrorResponse, HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, of } from 'rxjs';

import { AutomatonDefinition, AutomatonId, ValidationOutcome } from '../models/automaton.models';

const API_BASE_URL = 'http://localhost:8080/api/automata';

@Injectable({ providedIn: 'root' })
export class AutomataApiService {
	constructor(private readonly http: HttpClient) {}

	validate(automaton: AutomatonDefinition, chain: string): Observable<ValidationOutcome> {
		return this.http
			.post(`${API_BASE_URL}/${automaton.id}`, chain, {
				observe: 'response',
				responseType: 'text',
			})
			.pipe(
				map((response: HttpResponse<string>) => this.buildSuccessOutcome(automaton, chain, response.body)),
				catchError((error: HttpErrorResponse) => of(this.buildErrorOutcome(automaton, chain, error))),
			);
	}

	private buildSuccessOutcome(automaton: AutomatonDefinition, chain: string, body: string | null): ValidationOutcome {
		return {
			accepted: true,
			message: body?.trim() || `Aceptada por ${automaton.label}`,
			statusCode: 200,
			chain,
			automaton,
		};
	}

	private buildErrorOutcome(automaton: AutomatonDefinition, chain: string, error: HttpErrorResponse): ValidationOutcome {
		const message = typeof error.error === 'string' && error.error.trim().length > 0 ? error.error : 'Rechazada';
		const serverMessage =
			error.status === 0
				? 'No se pudo conectar con la API. Verifica que Spring Boot este corriendo en http://localhost:8080.'
				: message;

		return {
			accepted: false,
			message: serverMessage,
			statusCode: error.status || 400,
			chain,
			automaton,
		};
	}
}
